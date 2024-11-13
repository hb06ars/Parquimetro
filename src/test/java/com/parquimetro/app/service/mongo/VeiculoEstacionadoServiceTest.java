package com.parquimetro.app.service.mongo;

import com.parquimetro.domain.dto.RequestVeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoCustomRepository;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VeiculoEstacionadoServiceTest {

    @Mock
    private VeiculoEstacionadoRepository repository;

    @Mock
    private VeiculoEstacionadoCustomRepository customRepository;

    @InjectMocks
    private VeiculoEstacionadoService veiculoEstacionadoService;

    private VeiculoEstacionado veiculo;
    private String numeroProcesso = "1234";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        veiculo = new VeiculoEstacionado();
        veiculo.setNumeroProcesso(numeroProcesso);
        veiculo.setPlaca("ABC1234");
        veiculo.setLocal("Zona Azul");

        // Usando LocalDateTime com data e hora
        veiculo.setHoraEntrada(LocalDateTime.parse("2024-11-12T12:00"));
        veiculo.setHoraSaida(LocalDateTime.parse("2024-11-12T14:00"));
        veiculo.setValor(BigDecimal.valueOf(10.0));
    }

    @Test
    void testSave() {
        // Mockando o comportamento do save
        when(repository.save(veiculo)).thenReturn(veiculo);

        // Chama o método save
        VeiculoEstacionado savedVeiculo = veiculoEstacionadoService.save(veiculo);

        // Verificando o comportamento esperado
        assertNotNull(savedVeiculo);
        assertEquals(veiculo.getPlaca(), savedVeiculo.getPlaca());
        verify(repository, times(1)).save(veiculo);
    }

    @Test
    void testFindByNumeroProcessoFound() {
        when(repository.findByNumeroProcesso(numeroProcesso)).thenReturn(Optional.of(veiculo));

        VeiculoEstacionado foundVeiculo = veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso);

        assertNotNull(foundVeiculo);
        assertEquals(numeroProcesso, foundVeiculo.getNumeroProcesso());
        verify(repository, times(1)).findByNumeroProcesso(numeroProcesso);
    }

    @Test
    void testFindByNumeroProcessoNotFound() {
        when(repository.findByNumeroProcesso(numeroProcesso)).thenReturn(Optional.empty());

        VeiculoEstacionado foundVeiculo = veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso);

        assertNull(foundVeiculo);
        verify(repository, times(1)).findByNumeroProcesso(numeroProcesso);
    }

    @Test
    void testUpdateSuccess() {
        VeiculoEstacionado veiculoAtualizado = new VeiculoEstacionado();
        veiculoAtualizado.setPlaca("XYZ9876");
        veiculoAtualizado.setLocal("Outra Zona");
        veiculoAtualizado.setHoraEntrada(LocalDateTime.parse("2024-11-12T10:00"));
        veiculoAtualizado.setHoraSaida(LocalDateTime.parse("2024-11-12T12:00"));
        veiculoAtualizado.setValor(BigDecimal.valueOf(20.0));

        when(repository.findByNumeroProcesso(numeroProcesso)).thenReturn(Optional.of(veiculo));
        when(repository.save(any(VeiculoEstacionado.class))).thenReturn(veiculoAtualizado);

        VeiculoEstacionado updatedVeiculo = veiculoEstacionadoService.update(numeroProcesso, veiculoAtualizado);

        assertNotNull(updatedVeiculo);
        assertEquals("XYZ9876", updatedVeiculo.getPlaca());
        assertEquals("Outra Zona", updatedVeiculo.getLocal());
        verify(repository, times(1)).findByNumeroProcesso(numeroProcesso);
        verify(repository, times(1)).save(any(VeiculoEstacionado.class));
    }

    @Test
    void testUpdateNotFound() {
        VeiculoEstacionado veiculoAtualizado = new VeiculoEstacionado();
        veiculoAtualizado.setPlaca("XYZ9876");

        when(repository.findByNumeroProcesso(numeroProcesso)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            veiculoEstacionadoService.update(numeroProcesso, veiculoAtualizado);
        });

        assertEquals("Veículo com Número do Processo 1234 não encontrado.", exception.getMessage());
        verify(repository, times(1)).findByNumeroProcesso(numeroProcesso);
    }

    @Test
    void testDeleteSuccess() {
        when(repository.existsByNumeroProcesso(numeroProcesso)).thenReturn(true);

        veiculoEstacionadoService.delete(numeroProcesso);

        verify(repository, times(1)).existsByNumeroProcesso(numeroProcesso);
        verify(repository, times(1)).deleteByNumeroProcesso(numeroProcesso);
    }

    @Test
    void testDeleteNotFound() {
        when(repository.existsByNumeroProcesso(numeroProcesso)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            veiculoEstacionadoService.delete(numeroProcesso);
        });

        assertEquals("Veículo com Número do Processo 1234 não encontrado.", exception.getMessage());
        verify(repository, times(1)).existsByNumeroProcesso(numeroProcesso);
    }

    @Test
    void testFindByPlacaPendentePagamentoFound() {
        when(repository.findByPlacaPendentePagamento("ABC1234")).thenReturn(Optional.of(veiculo));

        VeiculoEstacionado foundVeiculo = veiculoEstacionadoService.findByPlacaPendentePagamento("ABC1234");

        assertNotNull(foundVeiculo);
        assertEquals("ABC1234", foundVeiculo.getPlaca());
        verify(repository, times(1)).findByPlacaPendentePagamento("ABC1234");
    }

    @Test
    void testFindByPlacaPendentePagamentoNotFound() {
        when(repository.findByPlacaPendentePagamento("XYZ9876")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            veiculoEstacionadoService.findByPlacaPendentePagamento("XYZ9876");
        });

        verify(repository, times(1)).findByPlacaPendentePagamento("XYZ9876");
    }

    @Test
    void testBuscaPaginada() {
        RequestVeiculoEstacionadoDTO dto = new RequestVeiculoEstacionadoDTO();
        Pageable pageable = PageRequest.of(0, 10);
        Page<VeiculoEstacionado> page = mock(Page.class);

        when(customRepository.findAllByCriteria(dto, pageable, "placa", "asc")).thenReturn(page);

        Page<VeiculoEstacionado> result = veiculoEstacionadoService.buscaPaginada(dto, 0, 10, "placa", "asc");

        assertNotNull(result);
        verify(customRepository, times(1)).findAllByCriteria(dto, pageable, "placa", "asc");
    }
}

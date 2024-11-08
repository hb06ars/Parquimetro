package com.parquimetro.domain.dto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MessageError {
    private String erro;
    private String detalhe;
}
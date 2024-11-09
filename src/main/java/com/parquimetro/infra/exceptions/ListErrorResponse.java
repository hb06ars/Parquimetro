package com.parquimetro.infra.exceptions;

import com.parquimetro.domain.dto.MessageError;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListErrorResponse {
    private List<MessageError> message;
    private int statusCode;

    public ListErrorResponse(List<MessageError> message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

}

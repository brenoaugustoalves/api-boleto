package br.com.breno.api_boleto.controller.exception;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroResponse {

    private String erro;

    private int codigo;

    private Date timestamp;

    private String path;
}

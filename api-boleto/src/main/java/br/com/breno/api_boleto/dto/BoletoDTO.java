package br.com.breno.api_boleto.dto;

import br.com.breno.api_boleto.entity.enums.SituacaoBoleto;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoDTO {


    private String codigoBarras;


    private SituacaoBoleto situacaoBoleto;


    private LocalDateTime datacriacao;


    private LocalDateTime dataatualizacao;
}

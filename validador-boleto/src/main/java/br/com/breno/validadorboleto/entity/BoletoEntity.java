package br.com.breno.validadorboleto.entity;

import br.com.breno.validadorboleto.entity.enums.SituacaoBoleto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "codigo_barras")
    private String codigoBarras;

    @Column(name = "situacao_boleto")
    private SituacaoBoleto situacaoBoleto;

    @Column(name = "data_criacao")
    private LocalDateTime datacriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataatualizacao;

}

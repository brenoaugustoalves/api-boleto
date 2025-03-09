package br.com.breno.api_boleto.mapper;

import br.com.breno.api_boleto.dto.BoletoDTO;
import br.com.breno.api_boleto.entity.BoletoEntity;
import br.com.breno.api_boleto.entity.enums.SituacaoBoleto;
import br.com.breno.avro.Boleto;

public class BoletoMapper {

    public static BoletoDTO todto(BoletoEntity boleto) {
        return BoletoDTO.builder()
                .codigoBarras(boleto.getCodigoBarras())
                .situacaoBoleto(boleto.getSituacaoBoleto())
                .datacriacao(boleto.getDatacriacao())
                .dataatualizacao(boleto.getDataatualizacao())
                .build();
    }

    public static Boleto toavro(BoletoEntity boleto) {
        return Boleto.newBuilder()
                .setCodigoBarras(boleto.getCodigoBarras())
                .setSituacaoBoleto(boleto.getSituacaoBoleto().ordinal())
                .build();
    }

    public static BoletoEntity toEntity(Boleto boleto){
        return BoletoEntity.builder()
                .codigoBarras(boleto.getCodigoBarras().toString())
                .situacaoBoleto(SituacaoBoleto.values()[boleto.getSituacaoBoleto()])
                .build();
    }
}

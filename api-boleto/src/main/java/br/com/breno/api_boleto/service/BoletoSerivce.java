package br.com.breno.api_boleto.service;

import br.com.breno.api_boleto.controller.exception.ApplicationException;
import br.com.breno.api_boleto.controller.exception.NotFoundException;
import br.com.breno.api_boleto.dto.BoletoDTO;
import br.com.breno.api_boleto.entity.BoletoEntity;
import br.com.breno.api_boleto.entity.enums.SituacaoBoleto;
import br.com.breno.api_boleto.mapper.BoletoMapper;
import br.com.breno.api_boleto.repository.BoletoRepository;
import br.com.breno.api_boleto.service.kafka.BoletoProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoSerivce {

    public BoletoSerivce(BoletoRepository boletoRepository, BoletoProducer boletoProducer) {
        this.boletoRepository = boletoRepository;
        this.boletoProducer = boletoProducer;
    }

    private final BoletoRepository boletoRepository;

    private final BoletoProducer boletoProducer;

    public BoletoDTO save(String codigoBarras) {
        var boletoOptional = boletoRepository.findByCodigoBarras(codigoBarras);
        if (boletoOptional.isPresent()) {
            throw new ApplicationException("Já existe uma solicitação para este boleto");
        }
        var boletoEntity = BoletoEntity.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .datacriacao(LocalDateTime.now())
                .dataatualizacao(LocalDateTime.now())
                .build();


        boletoRepository.save(boletoEntity);
        boletoProducer.enviarMensagem(BoletoMapper.toavro(boletoEntity));
        return BoletoMapper.todto(boletoEntity);
    }

    public BoletoDTO buscarBoletoPorCodigoBarras(String codigobarras){
        return BoletoMapper.todto(recuperarBoleto(codigobarras));
    }


    private BoletoEntity recuperarBoleto(String codigobarras) {
        return boletoRepository.findByCodigoBarras(codigobarras)
                .orElseThrow(() -> new NotFoundException("Boleto não encontrado"));
    }

    public void atualizar(BoletoEntity boleto) {
        var boletoAtual = recuperarBoleto(boleto.getCodigoBarras());

        boletoAtual.setSituacaoBoleto(boleto.getSituacaoBoleto());
        boletoAtual.setDataatualizacao(LocalDateTime.now());
        boletoRepository.save(boletoAtual);
    }

}

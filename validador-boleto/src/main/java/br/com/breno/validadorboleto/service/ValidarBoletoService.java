package br.com.breno.validadorboleto.service;

import br.com.breno.validadorboleto.entity.BoletoEntity;
import br.com.breno.validadorboleto.entity.enums.SituacaoBoleto;
import br.com.breno.validadorboleto.mapper.BoletoMapper;
import br.com.breno.validadorboleto.repository.BoletoRepository;
import br.com.breno.validadorboleto.service.kafka.NotificacaoProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ValidarBoletoService {


    private final NotificacaoProducer notificacaoProducer;

    private final BoletoRepository boletoRepository;

    private final PagarBoletoService pagarBoletoService;

    public ValidarBoletoService(NotificacaoProducer notificacaoProducer, BoletoRepository boletoRepository, PagarBoletoService pagarBoletoService) {
        this.notificacaoProducer = notificacaoProducer;
        this.boletoRepository = boletoRepository;
        this.pagarBoletoService = pagarBoletoService;
    }

    public void validar(BoletoEntity boleto){
        var codigo = Integer.parseInt(boleto.getCodigoBarras().toString().substring(0,1));
        if (codigo % 2 == 0 ){
            complementarErro(boleto);
            boletoRepository.save(boleto);
            notificacaoProducer.enviarMensagem(BoletoMapper.toAvro(boleto));
        } else {
            complementarBoletoSucesso(boleto);
            boletoRepository.save(boleto);
            notificacaoProducer.enviarMensagem(BoletoMapper.toAvro(boleto));
            pagarBoletoService.pagar(boleto);
        }

    }

    private void complementarErro(BoletoEntity boleto){
        boleto.setDatacriacao(LocalDateTime.now());
        boleto.setDataatualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.ERRO_VALIDADAO);
    }

    private void complementarBoletoSucesso(BoletoEntity boleto){
        boleto.setDatacriacao(LocalDateTime.now());
        boleto.setDataatualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.VALIDADO);
    }
}

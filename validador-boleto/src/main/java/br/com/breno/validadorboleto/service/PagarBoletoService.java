package br.com.breno.validadorboleto.service;

import br.com.breno.avro.Boleto;
import br.com.breno.validadorboleto.entity.BoletoEntity;
import br.com.breno.validadorboleto.entity.enums.SituacaoBoleto;
import br.com.breno.validadorboleto.mapper.BoletoMapper;
import br.com.breno.validadorboleto.repository.BoletoRepository;
import br.com.breno.validadorboleto.service.kafka.NotificacaoProducer;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagarBoletoService {


    private final BoletoRepository boletoRepository;

    private final NotificacaoProducer notificacaoProducer;

    public PagarBoletoService(BoletoRepository boletoRepository, NotificacaoProducer notificacaoProducer) {
        this.boletoRepository = boletoRepository;
        this.notificacaoProducer = notificacaoProducer;
    }

    @SneakyThrows
    public void pagar(BoletoEntity boleto){
        Thread.sleep(10000);
        String codigoBarrasNumeros = boleto.getCodigoBarras().replaceAll("[^0-9]", "");
        if (codigoBarrasNumeros.length() > 47){
            complementarBoletoErro(boleto);
        } else {
            complementarBoletoSucesso(boleto);
        }

        boletoRepository.save(boleto);
        notificacaoProducer.enviarMensagem(BoletoMapper.toAvro(boleto));
    }


    private void complementarBoletoErro(BoletoEntity boleto){
        boleto.setDataatualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.ERRO_PAGAMENTO);
    }

    private void complementarBoletoSucesso(BoletoEntity boleto){
        boleto.setDataatualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.PAGO);
    }
}

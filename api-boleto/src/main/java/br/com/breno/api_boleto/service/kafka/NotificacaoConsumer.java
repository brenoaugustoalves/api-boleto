package br.com.breno.api_boleto.service.kafka;

import br.com.breno.api_boleto.mapper.BoletoMapper;
import br.com.breno.api_boleto.service.BoletoSerivce;
import br.com.breno.avro.Boleto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacaoConsumer.class);

    private final BoletoSerivce boletoSerivce;

    public NotificacaoConsumer(BoletoSerivce boletoSerivce) {
        this.boletoSerivce = boletoSerivce;
    }


    @KafkaListener(topics = "${spring.kafka.topico-notificacao}")
    public void consumer(@Payload Boleto boleto) {
        LOGGER.info(String.format("Consumindo notificação -> %s", boleto));
        boletoSerivce.atualizar(BoletoMapper.toEntity(boleto));
    }
}

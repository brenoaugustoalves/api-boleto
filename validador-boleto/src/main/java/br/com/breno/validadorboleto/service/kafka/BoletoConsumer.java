package br.com.breno.validadorboleto.service.kafka;

import br.com.breno.avro.Boleto;
import br.com.breno.validadorboleto.mapper.BoletoMapper;
import br.com.breno.validadorboleto.service.ValidarBoletoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BoletoConsumer  {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoletoConsumer.class);

    public BoletoConsumer(ValidarBoletoService validarBoletoService) {
        this.validarBoletoService = validarBoletoService;
    }

    private final ValidarBoletoService validarBoletoService;

    @KafkaListener(topics ="${spring.kafka.topico-boleto}" ,groupId = "${spring.kafka.consumer.group-id}")
    public void consomeBoleto(@Payload Boleto boleto, Acknowledgment ack ) {
        LOGGER.info(String.format("Comsumindo mensagem -> %s", boleto));
        validarBoletoService.validar(BoletoMapper.toEntity(boleto));
        ack.acknowledge();
    }

}

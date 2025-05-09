package br.com.breno.api_boleto.service.kafka;

import br.com.breno.avro.Boleto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BoletoProducer {

    @Value("${spring.kafka.topico-boleto}")
    public String topico;

    public BoletoProducer(KafkaTemplate<String, Boleto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private final KafkaTemplate<String, Boleto> kafkaTemplate;

    public void enviarMensagem(Boleto boleto) {
        kafkaTemplate.send(topico,getKey(boleto), boleto);

    }

    private String getKey(Boleto boleto) {
        if (boleto.getCodigoBarras().toString().substring(0,1).equals("2") ){
            return "chave1";
        }
        return "chave2";
    }
}

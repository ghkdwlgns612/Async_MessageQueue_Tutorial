package async.example.config;

import java.util.HashMap;
import java.util.Map;

import async.example.domain.OrderRequestDto;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {
    public KafkaConfig() {
    }

    @Bean
    public ProducerFactory<String, OrderRequestDto> producerFactory() {
        Map<String, Object> config = new HashMap();
        config.put("bootstrap.servers", "127.0.0.1:9092");
        config.put("key.serializer", StringSerializer.class);
        config.put("value.serializer", JsonSerializer.class);
        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public KafkaTemplate<String, OrderRequestDto> kafkaTemplate() {
        return new KafkaTemplate(this.producerFactory());
    }
}
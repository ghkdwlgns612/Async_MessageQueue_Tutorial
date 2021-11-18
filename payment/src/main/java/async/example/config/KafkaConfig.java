package async.example.config;

import java.util.HashMap;
import java.util.Map;

import async.example.domain.AsyncRequest;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfig {
    @Bean
    public ConsumerFactory<String, AsyncRequest> consumerFactory() {
        Map<String, Object> config = new HashMap();
        config.put("bootstrap.servers", "127.0.0.1:9092");
        config.put("group.id", "group_id");
        config.put("key.deserializer", StringDeserializer.class);
        config.put("value.deserializer", JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AsyncRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AsyncRequest> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(this.consumerFactory());
        return factory;
    }
}
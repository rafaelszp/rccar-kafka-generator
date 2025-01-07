package szp.rafael.rccar.app;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import szp.rafael.rccar.dto.Body;

import java.util.Properties;

import static szp.rafael.rccar.app.RCCarGenerator.sendBody;
import static szp.rafael.rccar.app.RCCarGenerator.sendRC;

public class RemoteControlGenerator {

    public static void main(String[] args) {
        System.out.println("Generating RC Car...");
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        properties.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, false);
        properties.put(KafkaAvroSerializerConfig.USE_LATEST_VERSION, true);
        properties.put(KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());

        properties.setProperty("schema.registry.url", "http://localhost:8081");

        sendRC(sendBody(properties), properties);

    }

}

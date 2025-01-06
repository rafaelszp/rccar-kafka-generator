package szp.rafael.rccar.app;


import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import io.vavr.collection.List;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import szp.rafael.rccar.dto.Body;
import szp.rafael.rccar.dto.Engine;
import szp.rafael.rccar.dto.PlacementType;
import szp.rafael.rccar.dto.RemoteControl;
import szp.rafael.rccar.dto.Wheel;
import szp.rafael.rccar.producer.BodyProducer;
import szp.rafael.rccar.producer.EngineProducer;
import szp.rafael.rccar.producer.RemoteControlProducer;
import szp.rafael.rccar.producer.WheelProducer;

import java.util.Properties;

public class RCCarGenerator {
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

        Body body = sendBody(properties);
        sendWheels(body, properties);
        sendEngine(body, properties);
        sendRC(body, properties);
    }


    private static RemoteControl sendRC(Body body, Properties properties) {
        RemoteControlProducer remoteControlProducer = new RemoteControlProducer(properties, "rccar-remote-control");
        var remoteControl = remoteControlProducer.create(body.getPart().getSku());
        remoteControlProducer.send(remoteControl);
        return remoteControl;
    }

    private static Engine sendEngine(Body body, Properties properties) {
        EngineProducer engineProducer = new EngineProducer(properties, "rccar-engine");
        Engine engine = engineProducer.create(body.getPart().getSku());
        engineProducer.send(engine);
        return engine;
    }

    private static List<Wheel> sendWheels(Body body, Properties properties) {
        WheelProducer wheelProducer = new WheelProducer(properties, "rccar-wheel");
        Wheel frontLeft = wheelProducer.create(body.getPart().getSku(), PlacementType.FRONT_LEFT);
        wheelProducer.send(frontLeft);

        Wheel frontRight = wheelProducer.create(body.getPart().getSku(), PlacementType.FRONT_RIGHT);
        wheelProducer.send(frontRight);

        Wheel rearLeft = wheelProducer.create(body.getPart().getSku(), PlacementType.REAR_LEFT);
        wheelProducer.send(rearLeft);

        Wheel rearRight = wheelProducer.create(body.getPart().getSku(), PlacementType.REAR_RIGHT);
        wheelProducer.send(rearRight);

        return List.of(frontLeft, frontRight, rearLeft, rearRight);
    }

    private static Body sendBody(Properties properties) {
        BodyProducer bodyProducer = new BodyProducer(properties, "rccar-body");
        Body body = bodyProducer.create();
        bodyProducer.send(body);
        return body;
    }
}

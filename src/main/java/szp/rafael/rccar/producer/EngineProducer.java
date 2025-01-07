package szp.rafael.rccar.producer;

import io.vavr.Tuple2;
import szp.rafael.rccar.dto.Engine;
import szp.rafael.rccar.dto.Part;
import szp.rafael.rccar.dto.PlacementType;
import szp.rafael.rccar.dto.Wheel;

import java.util.Properties;
import java.util.UUID;

public class EngineProducer extends PartProducer<Engine> {

    public EngineProducer(Properties properties, String topic) {
        super(properties, topic);
    }

    @Override
    public Engine create() {
        throw new RuntimeException("Must use create(PlacementType placement)");
    }

    public Engine create(String sku) {
        var part = Part.newBuilder().setPartName("engine").setSku(sku).build();
        var engine = Engine.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setPart(part)
                .build();
        this.event = new Tuple2<>(engine.getId(), engine);
        return engine;
    }

}

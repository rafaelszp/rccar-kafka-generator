package szp.rafael.rccar.producer;

import io.vavr.Tuple2;
import szp.rafael.rccar.dto.Body;
import szp.rafael.rccar.dto.Part;
import szp.rafael.rccar.dto.PlacementType;
import szp.rafael.rccar.dto.Wheel;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class WheelProducer extends PartProducer<Wheel> {

    public WheelProducer(Properties properties, String topic) {
        super(properties, topic);
    }

    @Override
    public Wheel create() {
        throw new RuntimeException("Must use create(PlacementType placement)");
    }

    public Wheel create(String sku, PlacementType placement) {
        var part = Part.newBuilder().setPartName("body").setSku(sku).build();
        var wheel = Wheel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setPlacement(placement)
                .setPart(part)
                .build();
        this.event = new Tuple2<>(wheel.getId(), wheel);
        return wheel;
    }

}

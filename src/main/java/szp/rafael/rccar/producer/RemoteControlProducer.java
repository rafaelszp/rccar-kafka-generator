package szp.rafael.rccar.producer;

import io.vavr.Tuple2;
import szp.rafael.rccar.dto.ChannelFrequencyType;
import szp.rafael.rccar.dto.Part;
import szp.rafael.rccar.dto.RemoteControl;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class RemoteControlProducer extends PartProducer<RemoteControl> {

    public RemoteControlProducer(Properties properties, String topic, String productName) {
        super(properties, topic, productName);
    }

    @Override
    public RemoteControl create() {
        throw new RuntimeException("Must use create(PlacementType placement)");
    }

    private Random random = new Random();

    public RemoteControl create(String sku) {
        var part = Part.newBuilder().setPartName("remote-control").setSku(sku).setProductName(productName).build();
        var body = RemoteControl.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setChannelFrequency(getChannelFrequency())
                .setPart(part)
                .build();
        this.event = new Tuple2<>(body.getId(), body);
        return body;
    }

    public ChannelFrequencyType getChannelFrequency() {
        int length = ChannelFrequencyType.values().length;
        int index = random.nextInt(0, length);
        return ChannelFrequencyType.values()[index];
    }

}

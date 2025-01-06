package szp.rafael.rccar.producer;

import io.vavr.Tuple2;
import szp.rafael.rccar.dto.Body;
import szp.rafael.rccar.dto.Part;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class BodyProducer extends PartProducer<Body> {

    public BodyProducer(Properties properties, String topic) {
        super(properties, topic);
    }

    private List<String> colors = List.of("RED", "BLUE", "GREEN", "YELLOW", "BLACK", "WHITE", "ORANGE", "PURPLE", "PINK", "BROWN");
    private List<String> materials = List.of("PLASTIC", "METAL", "CARBON FIBER", "ALUMINUM", "STEEL");
    private Random random = new Random();

    @Override
    public Body create() {
        var part = Part.newBuilder().setPartName("body").setSku(getSKU()).build();
        var body = Body.newBuilder()
                .setColor(getColor())
                .setId(UUID.randomUUID().toString())
                .setMaterial(getMaterial())
                .setWeight(random.nextFloat(0.3f,5.0f))
                .setPart(part)
                .build();
        this.event = new Tuple2<>(body.getId(), body);
        return body;
    }

    public String getColor(){
        int size = this.colors.size();
        int index = random.nextInt(0, size);
        return this.colors.get(index);
    }

    public String getMaterial(){
        int size = this.materials.size();
        int index = random.nextInt(0, size);
        return this.colors.get(index);
    }

}

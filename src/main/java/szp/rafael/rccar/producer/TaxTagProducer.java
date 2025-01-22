package szp.rafael.rccar.producer;

import io.vavr.Tuple2;
import szp.rafael.rccar.dto.State;
import szp.rafael.rccar.dto.TaxTag;

import java.util.Properties;
import java.util.Random;

public class TaxTagProducer extends PartProducer<TaxTag>{

    private Random random;

    public TaxTagProducer(Properties properties, String topic) {
        super(properties, topic);
        random = new Random();
    }

    @Override
    public TaxTag create() {
        throw new RuntimeException("Must use create(String state)");
    }

    public TaxTag create(State state) {
        var taxTag = TaxTag.newBuilder()
                .setIcms(getIcms())
                .setState(state).build();
        this.event = new Tuple2<>(taxTag.getState().name(), taxTag);
        return taxTag;
    }

    private double getIcms() {
        return random.nextDouble(0.09, 0.35);
    }


}

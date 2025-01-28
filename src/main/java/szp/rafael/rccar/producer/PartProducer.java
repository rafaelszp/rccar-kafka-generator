package szp.rafael.rccar.producer;


import io.vavr.Tuple2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Properties;
import java.util.Random;

public abstract class PartProducer<T> {

    private KafkaProducer<String, T> producer ;
    private String topic;
    protected Tuple2<String,T> event;
    protected Properties properties;
    private final Logger logger;


    public PartProducer(Properties properties, String topic) {
        this.topic = topic;
        this.properties = properties;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public abstract T create();

    public  void send(T part) {
        producer = new KafkaProducer<>(properties);
        var record = new ProducerRecord<>(topic, event._1,  part);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                logger.info("Sent record: {}", record);
            }
        });
        producer.flush();
        producer.close();
    }

    protected String getSKU(){
        Random random = new Random();
        long id = random.nextLong(Long.MAX_VALUE);
        return String.valueOf(id);
    }

    public void setEvent(Tuple2<String, T> event) {
        this.event = event;
    }
}

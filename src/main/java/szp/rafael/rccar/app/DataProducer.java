package szp.rafael.rccar.app;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.security.SecureRandom;
import java.util.Properties;

public class DataProducer {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
        Faker faker = new Faker(new SecureRandom());
        var superhero = faker.superhero();
        String hero = superhero.name() + " " + superhero.descriptor();

        try {
            for (int i = 1; i <= 200; i++) {
                DataBean data = new DataBean(hero + "_" + String.valueOf(i));
                ProducerRecord record = new ProducerRecord<String, String>("flink_kafka1", null, null, JSON.toJSONString(data));
                producer.send(record);
                System.out.println("send data: " + JSON.toJSONString(data));
                Thread.sleep(1000);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        producer.flush();
    }
}

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
class DataBean {
    public String value;

    public DataBean(String value) {
        this.value = value;
    }

    public DataBean() {
    }
}

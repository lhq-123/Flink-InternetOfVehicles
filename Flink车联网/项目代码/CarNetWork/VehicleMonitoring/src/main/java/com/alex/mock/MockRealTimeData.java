package com.alex.mock;

import com.alex.utils.DateUtil;
import com.alex.utils.StringUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;

/**
 * @author Alex_liu
 * @create 2023-01-28 18:28
 * @Description
 */
public class MockRealTimeData {
    private final static Logger logger = LoggerFactory.getLogger(MockRealTimeData.class);
    private static final Random random = new Random();
    private static final String[] locations = new String[]{"鲁","京","京","京","沪","京","京","深","京","京"};
    private KafkaProducer<String, String> producer;

    public MockRealTimeData() {
        producer = new KafkaProducer<>(createProducerConfig());
    }

    private Properties createProducerConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "node1:9092,node2:9092,node3:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    public void run() {
        logger.info("正在生产数据!!!!");
        while(true) {
            String date = DateUtil.getTodayDate();
            String baseActionTime = date + " " + StringUtil.fulfuill(random.nextInt(24)+"");
            baseActionTime = date + " " + StringUtil.fulfuill((Integer.parseInt(baseActionTime.split(" ")[1])+1)+"");
            String actionTime = baseActionTime + ":" + StringUtil.fulfuill(random.nextInt(60)+"") + ":" + StringUtil.fulfuill(random.nextInt(60)+"");
            String monitorId = StringUtil.fulfuill(4, random.nextInt(9)+"");
            String car = locations[random.nextInt(10)] + (char)(65+random.nextInt(26))+StringUtil.fulfuill(5,random.nextInt(99999)+"");
            String speed = random.nextInt(260)+"";
            String roadId = random.nextInt(50)+1+"";
            String cameraId = StringUtil.fulfuill(5, random.nextInt(9999)+"");
            String areaId = StringUtil.fulfuill(2,random.nextInt(8)+"");
            producer.send(new ProducerRecord<>("RoadRealTimeLog", date+"\t"+monitorId+"\t"+cameraId+"\t"+car + "\t" + actionTime + "\t" + speed + "\t" + roadId + "\t" + areaId));

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动Kafka Producer
     * @param args
     */
    public static void main(String[] args) {
        MockRealTimeData producer = new MockRealTimeData();
        producer.run();
    }

}

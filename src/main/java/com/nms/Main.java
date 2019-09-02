package com.nms;


import com.influxdb.LogLevel;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.time.Instant;
import java.util.Random;

public class Main {
    public static void main(String... args) {

        var rnd = new Random();

        var client = InfluxDBClientFactory
                .create("http://localhost:9999", "nemadm", "exoo7Ahs".toCharArray());

        client.setLogLevel(LogLevel.BODY);

        var writeApi = client.getWriteApi();

        while (true) {

            var point = Point
                    .measurement("ethernet")
                    .addField("MacError", rnd.nextInt(100))
                    .addField("OctetsPassed", Math.round(rnd.nextFloat() * 1000))
                    .addTag("NetworkElement", String.valueOf(rnd.nextInt(10)))
                    .addTag("Unit", String.valueOf(rnd.nextInt(10)))
                    .addTag("Port", String.valueOf(rnd.nextInt(5)))
                    .time(Instant.now().toEpochMilli(), WritePrecision.MS);

            writeApi.writePoint("Performance Management Collector", "ABB", point);
            writeApi.flush();

        }
    }
}

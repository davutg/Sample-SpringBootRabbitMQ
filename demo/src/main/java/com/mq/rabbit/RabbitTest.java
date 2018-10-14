package com.mq.rabbit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.mq.rabbit")
@SpringBootApplication
public class RabbitTest {
	public static long start;
	public static long end;

	public RabbitTest() throws Exception {

	}

	/**
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext ctx = SpringApplication.run(RabbitTest.class, args);
		Producer pro = (Producer) ctx.getBean("producer");
		start = new Date().getTime();
		for (int i = 0; i < 1000000; i++) {
			HashMap<String, Integer> message = new HashMap<String, Integer>();
			message.put("message number", i);
			pro.sendMessage(message);
			System.out.println("Message Number " + i + " sent.");
		}
	}
}
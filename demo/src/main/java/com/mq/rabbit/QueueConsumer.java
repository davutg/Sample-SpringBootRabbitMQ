package com.mq.rabbit;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;


/**
 * The endpoint that consumes messages off of the queue. Happens to be runnable.
 * @author syntx
 *
 */
@Component
public class QueueConsumer extends EndPoint implements Runnable, Consumer{
	
	public QueueConsumer() throws IOException, TimeoutException
	{
		this("queue");
	}
	public QueueConsumer(@Value("#{queue}") String endPointName) throws IOException, TimeoutException{
		super(endPointName);	
		Thread consumerThread = new Thread(this);
		consumerThread.start();
	}
	
	public void run() {
		try {
			//start consuming messages. Auto acknowledge messages.
			channel.basicConsume(endPointName, true,this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called when consumer is registered.
	 */
	public void handleConsumeOk(String consumerTag) {
		System.out.println("Consumer "+consumerTag +" registered");		
	}

	/**
	 * Called when new message is available.
	 */
	public void handleDelivery(String consumerTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		
		@SuppressWarnings("unchecked")
		Map<String,Integer> map = (Map<String, Integer>) SerializationUtils.deserialize(body);
	    System.out.println("Message Number "+ map.get("message number") + " received.");
		if(map.get("message number")==999999)
		{
			RabbitTest.end=new Date().getTime();
			  System.out.println("1M basic message received in "+(RabbitTest.end-RabbitTest.start)/1000d);
		}
		
	}

	public void handleCancel(String consumerTag) {}
	public void handleCancelOk(String consumerTag) {}
	public void handleRecoverOk(String consumerTag) {}
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException arg1) {}
}
package com.mq.rabbit;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * The producer endpoint that writes to the queue.
 * @author syntx
 *
 */
@Component
public class Producer extends EndPoint{
	public Producer() throws IOException, TimeoutException {
		this("queue");
	}
	public Producer(@Value("#{queue}") String endPointName) throws IOException,TimeoutException{
		super(endPointName);
	}

	public void sendMessage(Serializable object) throws IOException {
	    channel.basicPublish("",endPointName, null, SerializationUtils.serialize(object));
	}	
}
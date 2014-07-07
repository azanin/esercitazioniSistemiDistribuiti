package it.unibo.sdls.sampleproject.logging;

import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


@MessageDriven(name="MDBLogger",
activationConfig= {
		@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destition",propertyValue="queue/LoggingQueue")}
)
public class MDBLogger implements MessageListener {
	
	private Logger logger = Logger.getLogger("MDBLogReceiver");
	
	public MDBLogger() {
		
		
	}

	public void onMessage(Message operation) {
		
		try {
			logger.info("Message arrived: " + ((ObjectMessage)operation).getObject().toString() );
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

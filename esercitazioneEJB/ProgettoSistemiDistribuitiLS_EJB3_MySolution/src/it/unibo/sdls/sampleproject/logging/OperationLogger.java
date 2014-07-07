package it.unibo.sdls.sampleproject.logging;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.Session;

public class OperationLogger {
	
	//send a message to jms provider
	@Resource(mappedName="QueueConectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName="queue/LoggingQueue")
	private Destination destination;
	
	
	@AroundInvoke
	public Object log(InvocationContext invocationContext)
	{
		try {
			//First step: getting a Connection from the ConnectionFactory

			Connection connection = connectionFactory.createConnection();
			//Second step: getting a Session form the Connection
			
			Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			//Third step: getting a MessageProducer from Session
			MessageProducer messageProducer = session.createProducer(destination);
			//Build the Message
			ObjectMessage message = session.createObjectMessage("Invocato il metodo "+invocationContext.getMethod().getName() );
			
			//start the connection
			connection.start();
			
			//send message to destination
			messageProducer.send(message);
			
			//Now businsess method invocation
			Object result = invocationContext.proceed();
			
			//Logical business method ended
			//Intercepted the end of the method
			//Send another message
			
			message = session.createObjectMessage("Invocato con successo il metodo " + invocationContext.getMethod().getName());
			messageProducer.send(message);
			
			//session close
			session.close();
			connection.close();
			return result;			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

package it.unibo.sdls.sampleproject.web;

import it.unibo.sdls.sampleproject.jmx.ManagedDAOFactory;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MBeanLoader implements ServletContextListener{

	private MBeanServer mBeanServer = null;
	private static ObjectName mbeanObjectName = null;
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

		//first check if tomcat is running
		boolean runningOnTomcat = Boolean.parseBoolean(servletContextEvent.getServletContext().getInitParameter("tomcat") );

		if(!runningOnTomcat)
		{
			for(MBeanServer mbs : MBeanServerFactory.findMBeanServer(null))
			{
				mBeanServer = mbs;
				break;
			}
			if(mBeanServer == null)
				mBeanServer = MBeanServerFactory.createMBeanServer();
		}
		else
		{
			// Tomcat doesn't use the platform mbean server, by default!
			mBeanServer = ManagementFactory.getPlatformMBeanServer();
		}


		//create and register DAOfactory MBean

		String domain = mBeanServer.getDefaultDomain();
		String mbeanClassName =	ManagedDAOFactory.class.getCanonicalName();
		String mbeanObjectNameStr =	domain + ":type=" + mbeanClassName + ",index=1";

		try {
			mbeanObjectName = ObjectName.getInstance(mbeanObjectNameStr);

		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		// this works fine in several circumstances, but with the Tomcat hierarchial class loader
		try {
			mBeanServer.createMBean(mbeanClassName, mbeanObjectName);
		} catch(Exception e)
		{
			try {
				mBeanServer.registerMBean(new ManagedDAOFactory(servletContextEvent.getServletContext()), mbeanObjectName);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}



	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		try {
			mBeanServer.unregisterMBean(mbeanObjectName);
		} catch (MBeanRegistrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

package br.com.dmoutinho.messagegateway;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dmoutinho.messagegateway.app.MessageGatewayApplication;
import br.com.dmoutinho.messagegateway.model.Message;
import br.com.dmoutinho.messagegateway.model.ObjectFactory;
import br.com.dmoutinho.messagegateway.utils.Formatter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageGatewayApplication.class)
public class MessageGatewayApplicationTests {

	private ObjectFactory messageFactory;

	private Formatter formatter;
	
	@Before
	public void contextLoads() {
		
		System.out.println("==== MessageGatewayApplicationTests - contextLoads - init ====");
		
		this.messageFactory = new ObjectFactory();
	
		this.formatter = new Formatter();
		
		System.out.println("==== MessageGatewayApplicationTests - contextLoads - end ====");
	
	}

	private Message initMessage() {

		Message message = new Message();
		
		message.setDocumentId("" + Math.random());
		message.setCreationDate(this.formatter.dateToXMLGregorianCalendar(new Date()));
		message.setName("validar-cliente");
		message.setType("request");
		message.setContentType("xml");
		message.setPayload("<xml><test><123456/test></xml>");

		return message;

	}

	private Message toMessage(File file) throws JAXBException {
        return (Message)JAXBContext.newInstance(Message.class).createUnmarshaller().unmarshal(file);
	};
		
	@Test
	public void testMessageMarshal() {

		System.out.println("==== MessageGatewayApplicationTests - testMessageMarshal - init ====");

		try {

			System.out.println("==== message xml: " + this.messageFactory.toXML( this.initMessage() ) + " ====");

		} catch (Exception e) {

			e.printStackTrace();

			fail("==== fail: " + e.getMessage() + " ====");

		}

		System.out.println("==== MessageGatewayApplicationTests - testMessageMarshal - end ====");

	}

	@Test
	public void testMessageUnmarshal() {

		System.out.println("==== MessageGatewayApplicationTests - testMessageUnmarshal - init ====");

		try {

			Arrays.stream(new File("model/sample").listFiles()).forEach((x -> {
				try {
					System.out.println("==== " + toMessage(x));
				} catch (Exception e) {

					e.printStackTrace();

					fail("==== fail: " + e.getMessage() + " ====");
				
				}
			}));

		} catch (Exception e) {

			e.printStackTrace();

			fail("==== fail: " + e.getMessage() + " ====");

		}

		System.out.println("==== MessageGatewayApplicationTests - testMessageUnmarshal - end ====");

	}

}
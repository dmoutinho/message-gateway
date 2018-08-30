package br.com.dmoutinho.messagegateway.app;

import java.util.Arrays;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import br.com.dmoutinho.messagegateway.model.Message;
import br.com.dmoutinho.messagegateway.model.ObjectFactory;

@SpringBootApplication
@EntityScan("br.com.dmoutinho.messagegateway.model")
public class MessageGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessageGatewayApplication.class,args);
	}
}

interface MessageRepository extends JpaRepository<Message,Long> {
}

@Configuration
@EnableJms
class JmsConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        if ( "".equals(user) ) {
            return new ActiveMQConnectionFactory(brokerUrl);
        }
        return new ActiveMQConnectionFactory(user,password,brokerUrl);
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsFactoryTopic(ConnectionFactory connectionFactory,
                                                  DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory,connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplateTopic() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        jmsTemplate.setPubSubDomain( true );
        return jmsTemplate;
    }
    
}

@Component
class MessageGatewayProcessor implements ApplicationRunner {

	@Autowired
	private Environment environment;

	@Autowired
	private MessageRepository messageRepository;
	
	private List<String> name;
	private List<String> type;
	private List<String> contentType;
		
	@JmsListener(destination = "topic.message.gateway", containerFactory = "jmsFactoryTopic")
	public void onReceiverTopic(String msg) {
		
		try {
			
			Message message = new ObjectFactory().toMessage(msg);

			this.messageRepository.save(message);
			
			this.messageRepository.findAll().forEach( x-> System.err.println(x) );
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		System.out.println("==== MessageGatewayConsumer - run - init ====");
		
		this.name = Arrays.asList( this.environment.getProperty("list.name").split(";") );
		this.type = Arrays.asList( this.environment.getProperty("list.type").split(";") );
		this.contentType = Arrays.asList( this.environment.getProperty("list.content-type").split(";") );

		System.out.println("==== MessageGatewayInit - run - name: " + name);
		System.out.println("==== MessageGatewayInit - run - type: " + type);
		System.out.println("==== MessageGatewayInit - run - content-type: " + contentType);
		
		System.out.println("==== MessageGatewayConsumer - run - end ====");
		
	}
    
}
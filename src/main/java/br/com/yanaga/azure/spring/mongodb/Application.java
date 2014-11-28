package br.com.yanaga.azure.spring.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.URI;
import java.util.Arrays;

@Configuration
@ComponentScan
@EnableMongoRepositories
public class Application extends SpringBootServletInitializer {

	@Autowired
	private Environment env;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		URI mongoUrl = new URI(env.getProperty("MONGODB_URL"));
		String host = mongoUrl.getHost();
		int port = mongoUrl.getPort();
		String[] userInfo = mongoUrl.getUserInfo().split(":");
		String userName = userInfo[0];
		char[] password = userInfo[1].toCharArray();
		String databaseName = mongoUrl.getPath().substring(1);
		MongoCredential credential = MongoCredential.createMongoCRCredential(userName, databaseName, password);
		MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
		return new SimpleMongoDbFactory(mongoClient, databaseName);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

}

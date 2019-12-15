package com.mail.app;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mail.app.components.MailConfig;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "com.mail.app" })
@PropertySource("classpath:mailscanner.properties")
public class Application {

	@Value("${deployedurl.appkey}")
	private String DEPLOYED_URL_GMAIL_APP_KEY;

	@Value("${localhosturl.appkey}")
	private String LOCALHOST_URL_GMAIL_APP_KEY;

	@Value("${mail.username}")
	private String fromUser;

	@Value("${smtp.email.type}")
	private String emailHost;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "mailConfig", destroyMethod = "closeMail")
	public MailConfig getMailConfig() {
		MailConfig mailConfig = new MailConfig();
		mailConfig.establishConnectionWithKey(createMailServerProperties());

		return mailConfig;
	}

	private Properties createMailServerProperties() {
		Properties emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", "587");
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.put("mail.smtp.debug", "true");
		emailProperties.put("mail.user", fromUser);
		emailProperties.put("mail.host", emailHost);
		emailProperties.put("mail.appkey", DEPLOYED_URL_GMAIL_APP_KEY);

		return emailProperties;
	}
}

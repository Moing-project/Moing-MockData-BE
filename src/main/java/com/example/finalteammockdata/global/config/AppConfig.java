package com.example.finalteammockdata.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class AppConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    public AppConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate restTemplate(){
        return restTemplateBuilder.build();
    }

    // S3 Config

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    //Java Mail Sender

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private Integer mailPort;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailPassword;
    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailPrTransportProtocol;
    @Value("${spring.mail.properties.mail.debug}")
    private Boolean mailPrDebug;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean mailPrSmtpAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private Boolean mailPrSmtpStartTlsEnable;
    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
    private Boolean mailPrSmtpStartTlsRequired;
//    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
//    private Boolean mailPrSSLEnable;
//    @Value("${spring.mail.properties.mail.smtp.ssl.trust}")
//    private String mailPrSSLTrust;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailPrTransportProtocol);
        props.put("mail.smtp.auth", mailPrSmtpAuth);
        props.put("mail.debug", mailPrDebug);
//        props.put("mail.ssl.enable", mailPrSSLEnable);
//        props.put("mail.ssl.trust", mailPrSSLTrust);
//        TLS 사용
        props.put("mail.smtp.starttls.enable", mailPrSmtpStartTlsEnable);
        props.put("mail.smtp.starttls.required", mailPrSmtpStartTlsRequired);

        return mailSender;
    }
}
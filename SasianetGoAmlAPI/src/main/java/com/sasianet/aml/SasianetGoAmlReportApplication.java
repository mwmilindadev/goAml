package com.sasianet.aml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SasianetGoAmlReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SasianetGoAmlReportApplication.class, args); 
		System.out.println("Working");
	} 
	
	@Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}

package com.labs.iw.service;

import java.io.IOException;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labs.iw.dto.Data;
import com.labs.iw.dto.StudentResponsePojo;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

@Component
public class CircuitBreakerService {
	@Value("${mock_base_url}")
	private String mockBaseUrl;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	RestTemplate restTemplate;
	
   @Autowired
   private CircuitBreakerFactory circuitBreakerFactory;
   	
	public StudentResponsePojo getStudentCircuitBreaker(String url) {
		try {
			String fullUrl = mockBaseUrl + url;
			CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
			
			StudentResponsePojo response = 
					circuitBreaker.run(() -> restTemplate.getForObject(fullUrl, StudentResponsePojo.class),
							 throwable -> getDefaultStudent());
			
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private StudentResponsePojo getDefaultStudent() {
		Long miliseconds = new Date().toInstant().toEpochMilli();
		
		StudentResponsePojo student = new StudentResponsePojo();
		student.setCreationDate(miliseconds.toString());
		
		Data data = new Data();
		data.setCreationDate(miliseconds.toString());
		data.setFirstName("Default Name");
		data.setLastName("Default LastName");
		
		student.setData(data);
		
		return student;
	}

	
	public StudentResponsePojo responseToObject(HttpResponse response) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		return objectMapper.readValue(response.getEntity().getContent(), StudentResponsePojo.class);
	}
	

}

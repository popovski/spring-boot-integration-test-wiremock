package com.labs.iw.service;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.labs.iw.dto.StudentResponsePojo;
import io.netty.handler.timeout.WriteTimeoutException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HttpClientService {
	@Value("${mock_base_url}")
	private String mockBaseUrl;

	@Autowired
	private ObjectMapper objectMapper;
	
	public StudentResponsePojo getStudent(String url) {
		try {
			CloseableHttpClient client = createHttpClient();
			String fullUrl = mockBaseUrl + url;
			HttpGet getMethod = new HttpGet(fullUrl);
			HttpResponse response = client.execute(getMethod);
			System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());
			
			return responseToObject(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
	    @Override
	    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
	   	 System.out.println("****** retry " + executionCount);
	        return executionCount < 5;
	    }
	};
	
	public StudentResponsePojo responseToObject(HttpResponse response) throws JsonParseException, JsonMappingException, UnsupportedOperationException, IOException {
		return objectMapper.readValue(response.getEntity().getContent(), StudentResponsePojo.class);
	}
	
	public RequestConfig createRequestConfig() {
		int timeout = 1;
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000)
				.setSocketTimeout(timeout * 1000)
				.build();
		
		return config;
	}
	
	public CloseableHttpClient createHttpClient() {
		return HttpClientBuilder.create()
				.setDefaultRequestConfig(createRequestConfig())
				.setRetryHandler(requestRetryHandler)
				.build();
	}
}

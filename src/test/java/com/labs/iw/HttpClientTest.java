package com.labs.iw;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.labs.iw.dto.StudentResponse;
import com.labs.iw.service.MockHttpService;
import com.labs.iw.service.MockService;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HttpClientTest {
	@Value("${mock_base_url}")
	String mockBaseUrl;
		
	@Autowired
	private MockHttpService mockHttpService;

	@Test
	void getHttpCall() {
		String url = "http://localhost:9999/api/get-json-normal";
		
		mockHttpService.getHttpRequest(url);
	}
	
	@Test
	void getWithRandomDelayCall() {
		String url = "http://localhost:9999/api/get-json-random-delay";
		
		StudentResponse response =	mockHttpService.getHttpRequest(url);
		
		assertThat(response).isNotNull();
		
	}
	
	@Test
	void getWithFixedDelay() {
		String url = "http://localhost:9999/api/get-json-fix-delay";
		
		StudentResponse response =	mockHttpService.getHttpRequest(url);
		
		assertThat(response).isNull();
		
	}
}

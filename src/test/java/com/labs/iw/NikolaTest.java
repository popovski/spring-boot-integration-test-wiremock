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
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NikolaTest {
	@Value("${mock_base_url}")
	String mockBaseUrl;
		
//	@Autowired
//	private MockService applicationService;

	@Autowired
	private static WireMockServer wireMockServer;

	@BeforeAll
	static void startWireMock() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(9999));

		wireMockServer.start();
	}

	@AfterAll
	static void stopWireMock() {
		wireMockServer.stop();
	}

	@BeforeEach
	void clearWireMock() {
		System.out.println("Stored stubbings: " + wireMockServer.getStubMappings().size());
		wireMockServer.resetAll();
		System.out.println("Stored stubbings after reset: " + wireMockServer.getStubMappings().size());
	}

	@Test
	void testWireMock() {
		System.out.println(wireMockServer.baseUrl());
		assertTrue(wireMockServer.isRunning());
	}



	@Test
	void normalCall() {
		wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/api/read-from-file"))
				.willReturn(aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("mock-api/response_nikola.json")));

		System.out.println(wireMockServer.baseUrl());
		
	//	applicationService.restTemplateReadFromFile();
	}

	@Test
	void failCall() {

	}
}

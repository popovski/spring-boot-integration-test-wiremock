package com.labs.iw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.labs.iw.service.MockService;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class NikolaTest {

	@Autowired
	private MockService applicationService;

	@Test
	void normalCall() {
		applicationService.normalCall();
	}
	
	@Test
	void failCall() {
	//	applicationService.handleClientError();
	}
}

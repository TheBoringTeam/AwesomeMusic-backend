package com.music.awesomemusic;

import com.music.awesomemusic.controllers.UserController;
import com.music.awesomemusic.domain.AwesomeUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleTest {

	@Autowired
	UserController userController;

	@Test
	public void testSumTwoNums() {
		int a = 3;
		int b = 2;
		int sum = 3 + 2;
		assertThat(sum).isEqualTo(5);
	}

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + 8081 + "/";
		URI uri = new URI(baseUrl);

		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

		//Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("Welcome"));
	}
}
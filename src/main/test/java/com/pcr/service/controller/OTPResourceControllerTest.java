/*package com.pcr.service.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OTPResourceControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OTPResourceControllerTest.class);
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	/*@WithMockUser("admin")
	@Test
	public void testGenerateOtp_mockMvc() throws Exception {
		String url = "/api/otp/generate";
		Map<String, String> otpMap = new HashMap<String, String>();
		String jsonRequest = mapper.writeValueAsString(otpMap);
		LOGGER.info("jsonRequest :: " + jsonRequest);
		MvcResult mvcResult = mockMvc
							.perform(post(url).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk()).andReturn();
		assertEquals(200,mvcResult.getResponse().getStatus());
	}
	
	@WithMockUser("test")//we can pass any user for testing with basic auth as its any user can authenticate
	@Test
	public void testValidateOtp_mockMvc() throws Exception {
		String url = "/api/otp/validate";
		Map<String, Object> otpMap = new HashMap<String, Object>();
		otpMap.put("otp", 566875);
		String jsonRequest = mapper.writeValueAsString(otpMap);
		LOGGER.info("jsonRequest :: " + jsonRequest);
		MvcResult mvcResult = mockMvc
							.perform(post(url).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk()).andReturn();
		LOGGER.info("mvcResult :: " + mvcResult.getResponse().getStatus());
		assertEquals(200,mvcResult.getResponse().getStatus());
	}*/
	
	/*@Test
	public void testGenerateOTP() {
		String url = "/api/otp/generate";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<String, String>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestMap, headers);
		ResponseEntity<?> resp = restTemplate.withBasicAuth("admin", "admin")
					.postForEntity(url, request, Object.class);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}*/

	/*
	 * for get type rest api to test with mockMvc
	 MvcResult mvcResult = mockMvc
							.perform(get(url).contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk()).andReturn();
	 */
	/*@Test
	public void testValidateOTP() {
		String url = "/api/otp/validate";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> otpMap = new HashMap<String, Object>();
		otpMap.put("otp", 566875);
		 Map<String, String> response = new HashMap<>(2);
		MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<String, String>();
		requestMap.add("admin",otpMap.get("otp").toString());
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestMap, headers);
		ResponseEntity<Object> resp = restTemplate.withBasicAuth("admin", "admin")
					.postForEntity(url, request, Object.class);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}*/

	

	
//}


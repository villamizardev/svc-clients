package org.interbanking.com;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.interbanking.com.security.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersAndAuthenticationTests {
	@Autowired
	private MockMvc mvc;
	private String token;

	public JSONObject credentials() {
		JSONObject credentials = new JSONObject();
		credentials.put("email", "luis@gmail.com");
		credentials.put("password", "admin");
		return credentials;
	}
	
	public JSONObject credentialsWithoutAuthorization() {
		JSONObject credentials = new JSONObject();
		credentials.put("email", "alejandro@gmail.com");
		credentials.put("password", "admin");
		return credentials;
	}
	
	public JSONObject clientObject() {
		JSONObject jsonClient = new JSONObject();
		jsonClient.put("name", "Maria");
		jsonClient.put("lastname", "Burgos");
		jsonClient.put("registeredname", "SAS");
		jsonClient.put("nit", 0);
		jsonClient.put("email", "lvillamizar@gmail.com");
		jsonClient.put("address", "Portal de la castella");
		jsonClient.put("phonenumber", 0);
		return jsonClient;
	}
	
	public JSONObject clientObjectWithNullProperties() {
		JSONObject jsonClientWithNullProperties = new JSONObject();
		jsonClientWithNullProperties.put("name", null);
		jsonClientWithNullProperties.put("lastname", null);
		jsonClientWithNullProperties.put("registeredname", "SAS");
		jsonClientWithNullProperties.put("nit", 0);
		jsonClientWithNullProperties.put("email", "lvillamizar@gmail.com");
		jsonClientWithNullProperties.put("address", "Portal de la castella");
		jsonClientWithNullProperties.put("phonenumber", 0);
		return jsonClientWithNullProperties;
	}

	@BeforeEach()
	public void newToken() {
		token = TokenUtils.createToken("luis", "luis@gmail.com");
		assertNotNull(token);
	}
	
	@Test
	@Order(1)
	public void generateTokenSendingTheCredentials() throws Exception {
		JSONObject credentials = credentials();
		mvc.perform(MockMvcRequestBuilders.post("/auth").accept(MediaType.APPLICATION_JSON)
		.content(credentials.toString().getBytes()).contentType(MediaType.APPLICATION_JSON).header("idClient", 5)).andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void generateTokenSendingTheFalseCredentials() throws Exception {
		JSONObject credentials = credentialsWithoutAuthorization();
		mvc.perform(MockMvcRequestBuilders.post("/auth").accept(MediaType.APPLICATION_JSON)
		.content(credentials.toString().getBytes()).contentType(MediaType.APPLICATION_JSON).header("idClient", 5)).andExpect(status().isUnauthorized());
	}

	@Test
	@Order(3)
	public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/clients/getClient")).andExpect(status().isForbidden());
	}

	@Test
	@Order(4)
	public void getClientTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/clients/getClient").accept(MediaType.APPLICATION_JSON)
				.header("idClient", 5).header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	public void getAllClientTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/clients/getAllClients").accept(MediaType.APPLICATION_JSON)
				.header("idClient", 5).header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void createNewClient() throws Exception {
		JSONObject jsonClient = clientObject();
		mvc.perform(MockMvcRequestBuilders.post("/clients/createClient").accept(MediaType.APPLICATION_JSON)
				.content(jsonClient.toString().getBytes()).contentType(MediaType.APPLICATION_JSON).header("idClient", 5)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}
	
	@Test
	@Order(7)
	public void updateNewClient() throws Exception {
		JSONObject jsonClient = clientObject();
		mvc.perform(MockMvcRequestBuilders.put("/clients/updateClient").accept(MediaType.APPLICATION_JSON)
				.content(jsonClient.toString().getBytes()).contentType(MediaType.APPLICATION_JSON).header("idClient", 5)
				.header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}
	
	@Test
	@Order(8)
	public void createNewClientWithNullProperties() throws Exception {
		JSONObject jsonClient = clientObjectWithNullProperties();
		mvc.perform(MockMvcRequestBuilders.put("/clients/updateClient").accept(MediaType.APPLICATION_JSON)
				.content(jsonClient.toString().getBytes()).contentType(MediaType.APPLICATION_JSON).header("idClient", 5)
				.header("Authorization", "Bearer " + token)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(9)
	public void updateNewClientWithNullProperties() throws Exception {
		JSONObject jsonClient = clientObjectWithNullProperties();
		mvc.perform(MockMvcRequestBuilders.put("/clients/updateClient").accept(MediaType.APPLICATION_JSON)
				.content(jsonClient.toString().getBytes()).contentType(MediaType.APPLICATION_JSON).header("idClient", 5)
				.header("Authorization", "Bearer " + token)).andExpect(status().isBadRequest());
	}

}

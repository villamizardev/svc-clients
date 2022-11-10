package org.interbanking.com.controllers;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.interbanking.com.entities.Client;
import org.interbanking.com.services.IClientService;
import org.interbanking.com.utils.Checker;
import org.interbanking.com.utils.ClientResponse;
import org.interbanking.com.utils.DefaultResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {
	@Autowired
	IClientService iClientService;

	@GetMapping("/getClient")
	public ResponseEntity<ClientResponse> getClient(@RequestHeader("idClient") int idClient) {
		Optional<Client> client = iClientService.getClient(idClient);
		Supplier<Client> supplier = Client::new;
		Supplier<ClientResponse> clientResponse = ClientResponse::new;
		ClientResponse response = clientResponse.get();

		if (!client.isPresent()) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setMessage(DefaultResponseMessage.MESSAGE_NOT_FOUND);
			response.setClient(supplier.get());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		response.setStatus(HttpStatus.OK);
		response.setMessage(DefaultResponseMessage.MESSAGE_FOUND);
		response.setClient(client.get());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/getAllClients")
	public ResponseEntity<List<Client>> getAllClient() {
		List<Client> clients = iClientService.getAllClients();
		if (clients.size() > 0)
			return ResponseEntity.status(HttpStatus.OK).body(clients);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clients);
	}

	@PostMapping("/createClient")
	public ResponseEntity<ClientResponse> createClient(@RequestBody(required = true) Client client) {
		Supplier<ClientResponse> clientResponse = ClientResponse::new;
		ClientResponse response = clientResponse.get();
		boolean allPropertiesExist = Checker.checkProperties(client);

		if (!allPropertiesExist) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(DefaultResponseMessage.MESSAGE_CREATED_ERROR);
			response.setClient(client);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setStatus(HttpStatus.OK);
		response.setMessage(DefaultResponseMessage.MESSAGE_CREATED_OK);
		response.setClient(iClientService.createClient(client));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("/updateClient")
	public ResponseEntity<ClientResponse> updateClient(@RequestBody Client client) {
		Supplier<ClientResponse> clientResponse = ClientResponse::new;
		ClientResponse response = clientResponse.get();
		boolean allPropertiesExist = Checker.checkProperties(client);
		if (!allPropertiesExist) {
			response.setStatus(HttpStatus.BAD_REQUEST);
			response.setMessage(DefaultResponseMessage.MESSAGE_UPDATED_ERROR);
			response.setClient(client);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setStatus(HttpStatus.OK);
		response.setMessage(DefaultResponseMessage.MESSAGE_UPDATED_OK);
		response.setClient(iClientService.createClient(client));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/deleteClient")
	public ResponseEntity<ClientResponse> deleteClient(@RequestHeader("idClient") int idClient) {
		Optional<Client> client = iClientService.deleteClient(idClient);
		Supplier<ClientResponse> clientResponse = ClientResponse::new;
		ClientResponse response = clientResponse.get();

		if (client.isPresent()) {
			response.setStatus(HttpStatus.OK);
			response.setMessage(DefaultResponseMessage.MESSAGE_CREATED_OK);
			response.setClient(client.get());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setMessage(DefaultResponseMessage.MESSAGE_CREATED_ERROR);
		response.setClient(client.get());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}

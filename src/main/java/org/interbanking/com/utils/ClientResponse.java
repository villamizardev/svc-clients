package org.interbanking.com.utils;

import org.interbanking.com.entities.Client;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponse {
	public HttpStatus status;
	public String message;
	public Client client;
}

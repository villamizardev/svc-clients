package org.interbanking.com.security;

import lombok.Data;

@Data
public class AuthCredentials {
	private String email;
	private String password;
}

package org.interbanking.com.utils;

import java.util.function.Predicate;

import org.interbanking.com.entities.Client;

public class Checker {
	
	public static boolean checkProperties(Client client) {
		Predicate<Client> result = null;
		if(client != null) {
			Predicate<Client> name = (c) -> c.getName() != null;
			Predicate<Client> lastname = (c) -> c.getLastname() != null;
			Predicate<Client> registeredname = (c) -> c.getRegisteredname() != null;
			Predicate<Client> email = (c) -> c.getEmail() != null;
			Predicate<Client> address = (c) -> c.getAddress() != null;
			result = name.and(lastname).and(registeredname).and(email).and(address);
		}else {
			return false;
		}
		
		return result.test(client);
	}
	
}

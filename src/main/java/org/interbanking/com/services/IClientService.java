package org.interbanking.com.services;

import java.util.List;
import java.util.Optional;

import org.interbanking.com.entities.Client;

public interface IClientService {
	public Optional<Client> getClient(int idClient);
	
	public List<Client> getAllClients();
	
	public Client createClient(Client client);
	
	public Client updateClient(Client client);
	
	public Optional<Client> deleteClient(int idClient);
}

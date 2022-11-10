package org.interbanking.com.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.interbanking.com.entities.Client;
import org.interbanking.com.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = { SQLException.class })
public class ClientServiceImpl implements IClientService{
	
	@Autowired
	private ClientRepository iClients;
	
	@Override
	public Optional<Client> getClient(int idClient) {
		Optional<Client> opClient = iClients.findById(idClient);
		return opClient;
	}

	@Override
	public List<Client> getAllClients() {
		return iClients.findAll();
	}

	@Override
	public Client createClient(Client client) {
		return iClients.save(client);
	}

	@Override
	public Client updateClient(Client client) {
		return iClients.save(client);
	}

	@Override
	public Optional<Client> deleteClient(int idClient) {
		Optional<Client> opClient = iClients.findById(idClient);
		if(opClient.isPresent())
			iClients.deleteById(idClient);
		return opClient;
	}
}

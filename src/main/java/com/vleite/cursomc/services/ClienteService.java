package com.vleite.cursomc.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.dto.ClienteDTO;
import com.vleite.cursomc.repositories.ClienteRepository;
import com.vleite.cursomc.services.exceptions.DataIntegrityException;
import com.vleite.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getCanonicalName())));
	}

	public Collection<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente toCliente(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail());
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Cliente update(Cliente obj) {
		Cliente client = find(obj.getId());
		updateClient(client, obj);
		return repository.save(client);
	}

	private void updateClient(Cliente client, Cliente obj) {
		client.setNome(obj.getNome());
		client.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new DataIntegrityException("Não é possível excluir pois há entidades relacionadas.");
		}
	}

	public Page<Cliente> findPage(int page, int size, String direction, String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), sortBy);
		return repository.findAll(pageRequest);
	}
}

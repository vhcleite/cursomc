package com.vleite.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.dto.ClienteDTO;
import com.vleite.cursomc.dto.NewClienteDTO;
import com.vleite.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody NewClienteDTO obj) {
		Cliente cliente = service.insert(service.toCliente(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")//
				.buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto) {
		objDto.setId(id);
		service.update(service.toCliente(objDto));
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> dtoList = service.findAll().stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(dtoList);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(//
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "24") int size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "sortBy", defaultValue = "nome") String sortBy) {
		Page<ClienteDTO> dtoList = service.findPage(page, size, direction, sortBy).map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(dtoList);
	}

}

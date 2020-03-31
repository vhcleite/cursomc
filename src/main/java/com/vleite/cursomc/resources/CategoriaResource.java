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

import com.vleite.cursomc.domain.Categoria;
import com.vleite.cursomc.dto.CategoriaDTO;
import com.vleite.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO obj) {
		Categoria categoria = service.insert(service.toCategoria(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")//
				.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDto) {
		objDto.setId(id);
		service.update(service.toCategoria(objDto));
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> dtoList = service.findAll().stream().map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(dtoList);
	}

	@RequestMapping(value = "page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(//
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "24") int size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "sortBy", defaultValue = "nome") String sortBy) {
		Page<CategoriaDTO> dtoList = service.findPage(page, size, direction, sortBy).map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(dtoList);
	}

}

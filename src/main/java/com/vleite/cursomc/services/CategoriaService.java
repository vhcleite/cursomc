package com.vleite.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vleite.cursomc.domain.Categoria;
import com.vleite.cursomc.repositories.CategoriaRepository;
import com.vleite.cursomc.services.exceptions.DataIntegrityException;
import com.vleite.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repository;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Categoria.class.getCanonicalName())));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); 
		return repository.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		
		try {
			repository.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria com produtos.");
		}
	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Page<Categoria> findPage(int page, int size, String direction, String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), sortBy);
		return repository.findAll(pageRequest);
	}
	
}

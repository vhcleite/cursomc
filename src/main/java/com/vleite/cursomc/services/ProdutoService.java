package com.vleite.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vleite.cursomc.domain.Categoria;
import com.vleite.cursomc.domain.Produto;
import com.vleite.cursomc.repositories.CategoriaRepository;
import com.vleite.cursomc.repositories.ProdutoRepository;
import com.vleite.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository productRepository;
	
	@Autowired
	CategoriaRepository categoryRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = productRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Produto.class.getCanonicalName())));
	}
	
	public Page<Produto> search(String name, List<Integer> categoryIds, int page, int size, String direction, String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), sortBy);
		List<Categoria> categories = categoryRepository.findAllById(categoryIds);
		return productRepository.findDistinctByNomeContainsAndCategoriasIn(name, categories, pageRequest);
	}

	public List<Produto> findAll() {
		return productRepository.findAll();
	}
}

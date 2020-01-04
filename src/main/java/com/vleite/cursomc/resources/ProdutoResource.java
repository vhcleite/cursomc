package com.vleite.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vleite.cursomc.domain.Produto;
import com.vleite.cursomc.dto.ProdutoDTO;
import com.vleite.cursomc.services.ProdutoService;
import com.vleite.cursomc.utils.UrlUtils;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	ProdutoService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto categoria = productService.find(id);
		return ResponseEntity.ok().body(categoria);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoDTO>> findAll() {
		List<ProdutoDTO> dtoList = productService.findAll().stream().map(obj -> new ProdutoDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(dtoList);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> search(//
			@RequestParam(value = "nome", defaultValue = "") String name,
			@RequestParam(value = "categorias", defaultValue = "") String categoryList,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "24") int size,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "sortBy", defaultValue = "nome") String sortBy) {
		Page<ProdutoDTO> dtoList = productService.search(UrlUtils.decodeParam(name),
				UrlUtils.parseIntegerList(categoryList), page, size, direction, sortBy).map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(dtoList);
	}

}

package com.vleite.cursomc;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vleite.cursomc.domain.Categoria;
import com.vleite.cursomc.domain.Cidade;
import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.domain.Endereco;
import com.vleite.cursomc.domain.Estado;
import com.vleite.cursomc.domain.Produto;
import com.vleite.cursomc.domain.enums.TipoCliente;
import com.vleite.cursomc.repositories.CategoriaRepository;
import com.vleite.cursomc.repositories.CidadeRepository;
import com.vleite.cursomc.repositories.ClienteRepository;
import com.vleite.cursomc.repositories.EnderecoRepository;
import com.vleite.cursomc.repositories.EstadoRepository;
import com.vleite.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "computador", 2000.0);
		Produto p2 = new Produto(null, "impressora", 800.0);
		Produto p3 = new Produto(null, "mouse", 80.0);

		cat1.getProdutos().addAll(asList(p1, p2, p3));
		cat2.getProdutos().addAll(asList(p2));
		
		p1.getCategorias().addAll(asList(cat1));
		p2.getCategorias().addAll(asList(cat1, cat2));
		p3.getCategorias().addAll(asList(cat1));
		
		
		
		categoriaRepository.saveAll(asList(cat1, cat2));
		produtoRepository.saveAll(asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(asList(cid1));
		est2.getCidades().addAll(asList(cid2, cid3));
		
		estadoRepository.saveAll(asList(est1, est2));
		cidadeRepository.saveAll(asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null,  "Maria Silva", "maria@gmail.com", "44444444444", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(asList("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);
		
		cli1.getEnderecos().addAll(asList(e1, e2));
		
		clienteRepository.saveAll(asList(cli1));
		enderecoRepository.saveAll(asList(e1, e2));	
	}

}

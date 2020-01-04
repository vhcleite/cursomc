package com.vleite.cursomc;

import static java.util.Arrays.asList;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vleite.cursomc.domain.Categoria;
import com.vleite.cursomc.domain.Cidade;
import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.domain.Endereco;
import com.vleite.cursomc.domain.Estado;
import com.vleite.cursomc.domain.ItemPedido;
import com.vleite.cursomc.domain.Pagamento;
import com.vleite.cursomc.domain.PagamentoComBoleto;
import com.vleite.cursomc.domain.PagamentoComCartao;
import com.vleite.cursomc.domain.Pedido;
import com.vleite.cursomc.domain.Produto;
import com.vleite.cursomc.domain.enums.EstadoPagamento;
import com.vleite.cursomc.domain.enums.TipoCliente;
import com.vleite.cursomc.repositories.CategoriaRepository;
import com.vleite.cursomc.repositories.CidadeRepository;
import com.vleite.cursomc.repositories.ClienteRepository;
import com.vleite.cursomc.repositories.EnderecoRepository;
import com.vleite.cursomc.repositories.EstadoRepository;
import com.vleite.cursomc.repositories.ItemPedidoRepository;
import com.vleite.cursomc.repositories.PagamentoRepository;
import com.vleite.cursomc.repositories.PedidoRepository;
import com.vleite.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Quarto");
		Categoria cat4 = new Categoria(null, "Cozinha");
		Categoria cat5 = new Categoria(null, "Sala");
		Categoria cat6 = new Categoria(null, "Sala de jantar");
		Categoria cat7 = new Categoria(null, "Lavanderia");
		Categoria cat8 = new Categoria(null, "Jardinagem");
		Categoria cat9 = new Categoria(null, "Iluminição");
		Categoria cat10 = new Categoria(null, "Banheiro");

		Produto p1 = new Produto(null, "computador", 2000.0);
		Produto p2 = new Produto(null, "impressora", 800.0);
		Produto p3 = new Produto(null, "mouse", 80.0);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.0);
		Produto p5 = new Produto(null, "Toalha", 50.0);
		Produto p6 = new Produto(null, "Colcha", 200.0);
		Produto p7 = new Produto(null, "TV True Color", 1200.0);
		Produto p8 = new Produto(null, "Roçadeira", 800.0);
		Produto p9 = new Produto(null, "Abajour", 100.0);
		Produto p10 = new Produto(null, "Pendente", 180.0);
		Produto p11 = new Produto(null, "Shampoo", 90.0);

		cat1.getProdutos().addAll(asList(p1, p2, p3));
		cat2.getProdutos().addAll(asList(p2, p4));
		cat3.getProdutos().addAll(asList(p5, p6));
		cat4.getProdutos().addAll(asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(asList(p8));
		cat6.getProdutos().addAll(asList(p9, p10));
		cat7.getProdutos().addAll(asList(p11));

		p1.getCategorias().addAll(asList(cat1, cat4));
		p2.getCategorias().addAll(asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(asList(cat1, cat4));
		p4.getCategorias().addAll(asList(cat2));
		p5.getCategorias().addAll(asList(cat3));
		p6.getCategorias().addAll(asList(cat3));
		p7.getCategorias().addAll(asList(cat4));
		p8.getCategorias().addAll(asList(cat5));
		p9.getCategorias().addAll(asList(cat6));
		p10.getCategorias().addAll(asList(cat6));
		p11.getCategorias().addAll(asList(cat7));

		categoriaRepository.saveAll(asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10));
		produtoRepository.saveAll(asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(asList(cid1));
		est2.getCidades().addAll(asList(cid2, cid3));

		estadoRepository.saveAll(asList(est1, est2));
		cidadeRepository.saveAll(asList(cid1, cid2, cid3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "44444444444", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);

		cli1.getEnderecos().addAll(asList(e1, e2));

		clienteRepository.saveAll(asList(cli1));
		enderecoRepository.saveAll(asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(asList(ped1, ped2));

		pedidoRepository.saveAll(asList(ped1, ped2));
		pagamentoRepository.saveAll(asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p2, 0.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

		ped1.getItens().addAll(asList(ip1, ip2));
		ped2.getItens().addAll(asList(ip3));

		p1.getItens().addAll(asList(ip1, ip2));
		p2.getItens().addAll(asList(ip2, ip3));

		itemPedidoRepository.saveAll(asList(ip1, ip2, ip3));

	}

}

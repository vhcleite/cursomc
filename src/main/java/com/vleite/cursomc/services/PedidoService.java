package com.vleite.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.domain.ItemPedido;
import com.vleite.cursomc.domain.PagamentoComBoleto;
import com.vleite.cursomc.domain.Pedido;
import com.vleite.cursomc.domain.enums.EstadoPagamento;
import com.vleite.cursomc.repositories.ItemPedidoRepository;
import com.vleite.cursomc.repositories.PagamentoRepository;
import com.vleite.cursomc.repositories.PedidoRepository;
import com.vleite.cursomc.security.UserSS;
import com.vleite.cursomc.services.exceptions.AuthorizationException;
import com.vleite.cursomc.services.exceptions.ObjectNotFoundException;
import com.vleite.cursomc.services.interfaces.IEmailService;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private IEmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Pedido.class.getCanonicalName())));
	}
	
	public Page<Pedido> findPage(int page, int size, String direction, String sortBy) {
		UserSS user = UserService.authenticated();
		if(user == null) {
		 throw new AuthorizationException("Acesso negado");
		}
		Cliente cliente = clienteService.find(user.getId());
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), sortBy);
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstante(new Date());
		
		obj.setCliente(clienteService.find(obj.getCliente().getId()));

		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) obj.getPagamento();
			boletoService.fillDataVencimento(pagamento, obj.getInstante());
		}
		
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		emailService.sendOrderConfirmationEmail(obj);
		
		return obj;
	}
}

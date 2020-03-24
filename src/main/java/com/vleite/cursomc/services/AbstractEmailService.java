package com.vleite.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.vleite.cursomc.domain.Pedido;
import com.vleite.cursomc.services.interfaces.IEmailService;

public abstract class AbstractEmailService implements IEmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage msg = prepareSimpleMailMessageFromPedido(pedido);
		sendEmail(msg);
	}

	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(sender);
		msg.setTo(pedido.getCliente().getEmail());
		msg.setSubject("Pedido Confirmado! Código: " + pedido.getId());
		msg.setSentDate(new Date(System.currentTimeMillis()));
		msg.setText(pedido.toString());
		
		return msg;
	}

}

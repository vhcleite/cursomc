package com.vleite.cursomc.services.interfaces;

import org.springframework.mail.SimpleMailMessage;

import com.vleite.cursomc.domain.Pedido;

public interface IEmailService {
	
	void sendOrderConfirmationEmail(Pedido pedido);
	void sendEmail(SimpleMailMessage msg);

}

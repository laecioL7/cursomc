package com.example.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.example.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService
{

	// obtem o valor do remetente da configuração
	@Value("${default.sender}")
	private String sender;

	/**Chama o metodo de envio, mas como a classe é abstrata a implementação do metodo
	 * será feita posteriormente na classe que herdar dessa classe*/
	@Override
	public void sendOrderConfirmationEmail(Pedido obj)
	{
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	//prepara os dados do email para envio
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj)
	{
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		
		return sm;
	}
}

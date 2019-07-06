package com.example.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.cursomc.domain.PagamentoComBoleto;

/**
 * Classe que seria responsavel por acessar um servico que retornaria um boleto
 * gerado com os dados referente a ele como a data de vencimento
 */
@Service
public class BoletoService
{
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		
		//adiciona 7 dias a mais na data
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}

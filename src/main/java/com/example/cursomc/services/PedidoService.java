package com.example.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.ItemPedido;
import com.example.cursomc.domain.PagamentoComBoleto;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.domain.enums.EstadoPagamento;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.ItemPedidoRepository;
import com.example.cursomc.repositories.PagamentoRepository;
import com.example.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService
{
	// automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;

	/** Busca uma categoria dado um ID */
	public Pedido buscar(Integer iId)
	{
		Optional<Pedido> objPedido = null;

		// chama a Da para buscar uma categoria no banco de acordo com o ID
		objPedido = pedidoRepository.findById(iId);

		// retorna o objeto se achou, se não retorna um erro personalizado
		return objPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + iId + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj)
	{
		//força ser nulo
		obj.setId(null);
		
		//define a data de criação
		obj.setInstante(new Date());
		
		//grava o cliente do pedido
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		
		//define o status de pagamento
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		
		//associa o pedido ao objeto de pagamento
		obj.getPagamento().setPedido(obj);
		
		//se for pagamento com boleto
		if (obj.getPagamento() instanceof PagamentoComBoleto)
		{
			//converte o Pagamento para um objeto pagamento com boleto
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			
			//obtem os dados do boleto e preenche
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		//chama a DA para salvar o pedido no banco
		obj = pedidoRepository.save(obj);
		
		//chama a DA para salvar o pagamento no banco
		pagamentoRepository.save(obj.getPagamento());
		
		//para cada item dentro do pedido
		for (ItemPedido ip : obj.getItens())
		{
			//monta os dados do item
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		//salva todos os itens do pedido no banco
		itemPedidoRepository.saveAll(obj.getItens());
		
		//envia o email com os dados do pedido
		emailService.sendOrderConfirmationEmail(obj);
		
		//retorna o objeto pedido
		return obj;
	}
}

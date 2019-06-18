package com.example.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService
{
	//automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private PedidoRepository pedidoRepository;

	/**Busca uma categoria dado um ID*/
	public Pedido buscar(Integer iId)
	{
		Optional<Pedido> objPedido = null;
		
		//chama a Da para buscar uma categoria no banco de acordo com o ID
		objPedido = pedidoRepository.findById(iId);
		
		//retorna o objeto se achou, se não retorna um erro personalizado
		return objPedido.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + iId + ", Tipo: " + Pedido.class.getName()));
	}
}

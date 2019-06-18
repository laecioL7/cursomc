package com.example.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.services.PedidoService;

/**É o mesmo que uma RN para Pedido*/
@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource
{
	@Autowired
	private PedidoService pedidoService;
	
	/**Recebe um id da requisição para retornar uma pedido*/
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id)
	{
		Pedido pedido = null;
		
		//chama a rn para buscar a pedido no banco
		pedido = pedidoService.buscar(id);
		
		//retorna a busca
		return ResponseEntity.ok().body(pedido);
	}
}

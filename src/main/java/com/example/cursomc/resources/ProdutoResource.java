package com.example.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Produto;
import com.example.cursomc.dto.ProdutoDTO;
import com.example.cursomc.services.ProdutoService;

import util.Apoio;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource
{
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) 
	{
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction)
	{
		//quando o nome tem espaço ele vem com um caracter no lugar então faz o decode dele
		String nomeDecoded = Apoio.decodeParam(nome);
		
		//transforma a lista de strings em lista de integer
		List<Integer> ids = Apoio.decodeIntList(categorias);
		
		//chama o metodo de busca da rn passando os parametros
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		//para cada objeto da lista instancia um novo objeto ProdutodDto
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));  
		
		return ResponseEntity.ok().body(listDto);
	}
}

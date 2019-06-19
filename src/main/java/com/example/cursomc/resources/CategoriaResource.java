package com.example.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.services.CategoriaService;

/**É o mesmo que uma REST*/
@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource
{
	@Autowired
	private CategoriaService categoriaService;
	
	/**Recebe um id da requisição para retornar uma categoria*/
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id)
	{
		Categoria categoria = null;
		
		//chama a rn para buscar a categoria no banco
		categoria = categoriaService.find(id);
		
		//retorna a busca
		return ResponseEntity.ok().body(categoria);
	}
	
	/**Recebe uma categoria no formato json e salva no banco de dados
	 * @param requestBody : faz o objeto ser convertido para json automaticamente*/
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj)
	{
		/*chama a RN para salvar lidar com a dao e salvar o objeto
		*criando um novo ID no banco automaticamente*/
		obj = categoriaService.insert(obj);
		
		//pega a uri da requisição e junta com a uri do novo recurso que foi inserido para a resposta
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		//manda a resposta
		return ResponseEntity.created(uri).build();
	}
	
	/**Recebe uma categoria no formato json e ATUALIZA no banco de dados*/
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id)
	{
		//força o id da url no objeto
		obj.setId(id);
		
		//chama a rn para atualizar o objeto que já tem um ID
		obj = categoriaService.update(obj);
		
		//retorna uma resposta vazia
		return ResponseEntity.noContent().build();
	}
	
	/**Recebe um id da requisição para deletar uma categoria*/
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Categoria> delete(@PathVariable Integer id)
	{	
		//chama a rn para deletar a categoria no banco
		categoriaService.delete(id);
		
		//retorna uma resposta vazia
		return ResponseEntity.noContent().build();
	}
}

package com.example.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.cursomc.domain.Categoria;
import com.example.cursomc.services.CategoriaService;

/**É o mesmo que uma RN para Categoria*/
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
		categoria = categoriaService.buscar(id);
		
		//retorna a busca
		return ResponseEntity.ok().body(categoria);
	}
}

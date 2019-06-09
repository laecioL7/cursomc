package com.example.cursomc.resources;
import java.awt.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource
{
	@RequestMapping(method=RequestMethod.GET)
	public ArrayList<Categoria> listar()
	{
		//return "Rest esta funcionando!";
		Categoria cat1 = new Categoria(1,"Informatica");
		Categoria cat2 = new Categoria(2, "Escritorio");
		
		ArrayList<Categoria> lista = new ArrayList<Categoria>();
		
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
}

package com.example.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService
{
	//automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private CategoriaRepository categoriaRepository;

	/**Busca uma categoria dado um ID*/
	public Categoria buscar(Integer iId)
	{
		Optional<Categoria> objCategoria = null;
		
		//chama a Da para buscar uma categoria no banco de acordo com o ID
		objCategoria = categoriaRepository.findById(iId);
		
		//retorna o objeto se achou, se não retorna um erro personalizado
		return objCategoria.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + iId + ", Tipo: " + Categoria.class.getName()));
	}
}

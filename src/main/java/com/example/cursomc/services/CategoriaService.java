package com.example.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.CategoriaRepository;

/**é o mesmo que um RN para categoria*/
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
	
	/**Insere uma categoria*/
	public Categoria insert(Categoria categoria)
	{
		//força o id ser nulo para não ocorrer update e sim inserção
		categoria.setId(null);
		
		//chama a dao para salvar o objeto retornando o objeto resposta
		return categoriaRepository.save(categoria);
	}
}

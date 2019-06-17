package com.example.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService
{
	//automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private ClienteRepository categoriaRepository;

	/**Busca uma categoria dado um ID*/
	public Cliente buscar(Integer iId)
	{
		Optional<Cliente> objCategoria = null;
		
		//chama a Da para buscar uma categoria no banco de acordo com o ID
		objCategoria = categoriaRepository.findById(iId);
		
		//retorna o objeto se achou, se não retorna um erro personalizado
		return objCategoria.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + iId + ", Tipo: " + Cliente.class.getName()));
	}
}

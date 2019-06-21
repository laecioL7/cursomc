package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.exceptions.DataIntegrityException;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.ClienteRepository;

@Service
public class ClienteService
{
	// automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private ClienteRepository repo;

	/** Busca uma categoria dado um ID */
	public Cliente find(Integer iId)
	{
		Optional<Cliente> objCliente = null;

		// chama a Da para buscar uma categoria no banco de acordo com o ID
		objCliente = repo.findById(iId);

		// retorna o objeto se achou, se não retorna um erro personalizado
		return objCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + iId + ", Tipo: " + Cliente.class.getName()));
	}

	// atualiza os dados de um cliente no banco
	public Cliente update(Cliente obj)
	{
		// busca o cliente no banco e preenche no objeto
		Cliente newObj = find(obj.getId());
		// atualiza no banco o novo objeto mantendo os dados que não mudaram e passando
		// os novos
		updateData(newObj, obj);

		// retorna o cliente como resultado
		return repo.save(newObj);
	}

	public void delete(Integer id)
	{
		// chama o metodo de busca aqui pois se der algum erro será capturado fora do
		// metodo
		find(id);

		try
		{
			//realiza a exclusão
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	//busca todos os dados sem filtro nenhum
	public List<Cliente> findAll()
	{
		return repo.findAll();
	}

	//busca os dados com limite para paginação e parametros de filtro
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	//transforma um clienteDTO em um cliente normal
	public Cliente fromDTO(ClienteDTO clienteDto)
	{
		Cliente cliente = null;
		
		//inicia o cliente
		cliente = new Cliente();
		
		//define os dados
		cliente.setId(clienteDto.getId());
		cliente.setNome(clienteDto.getNome());
		cliente.setEmail(clienteDto.getEmail());
		
		return cliente;
	}

	//atualiza os dados do objeto no novo
	private void updateData(Cliente newObj, Cliente obj)
	{
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}

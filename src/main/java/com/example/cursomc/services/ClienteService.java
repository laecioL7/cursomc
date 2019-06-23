package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.ClienteNovoDTO;
import com.example.cursomc.domain.Endereco;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.exceptions.DataIntegrityException;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.repositories.EnderecoRepository;

@Service
public class ClienteService
{
	// automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	/** Busca uma categoria dado um ID */
	public Cliente find(Integer iId)
	{
		Optional<Cliente> objCliente = null;

		// chama a Da para buscar uma categoria no banco de acordo com o ID
		objCliente = clienteRepository.findById(iId);

		// retorna o objeto se achou, se não retorna um erro personalizado
		return objCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + iId + ", Tipo: " + Cliente.class.getName()));
	}

	/** Insere um novo cliente no banco */
	@Transactional
	public Cliente insert(Cliente obj)
	{
		// define o id como nulo para não atualizar
		obj.setId(null);

		// salva o cliente
		obj = clienteRepository.save(obj);

		// salva os enderecos do cliente
		enderecoRepository.saveAll(obj.getEnderecos());

		// retorna o objeto
		return obj;
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
		return clienteRepository.save(newObj);
	}

	public void delete(Integer id)
	{
		// chama o metodo de busca aqui pois se der algum erro será capturado fora do
		// metodo
		find(id);

		try
		{
			// realiza a exclusão se não tiver pedidos atrelados
			clienteRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	// busca todos os dados sem filtro nenhum
	public List<Cliente> findAll()
	{
		return clienteRepository.findAll();
	}

	// busca os dados com limite para paginação e parametros de filtro
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	// transforma um clienteDTO em um cliente normal
	public Cliente fromDTO(ClienteDTO clienteDto)
	{
		Cliente cliente = null;

		// inicia o cliente
		cliente = new Cliente();

		// define os dados
		cliente.setId(clienteDto.getId());
		cliente.setNome(clienteDto.getNome());
		cliente.setEmail(clienteDto.getEmail());

		return cliente;
	}

	// conversão do dto de cliente para inserção
	public Cliente fromDTO(ClienteNovoDTO clienteNovoDto)
	{
		// cria um objeto cliente com base nos dados do dto
		Cliente cli = new Cliente(null, clienteNovoDto.getNome(), clienteNovoDto.getEmail(),
				clienteNovoDto.getCpfOuCnpj(), TipoCliente.toEnum(clienteNovoDto.getTipo()), "");

		// Cria uma nova cidade com o id que está no clienteDto
		Cidade cid = new Cidade(clienteNovoDto.getCidadeId(), null, null);

		// instancia um endereco com os dados gravados no dto
		Endereco end = new Endereco(null, clienteNovoDto.getLogradouro(), clienteNovoDto.getNumero(),
				clienteNovoDto.getComplemento(), clienteNovoDto.getBairro(), clienteNovoDto.getCep(), cli, cid);

		//adiciona na lista de endereço do cliente
		cli.getEnderecos().add(end);

		
		//adiciona o primeiro telefone obrigatorio
		cli.getTelefones().add(clienteNovoDto.getTelefone1());

		/*verifica se tem outros telefones
		se tiver guarda na lista de endereços*/
		if (clienteNovoDto.getTelefone2() != null)
		{
			cli.getTelefones().add(clienteNovoDto.getTelefone2());
		}
		if (clienteNovoDto.getTelefone3() != null)
		{
			cli.getTelefones().add(clienteNovoDto.getTelefone3());
		}
		
		//retorna o cliente
		return cli;
	}

	// atualiza os dados do objeto no novo
	private void updateData(Cliente newObj, Cliente obj)
	{
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}

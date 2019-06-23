package com.example.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.ClienteNovoDTO;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.exceptions.FieldMessage;
import com.example.cursomc.repositories.ClienteRepository;

import util.Apoio;

/**Validador personalizado para ser utilizado no lugar do @Valid padrão*/
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNovoDTO>
{
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann)
	{
	}

	@Override
	public boolean isValid(ClienteNovoDTO clienteNovoDto, ConstraintValidatorContext context)
	{
		Cliente clienteAux = null;
		List<FieldMessage> listaErros = new ArrayList<>();

		//valida o cpf do cliente pessoa fisica
		if (clienteNovoDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !Apoio.isValidCPF(clienteNovoDto.getCpfOuCnpj()))
		{
			listaErros.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		//valida o cpf do cliente pessoa juridica
		if (clienteNovoDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !Apoio.isValidCNPJ(clienteNovoDto.getCpfOuCnpj()))
		{
			listaErros.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		//verifica se já existe cliente com o email cadastrado
		clienteAux = clienteRepository.findByEmail(clienteNovoDto.getEmail());
		
		//se retornou cliente é porque o email já existe
		if (clienteAux != null) 
		{
			listaErros.add(new FieldMessage("email", "Email já existente"));
		}
		

		//adiciona a lista de erros personalizados na lista de erros padrão do framework
		for (FieldMessage e : listaErros)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		//se a lista estiver vazia não teve erros então retorna true, se não retorna false
		return listaErros.isEmpty();
	}
}

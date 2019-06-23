package com.example.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.example.cursomc.domain.Cliente;
import com.example.cursomc.dto.ClienteDTO;
import com.example.cursomc.exceptions.FieldMessage;
import com.example.cursomc.repositories.ClienteRepository;

/**Validador personalizado para ser utilizado no lugar do @Valid padrão*/
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>
{
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	HttpServletRequest request;

	@Override
	public void initialize(ClienteUpdate ann)
	{
	}

	@Override
	public boolean isValid(ClienteDTO clienteDto, ConstraintValidatorContext context)
	{
		Cliente clienteAux = null;
		List<FieldMessage> listaErros = new ArrayList<>();
		Integer uriId = null;
		
		//obtem os parametros passados na requisição via url para pegar o id do cliente
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		//obtem o id da url
		uriId = Integer.parseInt(map.get("id"));
		
		//verifica se já existe cliente com o email cadastrado
		clienteAux = clienteRepository.findByEmail(clienteDto.getEmail());
		
		//se retornou cliente e o id do cliente atual for diferente do dono do email é porque o email já existe com outro usuário
		if (clienteAux != null && !clienteAux.getId().equals(uriId)) 
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

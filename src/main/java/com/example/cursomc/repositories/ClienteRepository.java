package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.cursomc.domain.Cliente;

/**Automaticamente implementa todos os metodos de acesso a dados*/
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>
{

	//gera automaticamente um metodo que busca um objeto pelo atributo depois do "findBy"
	@Transactional(readOnly = true)
	Cliente findByEmail(String sEmail);
	
}

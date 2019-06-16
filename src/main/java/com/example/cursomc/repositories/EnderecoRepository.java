package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Endereco;
import com.example.cursomc.domain.Estado;

/**Automaticamente implementa todos os metodos de acesso a dados*/
@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>
{

}

package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cursomc.domain.Categoria;

/**Automaticamente implementa todos os metodos de acesso a dados (DAO)*/
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>
{

}

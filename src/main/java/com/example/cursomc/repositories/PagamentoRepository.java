package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cursomc.domain.Pagamento;

/**Automaticamente implementa todos os metodos de acesso a dados*/
@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>
{

}

package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cursomc.domain.Pedido;

/**Automaticamente implementa todos os metodos de acesso a dados*/
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>
{

}

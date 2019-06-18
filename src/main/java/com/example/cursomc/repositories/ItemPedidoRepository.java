package com.example.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cursomc.domain.ItemPedido;

/**Automaticamente implementa todos os metodos de acesso a dados*/
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>
{

}

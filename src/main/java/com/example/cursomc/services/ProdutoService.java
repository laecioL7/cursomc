package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.domain.Produto;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.repositories.ProdutoRepository;

/**é o mesmo que um RN para Produtod*/
@Service
public class ProdutoService
{
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	/**Busca uma categoria dado um ID*/
	public Produto find(Integer iId)
	{
		Optional<Produto> objProduto = null;
		
		//chama a Da para buscar um produto no banco de acordo com o ID
		objProduto = produtoRepository.findById(iId);
		
		//retorna o objeto se achou, se não retorna um erro personalizado
		return objProduto.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + iId + ", Tipo: " + Produto.class.getName()));
	}
	
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction)
	{
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
}

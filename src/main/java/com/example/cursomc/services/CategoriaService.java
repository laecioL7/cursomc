package com.example.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.dto.CategoriaDTO;
import com.example.cursomc.exceptions.DataIntegrityException;
import com.example.cursomc.exceptions.ObjectNotFoundException;
import com.example.cursomc.repositories.CategoriaRepository;

/**é o mesmo que um RN para categoria*/
@Service
public class CategoriaService
{
	//automaticamente instanciado pela injeção de dependência do spring boot
	@Autowired
	private CategoriaRepository categoriaRepository;

	/**Busca uma categoria dado um ID*/
	public Categoria find(Integer iId)
	{
		Optional<Categoria> objCategoria = null;
		
		//chama a Da para buscar uma categoria no banco de acordo com o ID
		objCategoria = categoriaRepository.findById(iId);
		
		//retorna o objeto se achou, se não retorna um erro personalizado
		return objCategoria.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + iId + ", Tipo: " + Categoria.class.getName()));
	}
	
	/**Insere uma categoria*/
	public Categoria insert(Categoria categoria)
	{
		//força o id ser nulo para não ocorrer update e sim inserção
		categoria.setId(null);
		
		//chama a dao para salvar o objeto retornando o objeto resposta
		return categoriaRepository.save(categoria);
	}
	

	/**Atualiza uma categoria que tenha id*/
	public Categoria update(Categoria categoria)
	{
		//verifica se o objeto já existe no banco e se não existir, o metodo lança uma excessao para fora do metodo
		Categoria categoriaTemp = find(categoria.getId());
		
		//atualiza os novos dados para o objeto que veio preenchido do banco
		updateData(categoriaTemp, categoria);
		
		//chama a dao para atualizar o objeto retornando o objeto resposta
		return categoriaRepository.save(categoriaTemp);
	}
	
	/**Recebe um id e deleta no banco*/
	public void delete(Integer id)
	{
		try
		{
		//verifica se o objeto já existe no banco e se não existir, o metodo lança uma excessao para fora do metodo
		find(id);
		
		//chama a dao para excluir
		categoriaRepository.deleteById(id);
		}
		catch (DataIntegrityViolationException err) 
		{
			//lança a excessão personalizada com a mensagem
			throw new DataIntegrityException("Não é possível excluir uma categoria com produtos!");
		}
	}
	
	/*busca todas as categorias no banco*/
	public List<Categoria> findAll()
	{
		//busca no banco e retorna a lista de categorias
		return categoriaRepository.findAll();
	}
	
	/*busca todas as categorias no banco com limite de resulttados com paginação*/
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String sOrderBy, String sOrdem)
	{
		//monta os filtros
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(sOrdem), sOrderBy);
		
		//realiza a busca e retorna
		return categoriaRepository.findAll(pageRequest);
	}
	
	/**Retorna um objeto categoria a partir de um obeto categoriaDTO*/
	public static Categoria fromDTO(CategoriaDTO categoriaDTO)
	{
		return new Categoria(categoriaDTO.getId(),categoriaDTO.getNome());
	}
	
	//atualiza os dados de 2 objetos para salvar
	private void updateData(Categoria newObj, Categoria obj)
	{
		newObj.setNome(obj.getNome());
	}
}

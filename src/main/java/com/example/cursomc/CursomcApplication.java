package com.example.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.domain.Produto;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner
{
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args)
	{
		SpringApplication.run(CursomcApplication.class, args);
	}

	/**chamado toda vez que a aplicação é executada*/
	@Override
	public void run(String... args) throws Exception
	{
		/* cria objetos para salvar no banco*/
	 	Categoria catInformatica = new Categoria(null,"Informática");
		Categoria catEscritorio = new Categoria(null,"Escritório");
		
		Produto prComputador = new Produto(null, "Computador", 2000.00);
		Produto prImpressora = new Produto(null, "Impressora", 2000.00);
		Produto prMouse = new Produto(null, "Mouse", 2000.00);
		
		//obtem a lista . adiciona todos os itens de produto na lista de produtos da categoria
		catInformatica.getProdutos().addAll(Arrays.asList(prComputador,prImpressora, prMouse));
		catEscritorio.getProdutos().add(prImpressora);
		
		//obtem a lista . adiciona todos os itens de categorias na lista de categorias de produtos
		prComputador.getCategorias().add(catInformatica);
		prImpressora.getCategorias().addAll(Arrays.asList(catInformatica,catEscritorio));
		prMouse.getCategorias().add(catInformatica);
		
		//salva todos objetos com os dados no banco
		categoriaRepository.saveAll(Arrays.asList(catInformatica,catEscritorio));
		produtoRepository.saveAll(Arrays.asList(prComputador,prImpressora,prMouse));
	}

}

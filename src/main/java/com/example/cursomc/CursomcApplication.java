package com.example.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.cursomc.domain.Categoria;
import com.example.cursomc.domain.Cidade;
import com.example.cursomc.domain.Cliente;
import com.example.cursomc.domain.Endereco;
import com.example.cursomc.domain.Estado;
import com.example.cursomc.domain.Produto;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.repositories.CidadeRepository;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.repositories.EnderecoRepository;
import com.example.cursomc.repositories.EstadoRepository;
import com.example.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner
{
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
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
	
		Estado esMinasGerais = new Estado(null,"Minas Gerais");
		Estado esSaoPaulo = new Estado(null,"São Paulo");
		
		//instancia e faz a associação muitos para um com o objeto
		Cidade cidUberlandia = new Cidade(null, "Uberlândia", esMinasGerais);
		Cidade cidSaoPaulo = new Cidade(null, "São Paulo", esSaoPaulo);
		Cidade cidCampinas = new Cidade(null, "Campinas", esSaoPaulo);
		
		//instancia e passa a lista de cidades para o estado
		esMinasGerais.getCidades().add(cidUberlandia);
		esSaoPaulo.getCidades().addAll(Arrays.asList(cidSaoPaulo,cidCampinas));
		
		//salva pelo repository
		estadoRepository.saveAll(Arrays.asList(esSaoPaulo,esMinasGerais));
		cidadeRepository.saveAll(Arrays.asList(cidUberlandia,cidSaoPaulo,cidCampinas));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, "");
		
		//salva a lista de telefones
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		//cria os endereços
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, cidUberlandia);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, cidSaoPaulo);

		//salva os enderecos no cliente
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}

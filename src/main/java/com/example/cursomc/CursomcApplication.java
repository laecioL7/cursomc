package com.example.cursomc;

import java.text.SimpleDateFormat;
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
import com.example.cursomc.domain.ItemPedido;
import com.example.cursomc.domain.Pagamento;
import com.example.cursomc.domain.PagamentoComBoleto;
import com.example.cursomc.domain.PagamentoComCartao;
import com.example.cursomc.domain.Pedido;
import com.example.cursomc.domain.Produto;
import com.example.cursomc.domain.enums.EstadoPagamento;
import com.example.cursomc.domain.enums.TipoCliente;
import com.example.cursomc.repositories.CategoriaRepository;
import com.example.cursomc.repositories.CidadeRepository;
import com.example.cursomc.repositories.ClienteRepository;
import com.example.cursomc.repositories.EnderecoRepository;
import com.example.cursomc.repositories.EstadoRepository;
import com.example.cursomc.repositories.ItemPedidoRepository;
import com.example.cursomc.repositories.PagamentoRepository;
import com.example.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
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
	 	Categoria cat1 = new Categoria(null,"Alimentos");
	 	Categoria cat2 = new Categoria(null,"Higienicos");
	 	Categoria cat3 = new Categoria(null,"Perfumaria");
	 	Categoria cat4 = new Categoria(null,"Feirinha");
		Categoria cat5 = new Categoria(null,"Naturais");
		
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
		categoriaRepository.saveAll(Arrays.asList(catInformatica,catEscritorio, cat1,cat2,cat3,cat4,cat5));
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
		
		//pedidos
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 00:00"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 00:00"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		
		//define o pagamento do pedido 1
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		//define o pagamento do pedido 2
		ped2.setPagamento(pagto2);
		
		//associa os pedidos ao cliente
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		//salva primeiro os pedidos
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		
		//salva os pagamentos
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		//items pedido
		ItemPedido ip1 = new ItemPedido(ped1, prComputador, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, prMouse, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, prImpressora, 100.00, 1, 800.00);
		
		//salva os dados de ItemPedido em pedido / referencia inversa
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//salva os dados de ItemPedido em produto / referencia inversa
		prComputador.getItens().addAll(Arrays.asList(ip1));
		prMouse.getItens().addAll(Arrays.asList(ip3));
		prImpressora.getItens().addAll(Arrays.asList(ip2));
		
		//salva no banco
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}

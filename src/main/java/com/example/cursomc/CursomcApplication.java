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
	 	Categoria cat1 = new Categoria(null,"Informática");
	 	Categoria cat2 = new Categoria(null,"Escritório");
	 	Categoria cat3 = new Categoria(null,"Perfumaria");
	 	Categoria cat4 = new Categoria(null,"Feirinha");
		Categoria cat5 = new Categoria(null,"Naturais");
		Categoria cat6 = new Categoria(null,"Massas");
		Categoria cat7 = new Categoria(null,"PetShop");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 2000.00);
		Produto p3 = new Produto(null, "Mouse", 2000.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		//obtem a lista . adiciona todos os itens de produto na lista de produtos da categoria
		cat1.getProdutos().addAll(Arrays.asList(p1,p2, p3));
		cat2.getProdutos().add(p2);
		
		//obtem a lista . adiciona todos os itens de categorias na lista de categorias de produtos
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().add(cat1);
		
		//associa os produtos a categorias
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		//associa as categorias aos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		//salva todos objetos com os dados no banco
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
	
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
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		//salva os dados de ItemPedido em pedido / referencia inversa
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//salva os dados de ItemPedido em produto / referencia inversa
		p1.getItens().addAll(Arrays.asList(ip1));
		p3.getItens().addAll(Arrays.asList(ip3));
		p2.getItens().addAll(Arrays.asList(ip2));
		
		//salva no banco
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}

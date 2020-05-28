package com.cakegourmet.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cakegourmet.model.DetalhesPedido;
import com.cakegourmet.model.Pedido;
import com.cakegourmet.model.Produto;
import com.cakegourmet.repository.CartaoCreditoRepository;
import com.cakegourmet.repository.DetalhesPedidoRepository;
import com.cakegourmet.repository.PedidoRepository;
import com.cakegourmet.repository.ProdutoRepository;

@Controller
@RequestMapping("/cakegourmet")
public class CarrinhoController {
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private PedidoRepository per;
	
	@Autowired
	private DetalhesPedidoRepository dpr;
	
	@Autowired
	private CartaoCreditoRepository ccr;
	
	@RequestMapping(value = "/cardapio", method = RequestMethod.GET)
	public ModelAndView cardapio(){
		
	    ModelAndView mv = new ModelAndView("produto-cliente");
	    
	    Iterable<Produto> produtos = pr.findAll();
	    mv.addObject("produtos", produtos);
	    
	    return mv;
	}
	
	@RequestMapping(value = "/cardapio", method = RequestMethod.POST)
	public String adicionarCarrinho(Produto produto, Pedido pedido, DetalhesPedido detalhesPedido,  HttpServletRequest request){
		
		Principal principal = request.getUserPrincipal();
		String cpf = principal.getName();
		
		Pedido pedidoBusca = new Pedido();
		pedidoBusca = per.findByCpfAndStatus(cpf, "CARRINHO");
		produto = pr.findByIdProduto(produto.getIdProduto());
		
		if(pedidoBusca == null) {
			java.sql.Date dataPedido = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		    pedido.setDataPedido(dataPedido);
		    
		    double value = Double.parseDouble(produto.getPreco().replace(',', '.'));
		    Double valorPedido = value * detalhesPedido.getQtdProduto();
		    pedido.setValorPedido(valorPedido);
		    pedido.setStatus("CARRINHO");
		    
		    pedido.setCpf(cpf);
		    
		    Long idPedido = per.save(pedido).getIdPedido();
		    detalhesPedido.setIdPedido(idPedido);
		    detalhesPedido.setIdProduto(produto.getIdProduto());
		    detalhesPedido.setValorProduto(produto.getPreco());
		    
		    dpr.save(detalhesPedido);
			
			return "redirect:/cakegourmet/carrinho";
		}
		
		
		Iterable<DetalhesPedido> detalhesPedidoBuscaLista = dpr.findAllByIdPedido(pedidoBusca.getIdPedido());
		
		for(DetalhesPedido detalhesPedidoBusca : detalhesPedidoBuscaLista) {
			if(detalhesPedidoBusca.getIdProduto() == produto.getIdProduto()) {				
				
				double value = Double.parseDouble(produto.getPreco().replace(',', '.'));
				Double valorPedido = (value * detalhesPedido.getQtdProduto()) + pedidoBusca.getValorPedido();
				pedidoBusca.setValorPedido(valorPedido);
				
				detalhesPedidoBusca.setQtdProduto(detalhesPedido.getQtdProduto());
				
				per.save(pedidoBusca);
				
			    dpr.save(detalhesPedidoBusca);
			    
			    return "redirect:/cakegourmet/carrinho";
			}
		}
		
		double value = Double.parseDouble(produto.getPreco().replace(',', '.'));
		Double valorPedido = (value * detalhesPedido.getQtdProduto()) + pedidoBusca.getValorPedido();
		pedidoBusca.setValorPedido(valorPedido);
	    
	    per.save(pedidoBusca).getIdPedido();
	    detalhesPedido.setIdPedido(pedidoBusca.getIdPedido());
	    
	    pedidoBusca.setStatus("CARRINHO");
	    
	    detalhesPedido.setIdProduto(produto.getIdProduto());
	    detalhesPedido.setValorProduto(produto.getPreco());
	    
	    per.save(pedidoBusca);
	    dpr.save(detalhesPedido);
	       
	    return "redirect:/cakegourmet/carrinho";
	}

	@RequestMapping(value = "/carrinho", method = RequestMethod.GET)
	public ModelAndView carrinho(HttpServletRequest request){
		
		Principal principal = request.getUserPrincipal();
		String cpf = principal.getName();
		
		ModelAndView mv = new ModelAndView("carrinho");
		Pedido pedido = per.findByCpfAndStatus(cpf, "CARRINHO");
		
		if(pedido == null) {
			mv.addObject("pedidos", pedido); 
			return mv;
		}
		
		DecimalFormat df = new DecimalFormat("#####0.00");
		String valorFormatado = df.format(pedido.getValorPedido());
		Double recuperarValor = new Double(valorFormatado.replace(',', '.'));
		pedido.setValorPedido(recuperarValor);
		
		Iterable<DetalhesPedido> detalhesPedido = dpr.findAllByIdPedido(pedido.getIdPedido());
				
		Iterable<Produto> produto = pr.findAll();
		
		mv.addObject("pedidos", pedido); 
		mv.addObject("detalhesPedidos", detalhesPedido);
		mv.addObject("produtos", produto);
		
		return mv;
	}
	
	@RequestMapping(value = "/carrinho", method = RequestMethod.POST)
	public String finalizarPedido(Pedido pedido, HttpServletRequest request){
		
		Principal principal = request.getUserPrincipal();
		String cpf = principal.getName();
		
		Pedido pedidoBusca = new Pedido();
		pedidoBusca = per.findByCpfAndStatus(cpf, "CARRINHO");
		
		if(pedido.getPagamento() == "Cartao") {
			if(ccr.findByCpf(cpf) == null) {
				return "redirect:/cakegourmet/novocartao";
			}
		}
		
		pedidoBusca.setStatus("PEDIDO");
		pedidoBusca.setPagamento(pedido.getPagamento());
		
		per.save(pedidoBusca);
		
		return "redirect:/cakegourmet/pedido";
	}
	
	@RequestMapping(value="/deletar-produto-carrinho", method=RequestMethod.GET)
	public String deletarProdutoCarrinho(Long idDetalhesPedido) {
		
		DetalhesPedido detalhesPedido = dpr.findByIdPedido(idDetalhesPedido);
		
		Long idPedido = detalhesPedido.getIdPedido();
		
		dpr.delete(detalhesPedido);
		
		Iterable<DetalhesPedido> detalhesPedidoBusca = dpr.findAllByIdPedido(idPedido);
		
		for (DetalhesPedido detalhe : detalhesPedidoBusca) {
			if(detalhe == null) {
				
				return "redirect:/cakegourmet/carrinho";
			}
		}
		
		Pedido pedido = per.findByIdPedido(idPedido);
		per.delete(pedido);
		
		return "redirect:/cakegourmet/produtos";
		
	}
	
	
	@RequestMapping(value="/deletar-produtos-carrinho", method=RequestMethod.GET)
	public String limpar(Long idPedido) {
		
		Iterable<DetalhesPedido> detalhesPedido = dpr.findAllByIdPedido(idPedido);

		for (DetalhesPedido detalhe : detalhesPedido) {
			dpr.delete(detalhe);
		}
		Pedido pedido = per.findByIdPedido(idPedido);
		
		per.delete(pedido);
		
		return "redirect:/cakegourmet/cardapio";
		
	}
	
	
	@RequestMapping(value="/adicionar-produto-carrinho", method=RequestMethod.GET)
	public String adicionarProdutoCarrinho(Long idProduto, HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		String cpf = principal.getName();
		
		int qtdProduto = 1; 
		
		Pedido pedido = new Pedido();
		Pedido pedidoBusca = new Pedido();
		pedidoBusca = per.findByCpfAndStatus(cpf, "CARRINHO");
		
		DetalhesPedido detalhesPedido = new DetalhesPedido();
		
		Produto produto = new Produto();
		produto = pr.findByIdProduto(idProduto);
		
		if(pedidoBusca == null) {
			java.sql.Date dataPedido = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		    pedido.setDataPedido(dataPedido);
		    
		    double value = Double.parseDouble(produto.getPreco().replace(',', '.'));
		    Double valorPedido = value * qtdProduto;
		    pedido.setValorPedido(valorPedido);
		    pedido.setStatus("CARRINHO");
		    
		    pedido.setCpf(cpf);
		    
		    Long idPedido = per.save(pedido).getIdPedido();
		    detalhesPedido.setIdPedido(idPedido);
		    detalhesPedido.setQtdProduto(qtdProduto);
		    detalhesPedido.setIdProduto(produto.getIdProduto());
		    detalhesPedido.setValorProduto(produto.getPreco());
		    
		    dpr.save(detalhesPedido);
			
			return "redirect:/cakegourmet/cardapio";
		}
		
		Iterable<DetalhesPedido> detalhesPedidoBuscaLista = dpr.findAllByIdPedido(pedidoBusca.getIdPedido());
		
		for(DetalhesPedido detalhesPedidoBusca : detalhesPedidoBuscaLista) {
			if(detalhesPedidoBusca.getIdProduto() == produto.getIdProduto()) {				
				
				
				double value = Double.parseDouble(produto.getPreco().replace(',', '.'));
				Double valorPedido = (value * 1) + pedidoBusca.getValorPedido();
				pedidoBusca.setValorPedido(valorPedido);
				
				detalhesPedidoBusca.setQtdProduto(detalhesPedidoBusca.getQtdProduto() + qtdProduto);
				
				per.save(pedidoBusca);
				
			    dpr.save(detalhesPedidoBusca);
			    
			    return "redirect:/cakegourmet/cardapio";
			}
		}
		
		double value = Double.parseDouble(produto.getPreco().replace(',', '.'));
		Double valorPedido = (value * 1) + pedidoBusca.getValorPedido();
		pedidoBusca.setValorPedido(valorPedido);
	    
	    per.save(pedidoBusca).getIdPedido();
	    detalhesPedido.setIdPedido(pedidoBusca.getIdPedido());
	    
	    detalhesPedido.setIdPedido(pedidoBusca.getIdPedido());
	    detalhesPedido.setValorProduto(produto.getPreco());
	    detalhesPedido.setQtdProduto(qtdProduto);
	    detalhesPedido.setIdProduto(idProduto);
	    
	    dpr.save(detalhesPedido);
		
		return "redirect:/cakegourmet/cardapio";
	}
	
	@RequestMapping(value = "/pedido", method = RequestMethod.GET)
	public ModelAndView finalizarPedido(HttpServletRequest request){
		
		Principal principal = request.getUserPrincipal();
		String cpf = principal.getName();
		
		ModelAndView mv = new ModelAndView("pedido");
		Pedido pedido = per.findByCpfAndStatus(cpf, "PEDIDO");
		
		if(pedido == null) {
			mv.addObject("pedidos", pedido); 
			return mv;
		}
		
		DecimalFormat df = new DecimalFormat("#####0.00");
		String valorFormatado = df.format(pedido.getValorPedido());
		Double recuperarValor = new Double(valorFormatado.replace(',', '.'));
		pedido.setValorPedido(recuperarValor);
		
		Iterable<DetalhesPedido> detalhesPedido = dpr.findAllByIdPedido(pedido.getIdPedido());
				
		Iterable<Produto> produto = pr.findAll();
		
		mv.addObject("pedidos", pedido); 
		mv.addObject("detalhesPedidos", detalhesPedido);
		mv.addObject("produtos", produto);
		
		return mv;
	}
}

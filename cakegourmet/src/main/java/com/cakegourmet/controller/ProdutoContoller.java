package com.cakegourmet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cakegourmet.model.Produto;
import com.cakegourmet.repository.ProdutoRepository;

@Controller
@RequestMapping("/cakegourmet")
public class ProdutoContoller {
	
	@Autowired
	private ProdutoRepository pr;
	
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ModelAndView listaProdutos(){
		
	    ModelAndView mv = new ModelAndView("produto");
	    
	    Iterable<Produto> produtos = pr.findAll();
	    mv.addObject("produtos", produtos);
	    
	    return mv;
	}
	
	@RequestMapping(value = "/editar-produto", method = RequestMethod.POST)
	public String formshowClient(Produto produto) {
				
		pr.save(produto);
		
	    return "redirect:/cakegourmet/produtos";
	}
	
	@RequestMapping(value = "/editar-produto", method = RequestMethod.GET)
	public ModelAndView showProduct(Long id) {		
		
		ModelAndView modelAndView = new ModelAndView("form-produto");
		Produto produto = new Produto();
		produto = pr.findByIdProduto(id);
		modelAndView.addObject("produto", produto);
		
	    return modelAndView;
	}
	
	@RequestMapping(value = "/novo-produto", method = RequestMethod.POST)
	public String newProduct(Produto produto) {
		
		pr.save(produto);
		
	    return "redirect:/cakegourmet/novo-produto";
	}
	
	
	@RequestMapping(value="/novo-produto", method=RequestMethod.GET)
	public ModelAndView formNewProduct() {
		
		ModelAndView modelAndView = new ModelAndView("form-produto");
		
		Produto produto = new Produto();
		modelAndView.addObject("produto", produto);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/deletar-produto", method=RequestMethod.GET)
	public String deletarProduto(Long id) {
		
		Produto produto = pr.findByIdProduto(id);
		
		pr.delete(produto);
		
		return "redirect:/cakegourmet/produtos";
		
	}
}

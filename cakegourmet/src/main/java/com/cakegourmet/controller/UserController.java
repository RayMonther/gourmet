package com.cakegourmet.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cakegourmet.model.CartaoCredito;
import com.cakegourmet.model.Cliente;
import com.cakegourmet.model.Role;
import com.cakegourmet.model.Usuario;
import com.cakegourmet.repository.CartaoCreditoRepository;
import com.cakegourmet.repository.ClienteRepository;
import com.cakegourmet.repository.UsuarioRepository;
import com.cakegourmet.security.SecurePassword;

@Controller
@RequestMapping("/cakegourmet")
public class UserController {
	
	@Autowired
	private ClienteRepository cr;
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private CartaoCreditoRepository ccr;
	
	@RequestMapping(value="novocliente", method=RequestMethod.GET)
	public ModelAndView form() {
		
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		Cliente cliente = new Cliente();
	    modelAndView.addObject("cliente", cliente);
	    
	    return modelAndView;
	}
	
	@RequestMapping(value="/novocliente", method=RequestMethod.POST)
	public String form(Cliente cliente) {
		
		String pass = cliente.getPassword();
		
		java.sql.Date dataCriacao = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		cliente.setDataCriacao(dataCriacao);
		
		String cpf = cliente.getCpf(); 
		String x = cpf.replaceAll(Pattern.quote("-"), "");
		cpf = x.replaceAll(Pattern.quote("."), "");
		System.out.println(cpf);
		
		cliente.setCpf(cpf);
		cliente.setPassword(new BCryptPasswordEncoder().encode(pass));
		
		Role role = new Role();
		role.setNomeRole("ROLE_ADMIN");
		List<Role> roles = new ArrayList<Role>();
		roles.add(0,role);
		
		Usuario user = new Usuario();
		user.setLogin(cpf);
		user.setSenha(new BCryptPasswordEncoder().encode(pass));
		user.setRoles(roles);
		
		cr.save(cliente);
		ur.save(user);
		
		return "redirect:/cakegourmet/home";
	}
	
	@RequestMapping("/clientes")
	public ModelAndView showClients() {
		ModelAndView mv = new ModelAndView("clientes");
		Iterable<Cliente> clientes = cr.findAll();
		mv.addObject("clientes", clientes);
		
		return mv;
	}
	
	@RequestMapping(value = "/edit-cliente", method = RequestMethod.GET)
	public ModelAndView editClient(String cpf, HttpServletRequest request) {
		
		if(cpf == null) {
			Principal principal = request.getUserPrincipal();
			cpf = principal.getName();
		}
		
		Cliente cliente = new Cliente();
		cliente = cr.findByCpf(cpf);
		
		ModelAndView modelAndView = new ModelAndView("form-cliente");
		
		modelAndView.addObject("cliente", cliente);
		
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-cliente", method = RequestMethod.POST)
	public String editClientSave(Cliente cliente) {
		
		String cpf = cliente.getCpf(); 
		String x = cpf.replaceAll(Pattern.quote("-"), "");
		cpf = x.replaceAll(Pattern.quote("."), "");
		System.out.println(cpf);
		
		cliente.setCpf(cpf);
		
		String pass = cliente.getPassword();
		cliente.setPassword(new BCryptPasswordEncoder().encode(pass));
		
		Usuario user = new Usuario();
		user = ur.findByLogin(cliente.getCpf());
		user.setSenha(new BCryptPasswordEncoder().encode(pass));
		
		cr.save(cliente);
		ur.save(user);
		
	    return "redirect:/cakegourmet/clientes";
	}
	
	@RequestMapping(value="novocartao", method=RequestMethod.GET)
	public static String formCartao() {	    
	    return "form-cartao";
	}
	
	@RequestMapping(value="novocartao", method=RequestMethod.POST)
	public String formCartao(CartaoCredito cartao, HttpServletRequest request) {
		
		Principal principal = request.getUserPrincipal();
		String cpf = principal.getName();
		
		String num = cartao.getNumero();
		String numero = SecurePassword.getSHA512(num);
		cartao.setNumero(numero);
		cartao.setCpf(cpf);
		ccr.save(cartao);
		
	    return "redirect:/cakegourmet/pedido";
	}
	
}

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

import com.cakegourmet.model.Funcionario;
import com.cakegourmet.model.Role;
import com.cakegourmet.model.Usuario;
import com.cakegourmet.repository.FuncionarioRepository;
import com.cakegourmet.repository.UsuarioRepository;

@Controller
@RequestMapping("/cakegourmet")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository fr;
	
	@Autowired
	private UsuarioRepository ur;
	
	@RequestMapping(value="novofuncionario", method=RequestMethod.GET)
	public ModelAndView formFuncionario() {
		
		ModelAndView modelAndView = new ModelAndView("form-funcionario");
		Funcionario funcionario = new Funcionario();
	    modelAndView.addObject("funcionario", funcionario);
	    
	    return modelAndView;
	}
	
	@RequestMapping(value="/novofuncionario", method=RequestMethod.POST)
	public String formFuncionario(Funcionario funcionario) {
		
		String pass = funcionario.getPassword();
		
		java.sql.Date dataCriacao = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
		funcionario.setDataCriacao(dataCriacao);
		
		String cpf = funcionario.getCpf(); 
		String x = cpf.replaceAll(Pattern.quote("-"), "");
		cpf = x.replaceAll(Pattern.quote("."), "");
		System.out.println(cpf);
		
		funcionario.setCpf(cpf);
		funcionario.setPassword(new BCryptPasswordEncoder().encode(pass));
		
		Role role = new Role();
		role.setNomeRole("ROLE_ADMIN");
		List<Role> roles = new ArrayList<Role>();
		roles.add(0,role);
		
		Usuario user = new Usuario();
		user.setLogin(cpf);
		user.setSenha(new BCryptPasswordEncoder().encode(pass));
		user.setRoles(roles);
		
		fr.save(funcionario);
		//ur.save(user);
		
		return "redirect:/cakegourmet/novofuncionario";
	}
	
	@RequestMapping("/funcionarios")
	public ModelAndView showFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionarios");
		Iterable<Funcionario> funcionarios = fr.findAll();
		mv.addObject("funcionarios", funcionarios);
		
		return mv;
	}
	
	@RequestMapping(value = "/edit-funcionarios", method = RequestMethod.GET)
	public ModelAndView editarFuncionarios(String cpf, HttpServletRequest request) {
		
		if(cpf == null) {
			Principal principal = request.getUserPrincipal();
			cpf = principal.getName();
		}
		
		Funcionario funcionario = new Funcionario();
		funcionario = fr.findByCpf(cpf);
		
		ModelAndView modelAndView = new ModelAndView("form-funcionario");
		
		modelAndView.addObject("funcionario", funcionario);
		
	    return modelAndView;
	}
	
	@RequestMapping(value = "/edit-funcionarios", method = RequestMethod.POST)
	public String editarFuncionarios(Funcionario funcionario) {
		
		String pass = funcionario.getPassword();
		funcionario.setPassword(new BCryptPasswordEncoder().encode(pass));
		
		Usuario user = new Usuario();
		user = ur.findByLogin(funcionario.getCpf());
		user.setSenha(new BCryptPasswordEncoder().encode(pass));
		
		fr.save(funcionario);
		ur.save(user);
		
	    return "redirect:/cakegourmet/funcionarios";
	}
}

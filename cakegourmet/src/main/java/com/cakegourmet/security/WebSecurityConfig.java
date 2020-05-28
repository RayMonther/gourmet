package com.cakegourmet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/cakegourmet/home").permitAll()
		.antMatchers(HttpMethod.GET, "/cakegourmet/cardapio").permitAll()
		.antMatchers(HttpMethod.POST, "/cakegourmet/cardapio").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/novofuncionario").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/novofuncionario").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/funcionarios").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/edit-funcionarios").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/edit-funcionarios").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/pedido").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/pedido").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/produtos").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/editar-produto").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/editar-produto").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/novo-produto").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/novo-produto").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/novocliente").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/novocliente").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/clientes").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/edit-cliente").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/edit-cliente").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/novocartao").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/novocartao").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/carrinho").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cakegourmet/carrinho").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/deletar-produto").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/deletar-produtos-carrinho").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/deletar-produto-carrinho").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cakegourmet/adicionar-produtos-carrinho").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().defaultSuccessUrl("/cakegourmet/cardapio",true).permitAll()
		.failureUrl("/cakegourmet/home?error=true")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
       
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/cakegourmet/css/**", "/cakegourmet/js/**", "/cakegourmet/img/**");
	}
}

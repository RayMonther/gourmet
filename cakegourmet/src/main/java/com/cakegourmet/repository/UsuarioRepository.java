package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.Usuario;

public interface UsuarioRepository  extends CrudRepository<Usuario, String>{
	
	Usuario findByLogin(String login);
}

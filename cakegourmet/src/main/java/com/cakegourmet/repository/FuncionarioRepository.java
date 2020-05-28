package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String>{
	
	Funcionario findByCpf(String cpf);
}

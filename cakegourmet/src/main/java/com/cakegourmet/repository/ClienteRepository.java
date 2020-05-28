package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, String>{
	
	Cliente findByCpf(String cpf);
	
}

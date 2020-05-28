package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.CartaoCredito;

public interface CartaoCreditoRepository extends CrudRepository<CartaoCredito, String>{
	CartaoCredito findByCpf(String cpf);
}

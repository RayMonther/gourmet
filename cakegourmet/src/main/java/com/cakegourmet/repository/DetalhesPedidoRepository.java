package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.DetalhesPedido;

public interface DetalhesPedidoRepository extends CrudRepository<DetalhesPedido, String>{
	
	Iterable<DetalhesPedido> findAllByIdPedido(Long idPedido);
	DetalhesPedido findByIdPedido(Long idPedido);
}

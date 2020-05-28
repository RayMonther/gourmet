package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, String>{
	
	Pedido findByIdPedido(Long idPedido);
	Pedido findByStatus(String status);
	Pedido findByCpfAndStatus(String cpf,String status);
	void deleteByIdPedido(Long idPedido);
}

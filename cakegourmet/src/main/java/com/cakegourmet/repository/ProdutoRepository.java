package com.cakegourmet.repository;

import org.springframework.data.repository.CrudRepository;

import com.cakegourmet.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, String>{
	Produto findByIdProduto(Long idProduto);
}

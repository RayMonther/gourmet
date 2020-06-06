package com.cakegourmet.service.impl;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cakegourmet.model.Produto;
import com.cakegourmet.repository.ProdutoRepository;
import com.cakegourmet.service.IReportService;

@Service
@Transactional
public class ReportServiceImpl implements IReportService{
	
	@Autowired
	private ProdutoRepository pr;
	
	public StringBuffer generateReport() throws NoSuchAlgorithmException, ParseException {
		
		Iterable<Produto> list = pr.findAll();
		
		StringBuffer csvRow = new StringBuffer("ID;PRODUTO;CATEGORIA;VALOR;PESO;SABOR;DESCRICAO;ESTOQUE\n");
		for(Produto produto : list) {
			csvRow.append(produto.getIdProduto());
			csvRow.append(";");
			
			csvRow.append(produto.getNome());
			csvRow.append(";");
			
			csvRow.append(produto.getCategoria());
			csvRow.append(";");
			
			csvRow.append(produto.getPreco());
			csvRow.append(";");
			
			csvRow.append(produto.getPeso());
			csvRow.append(";");
			
			csvRow.append(produto.getSabor());
			csvRow.append(";");
			
			csvRow.append(produto.getDescricao());
			csvRow.append(";");
			
			csvRow.append(produto.getEstoque());
			csvRow.append("\n");
		}
		return csvRow;
	}
	
}

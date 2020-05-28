package com.cakegourmet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DetalhesPedido")
public class DetalhesPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DET_SEQ")
    @SequenceGenerator(sequenceName = "detalhes_pedido_seq", initialValue = 1, allocationSize = 1, name = "DET_SEQ")
	private Long idDetalhesPedido;
	
	private Long idPedido;
	private Long idProduto;
	private String valorProduto;
	private int qtdProduto;
	
	public Long getIdDetalhesPedido() {
		return idDetalhesPedido;
	}
	public void setIdDetalhesPedido(Long idDetalhesPedido) {
		this.idDetalhesPedido = idDetalhesPedido;
	}
	public Long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	public String getValorProduto() {
		return valorProduto;
	}
	public void setValorProduto(String valorProduto) {
		this.valorProduto = valorProduto;
	}
	public int getQtdProduto() {
		return qtdProduto;
	}
	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}
}

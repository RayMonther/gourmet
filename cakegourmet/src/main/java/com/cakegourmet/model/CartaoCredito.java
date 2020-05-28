package com.cakegourmet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Cartao")
public class CartaoCredito {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ")
    @SequenceGenerator(sequenceName = "cartao_seq", initialValue = 1, allocationSize = 1, name = "CARD_SEQ")
	private Long idCartao;
    private String cpf;
    
    private String nome;
    
    @Column(unique=true)
    private String numero;
    private String data;
    private String cvv;
	private String bandeira;
	
	public Long getIdCartao() {
		return idCartao;
	}
	public void setIdCartao(Long idCartao) {
		this.idCartao = idCartao;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String getBandeira() {
		return bandeira;
	}
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
}

package com.gourmet.models;

public class Product {
	
	    private Long id;
	    private String name;
	    private String taste;
	    private Double weight;
	    private Integer stock;

	    public Product() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getTaste() {
	        return taste;
	    }

	    public void setTaste(String taste) {
	        this.taste = taste;
	    }

	    public Double getWeight() {
	        return weight;
	    }

	    public void setWeight(Double weight) {
	        this.weight = weight;
	    }

	    public Integer getStock() {
	        return stock;
	    }

	    public void setStock(Integer stock) {
	        this.stock = stock;
	    }
}

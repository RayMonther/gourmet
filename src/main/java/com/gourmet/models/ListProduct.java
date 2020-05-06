package com.gourmet.models;

import java.util.ArrayList;
import java.util.List;

public class ListProduct {
	private List<Product> productList;
	
	public ListProduct() {
		productList = new ArrayList<>();
    }
	
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}

package com.gourmet.models;

import java.util.ArrayList;
import java.util.List;

public class ListClient {
	
	private List<Client> clientList;
	
	public ListClient() {
		clientList = new ArrayList<>();
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}
	
}

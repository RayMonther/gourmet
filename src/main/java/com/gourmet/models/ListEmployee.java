package com.gourmet.models;

import java.util.ArrayList;
import java.util.List;

public class ListEmployee {
	
private List<Employee> listEmployee;
	
	public ListEmployee() {
		listEmployee = new ArrayList<>();
	}

	public List<Employee> getListEmployee() {
		return listEmployee;
	}

	public void setListEmployee(List<Employee> listEmployee) {
		this.listEmployee = listEmployee;
	}
}

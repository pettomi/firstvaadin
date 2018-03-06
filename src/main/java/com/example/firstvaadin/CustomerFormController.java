package com.example.firstvaadin;

import java.lang.reflect.Constructor;
import java.sql.Savepoint;

public class CustomerFormController extends CustomerForm{

	private CustomerService service= new CustomerService().getInstance();
	private Customer customer ;
	private MyUI myUI;
	
	public CustomerFormController(MyUI myUI) {
		// TODO Auto-generated constructor stub
		this.myUI = myUI;
		status.addItems(CustomerStatus.values());
		
	}
	
	
	
}

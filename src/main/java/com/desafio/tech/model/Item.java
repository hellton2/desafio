package com.desafio.tech.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
	
	private int id;
	private int quantity;
	private double doublePrice;
	private Vendedor vendedor;

}

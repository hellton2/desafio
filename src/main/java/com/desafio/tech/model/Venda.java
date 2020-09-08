package com.desafio.tech.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda {
	
	private String saleId;
	private List<Item> itens;
	
	
	
	

}

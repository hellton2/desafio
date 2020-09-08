package com.desafio.tech.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Vendedor {
	
	private String cpf;
	private String name;
	private double salary;
	

//
//
//	public static class VendedorBuilder 
//    {
//		private int id;
//		private String cpf;
//		private String name;
//		private double salary;
//		
//        public VendedorBuilder(int id, String cpf, String name, double salary) {
//			super();
//			this.id = id;
//			this.cpf = cpf;
//			this.name = name;
//			this.salary = salary;
//		}
//        
//		public VendedorBuilder id(int id) {
//            this.id = id;
//            return this;
//        }
//		
//        public VendedorBuilder cpf(String cpf) {
//            this.cpf = cpf;
//            return this;
//        }
//        
//        public VendedorBuilder name(String name) {
//            this.name = name;
//            return this;
//        }
//        
//        public VendedorBuilder salary(double salary) {
//            this.salary = salary;
//            return this;
//        }
//        
//        public Vendedor build() {
//            Vendedor user =  new Vendedor(this);
//            return user;
//        }
//    }

}

package com.desafio.tech;

import java.util.regex.Pattern;

public class Constants {
	
	public final static String IN = System.getProperty("user.home") + "\\data\\in\\";
	public static final String OUT = System.getProperty("user.home") + "\\data\\out\\";
	public static final String PATTERN_SEQUENCE_ITENS = "(?<=\\[).+?(?=\\])";
	public static final String PATTERN_SEQUENCE = "(^001)|(^002)|(^003)";

	public static final Pattern PATTENRN_OBJECT_TYPE = Pattern.compile(PATTERN_SEQUENCE);//. represents single character  
	public static final Pattern PATTENRN_OBJECT_ITENS = Pattern.compile(PATTERN_SEQUENCE_ITENS);//. represents single character  



}

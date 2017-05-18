package com.netfinworks.optimus.test;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {
		
		BigDecimal value1 = new BigDecimal("1");
		BigDecimal value2 = new BigDecimal("2");
		System.out.println(value1.compareTo(value2));
		System.out.println(value2.compareTo(value1));
		System.out.println(value1.compareTo(new BigDecimal("1")));
	}

}


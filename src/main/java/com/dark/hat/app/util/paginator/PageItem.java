package com.dark.hat.app.util.paginator;

import lombok.Getter;

@Getter
public class PageItem {
	private int numero;
	private boolean actual;
	
	public PageItem(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}
	
	
}

package org.springevent.test.controller.protectedTest;

import org.springevent.test.controller.protectedTest.fath.Basebook;

public class Book extends Basebook{

	public String getFathName(){
		System.out.println(this.name);
		try {
			this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.getName();
	}
}

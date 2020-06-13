package org.springevent.test.controller.cloneTest;

public class Son {

	private String sonName;
	private int age;

	public Son(String sonName, int age) {
		super();
		this.sonName = sonName;
		this.age = age;
	}
	
	
	protected void setSonName(String sonName) {
		this.sonName = sonName;
	}



	@Override
	public String toString() {
		return "Son [sonName=" + sonName + ", age=" + age + "]";
	}
	
	/*public Son myclone(){
		Son son = null;
		try {
			son = (Son) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return son;
	}*/
	
}

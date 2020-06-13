package org.springevent.test.controller.voidTest;

public class VoidTest {

	
	public static void main(String[] args) {
		Student student = new VoidTest().new Student("tian");
		new VoidTest().changeName(student);
		System.out.println(student.getName());
		
	}
	
	public void changeName(Student s){
		s = new VoidTest().new Student("ming");
	}
	
	
	public class Student {
		private String name;
		public Student(String setname){
			name=setname;
		}
		public String getName(){
			return name;
		}
		public void setName(String setname){
			name=setname;
		}
	}
}

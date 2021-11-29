package in.co.test;

public class Person {
	private String name;
	private int id;
	public Person(){}
	public Person(String name){
		this.name=name;
	}
	public Person(String name,int id){
		this.name=name;
		this.id=id;
	}
	public String toString (){
		return this.name+ this.id;
				
	}
}

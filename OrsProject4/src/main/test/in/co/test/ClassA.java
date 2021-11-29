package in.co.test;

public class ClassA extends ClassB{
public static void main(String[]args){
    ClassB b=new ClassB();
    b.testB();
	ClassA a=new ClassA();
	a.testB();
}
public void testB(){
	System.out.println("Test-A");
}
}

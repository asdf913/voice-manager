import java.io.*;
class myClass {
	String Howdy = "Hello There";
	static MyOtherClass otherClass;
	myClass(){
		otherClass= new MyOtherClass();
	}
	public static void main(String[] args) {
		System.out.println(otherClass.Goodbye);
	}
}
class MyOtherClass{
	static public String Goodbye ="So Long!";
	MyOtherClass(){}
}

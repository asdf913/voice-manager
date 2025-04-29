package com.sample;

public class MyClass implements Cloneable{
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public String getInfo() {
		return "MyClass";
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		//MyClass myClass= (MyClass) Class.forName("com.sample.MyClass").newInstance();
//		MyClass myClass= MyClass.clone();
//		Class class=Class.forName("com.sample.MyClass");
//		MyClass myClass =  new MyClass();
		MyClass myClass = (MyClass)MyClass.class.getClassLoader().loadClass("com.sample.MyClass").newInstance();
		System.out.println(myClass.getInfo());
	}
}

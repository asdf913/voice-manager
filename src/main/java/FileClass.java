import java.io.*;
import java.nio.file.Path;
public class FileClass {
	FileClass(){System.out.println("File");}
	public static void main(String[] args) throws Exception {
		IkmTest FileClass = new IkmTest("XYZ");
		FileOutputStream file = new FileOutputStream("Data.txt");
		ObjectOutputStream output = new ObjectOutputStream(file);
		output.writeObject(FileClass);
		output.close();
		FileInputStream fis = new FileInputStream("Data.txt");
		ObjectInputStream is = new ObjectInputStream(fis);
		IkmTest c2 = (IkmTest) is.readObject();
		System.out.println(c2.getName());
		is.close();
		
	}
}
class IkmTest extends FileClass implements Serializable{
	private String name;
	IkmTest(String name){
		this.name=name;
		System.out.println("Test");
	}
	public String getName() {return name;}
}
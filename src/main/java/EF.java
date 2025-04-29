
public class EF {

	@FunctionalInterface
	interface MyString{
		String ChangeString(String input);
	}
	
	public static void main(String[] args) {
//		MyString doLower = (Value)->Value.toLowerCase();
//		MyString doLower = new MyString() {
//			 String ChangeString(String input) {
//				return input.toLowerCase();
//			}
//		};
//		MyString doLower = new MyString() {
//		public String ChangeString(String input) {
//			return input.toLowerCase();
//		}
//		MyString doLower = (Value)->return Value.toLowerCase();
//	};
	
	}
	class Encapsulated{
		String outher = "Goodbye!";
		MyString doLower = new MyString() {
			String Inner= "Hello";
			public String ChangeString(String input) {
				// TODO Auto-generated method stub
				Inner= Encapsulated.this.outher+input;
				return Inner.toLowerCase();
			}
		};
	}
	
}

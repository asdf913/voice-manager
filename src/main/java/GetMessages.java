
public class GetMessages {
static String message;
public static void main(String[] args) {
	try {
		String message = getMessage("B");
		System.out.println(message);
		if(message.length()<3) {
			throw new Exception();
		}
	}catch(Exception e) {
		message+="F";
		System.out.println(message);
	}finally {
		System.out.println(message);
	}
}
public static String getMessage(String message) {
	String data = message.toCharArray()[0]==66?"A":"D";
	try {
		if(data=="A") {
			throw new Exception();
		}else {
			message=data;
		}
	}catch(Exception e) {
		System.out.println(e);
		message+="C";
	}finally {
		message+="E";
	}
	return message;
	}
}
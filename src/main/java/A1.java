
public class A1 {

	public static class Shape{}
		public static class Quadrilateral extends Shape{}
		public static class Triangle extends Shape{}
		public static void main(String[] args) {
			Shape shape = new Quadrilateral();
			Quadrilateral quadrilateral = new Quadrilateral();
			Shape tri=(Triangle)shape;
//			shape=quadrilateral;  
//			Shape tri = quadrilateral;
//			Triangle tri= (Triangle) quadrilateral;
		}
}

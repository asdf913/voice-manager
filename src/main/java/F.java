import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

public class F {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));
		for(int i=1;i<=3;i++) {
			list.add(i);
			Spliterator<Integer> split = list.spliterator();
			split.forEachRemaining(z->System.out.print(z+" "));
			System.out.println();
		}
	}

}

package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamExample3 {
	static int sum = 0, cnt = 0;
public static void main(String[] args) {
//	int sum = 0, cnt = 0;
	List<Student> list = Arrays.asList(new Student("최재영", 90), new Student("민해주", 88), new Student("김상민", 83), new Student("최형준", 86));
	Stream<Student> students = list.stream();
	students.forEach( t -> {
			sum += t.getScore();
			cnt++;
	});
	System.out.println("총점: " + sum + ", 평균: " + (sum/(double)cnt));
}
}

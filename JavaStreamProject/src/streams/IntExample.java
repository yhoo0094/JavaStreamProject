package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntExample {
	static int sumsum = 0;
	public static void main(String[] args) {
		//1~10까지 정수형 배열.
		//배열로부터 스트림을 생성
		//filter를 통해서 짝수값 가져오기
		//최종집계처리는 sum()을 가져오는 코드 구현
		
		int[] intAry = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		IntStream iStream = Arrays.stream(intAry);
		long sum = iStream.filter(t-> t%2 == 0).sum();
		System.out.println(sum);
		
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Stream<Integer> streams = list.stream();
		streams.filter(t-> t%2 == 0)
			   .forEach(t -> sumsum = sumsum + t);
		System.out.println(sumsum);
	}
}

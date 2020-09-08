package streams;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class FromArrayExample {
	public static void main(String[] args) {
		int[] intAry = {3,6,4,9,7};
		IntStream iStream = Arrays.stream(intAry);
		
		iStream.filter(value -> value > 5)
		.forEach(value -> System.out.print(value + " "));
		System.out.println();
		
//		long cnt = iStream.count(); //->가져올 반복자가 없어서 오류난다!
		
		
		//반복자 다시 넣어주고 실행하면 오류 안 뜬다!
		iStream = Arrays.stream(intAry);
		long cnt = iStream.count();
		iStream = Arrays.stream(intAry);
		long sum = iStream.sum();
		System.out.println("갯수는: " + cnt + " 합계는 : " + sum);
		
		//배열의 일부만 가져오기
		iStream = Arrays.stream(intAry, 0, 4);
		cnt = iStream.peek(t -> System.out.print(t + " ")).count();
		System.out.println();
		System.out.println("배열 0에서 4사이에는 " + cnt + "개가 있다!");
		
//		iStream.filter(new IntPredicate() {
//			@Override
//			public boolean test(int value) {
//				return value > 5;
//			}
//		}).forEach(new IntConsumer() {
//			@Override
//			public void accept(int value) {
//				System.out.println(value);
//			}
//		});
	}
}

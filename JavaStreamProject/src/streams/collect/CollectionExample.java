package streams.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import streams.intermediate.Student;

public class CollectionExample {
	public static void main(String[] args) {
		System.out.println("-----------SET-------------");
		
		Set<String> setStr = new HashSet<String>();
		setStr.add(new String("최재영"));
		setStr.add(new String("민해주"));
		setStr.add(new String("김상민"));
		
//		for(String s:setStr) {
//			System.out.println(setStr);
//		}
		
		Iterator<String> iter = setStr.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		
		System.out.println("-----------LIST-------------");
		
		List<Student> students = new ArrayList<Student>();
		students.add(new Student("허성준", 80));
		students.add(new Student("최형준", 90));
		
		for(Student s : students) {
			System.out.println(s);
		}
		
		List<Student> studentw = new ArrayList<Student>();
		students.add(new Student("김다희", 80));
		students.add(new Student("김도은", 90));
		
		List<Student> studentr = new ArrayList<Student>();
		students.add(new Student("허성준", 80));
		students.add(new Student("김다희", 80));
		
		System.out.println("-----------MAP-------------");
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("동광희", 25);
		map.put("김시무", 26);
		
		Map<String, List<Student>> stMapa = new HashMap<String, List<Student>>();
		stMapa.put("남자", students);
		stMapa.put("여자", studentw);
		
		
		
		Set<String> key = map.keySet();
		Iterator<String> itr = key.iterator();
		while (itr.hasNext()) {
			String K = itr.next(); // key값을 가져오기
			System.out.print(K+ ": ");
			System.out.println(map.get(K));
		}
		
		System.out.println("-----------MAP2-------------");
		
		Map<String, List<Student>> stMap = new HashMap<String, List<Student>>();
		stMap.put("남자", students);
		stMap.put("여자", studentw);
		stMap.put("재평가", studentr);
		Set<String> setK = stMap.keySet();
		Iterator<String> sit = setK.iterator();
		
//		while(sit.hasNext()) {
//			String keys = sit.next();
//			for (Student st : stMap.get(keys)) {
//				System.out.println(" " + st);
//			}
//			System.out.println(keys);
//		}
		
		for(String s : setK) {
			List<Student> valueList = stMap.get(s);
			System.out.println(s);
			for (Student st : valueList) {
				System.out.println(st);
			}
		}
		
//		for(String s : setK) {
//			List<Student> valueList = stMap.get(s);
//			System.out.println(s);
//			for (Student st : valueList) {
//				System.out.println(st);
//			}
//		}
		
	}
}

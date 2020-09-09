package streams.intermediate;

public class Student implements Comparable<Student> {
	String name;
	int score;
	public Student(String name, int score) {
		this.name = name;
		this.score = score;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", score=" + score + "]";
	}
	
	//둘다 비교하기 기본
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + score;
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Student other = (Student) obj;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (score != other.score)
//			return false;
//		return true;
//	}
	
	//둘다 비교하기
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.score;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Student) {
			Student that = (Student) obj;
			return this.name == that.name && this.score == that.score;	
		} else {
			return false;
		}
	}
	
//	//이름으로 구분
//	@Override
//	public int hashCode() {
//		return this.name.hashCode();
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if(obj instanceof Student) {
//			Student that = (Student) obj;
//			return this.name == that.name;	
//		} else {
//			return false;
//		}
//	}
	
	
	
	//스코어로 구분
//	@Override
//	public int hashCode() {
//		return this.score;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if(obj instanceof Student) {
//			Student that = (Student) obj;
//			return this.score == that.score;	
//		} else {
//			return false;
//		}
//	}
	
	@Override
	public int compareTo(Student that) {
		return this.score - that.score; //음수값: 오름차순, 양수값: 내림차순
	}
	
}

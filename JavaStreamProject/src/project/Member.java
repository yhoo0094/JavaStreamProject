package project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {
	private SimpleStringProperty name;
	private SimpleIntegerProperty password;
	
	public Member(String name, int password) {
		this.name = new SimpleStringProperty(name);
		this.password = new SimpleIntegerProperty(password);
	}
	
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	
	public int getPassword() {
		return this.password.get();
	}
	public void setPassword(int password) {
		this.password.set(password);
	}
	
}

package project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
	private SimpleStringProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty condition;
	private SimpleIntegerProperty price;
	private SimpleIntegerProperty pid;
	
	public Item(String name, String condition, int price) {
		this.name = new SimpleStringProperty(name);
		this.condition = new SimpleStringProperty(condition);
		this.price = new SimpleIntegerProperty(price);
	}
	
	public Item(String id, String name, String condition, int price, int pid) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.condition = new SimpleStringProperty(condition);
		this.price = new SimpleIntegerProperty(price);
		this.pid = new SimpleIntegerProperty(pid);
	}
	
	public String getId() {
		return this.id.get();
	}
	public void setId(String id) {
		this.id.set(id);
	}
	
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getCondition() {
		return this.condition.get();
	}
	public void setCondition(String condition) {
		this.condition.set(condition);
	}
	
	public int getPrice() {
		return this.price.get();
	}
	public void setPrice(int price) {
		this.price.set(price);
	}
	
	public int getPid() {
		return this.pid.get();
	}
	public void setPid(int pid) {
		this.pid.set(pid);
	}
}

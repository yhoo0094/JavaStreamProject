package project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Massage {
	private SimpleStringProperty fromId;
	private SimpleStringProperty toId;
	private SimpleStringProperty title;
	private SimpleStringProperty massage;
	private SimpleIntegerProperty mid;
	
	public Massage(String fromId, String toId, String title, String massage) {
		this.fromId = new SimpleStringProperty(fromId);
		this.toId = new SimpleStringProperty(toId);
		this.title = new SimpleStringProperty(title);
		this.massage = new SimpleStringProperty(massage);
	}
	
	public Massage(String fromId, String toId, String title, String massage, Integer mid) {
		this.fromId = new SimpleStringProperty(fromId);
		this.toId = new SimpleStringProperty(toId);
		this.title = new SimpleStringProperty(title);
		this.massage = new SimpleStringProperty(massage);
		this.mid = new SimpleIntegerProperty(mid);
	}
	
	public String getFromId() {
		return this.fromId.get();
	}
	public void setFromId(String fromId) {
		this.fromId.set(fromId);
	}
	
	public String getToId() {
		return this.toId.get();
	}
	public void setToId(String toId) {
		this.toId.set(toId);
	}
	
	public String getTitle() {
		return this.title.get();
	}
	public void setTitle(String title) {
		this.title.set(title);
	}
	
	public String getMassage() {
		return this.massage.get();
	}
	public void setMassage(String massage) {
		this.massage.set(massage);
	}
	
	public Integer getMid() {
		return this.mid.get();
	}
	public void setMid(Integer mid) {
		this.mid.set(mid);
	}
}

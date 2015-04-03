package db.model;

public class User {
	private int id;
	private String lastname;
	private String firstname;
	private String middlename;
	private int age;
	
	public User() {
		
	}
	
	public User(String lastname, String firstname, String middlename, int age) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.middlename = middlename;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getMiddlename() {
		return middlename;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
}
package sport.model;

public class User {
	private int id;
	private String name;
	private int height;
	private int weight;
	private int age;
	private int body_mass;
	
	public User(int id, String name, int height, int weight, int age, int body_mass) {
		this.id = id;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.age = age;
		this.body_mass = body_mass;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getBodyMass() {
		return body_mass;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setBodyMass(int body_mass) {
		this.body_mass = body_mass;
	}
}

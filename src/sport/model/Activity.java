package sport.model;

public class Activity {
	private int id;
	private String name;
	
	public Activity(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}

package vttp2022.mp2.shop.server.models;

public class User {
    private String id;
    private String name;
	private String address;
	private String email;

    public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }

	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }

    
}

package com.blackphoton.planetclicker.objectType;

public class Era {
	private String name;
	private Exponent pop_req;
	private String imagePath;
	public Era(String name, Exponent pop_req, String imagePath){
		this.imagePath = imagePath;
		this.name = name;
		this.pop_req = pop_req;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Exponent getPop_req() {
		return pop_req;
	}

	public void setPop_req(Exponent pop_req) {
		this.pop_req = pop_req;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}

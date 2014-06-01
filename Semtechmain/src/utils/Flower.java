package utils;

public class Flower {
	String name;
	String type;
	String colour;
	double pricePerUnit;
	int quantity;

	public Flower(String name, String type, String colour, double pricePerUnit,
			int quantity) {
		super();
		this.name = name;
		this.type = type;
		this.colour = colour;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
	}
	
	public Flower()
	{
		
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

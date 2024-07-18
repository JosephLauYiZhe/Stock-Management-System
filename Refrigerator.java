public class Refrigerator extends Product{ 
	
	private String doorDesign;
	private String color;
	private double capacity;
	
	//Parameterized constructor
	public Refrigerator(String itemNumber, String itemName, int quantity, double price, String doorDesign, boolean isActive, String color, double capacity) {
		super(itemNumber, itemName, quantity, price);
		super.setActive(isActive);
		this.doorDesign = doorDesign;
		this.color = color;
		this.capacity = capacity;
	}
	
	//getters & setters
	public String getDoorDesign() {
		return doorDesign;
	}
	
	public void setDoorDesign(String doorDesign) {
		this.doorDesign = doorDesign;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public double getCapacity() {
		return capacity;
	}
	
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	//method to calculate value of stock of a refrigerator
	public double calculateRefrigeratorValue(){
		return super.getInventoryValue();
	}
	
	@Override
	public String toString() {
		return "Item number : " + getItemNumber() +
				"\nProduct name : " + getItemName() +
				"\nDoor design : " + getDoorDesign() +
				"\nColor : " + getColor() +
				"\nCapacity (in Litres): " + getCapacity() +
				"\nQuantity available: " + getQuantity() +
				"\nPrice (RM) : " + getPrice() +
				"\nInventory value (RM): " + calculateRefrigeratorValue() +
				"\nProduct status : " + (isActive() ? "Active" : "Discontinued");	
	}
	
}

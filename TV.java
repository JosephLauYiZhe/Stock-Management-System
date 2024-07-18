
public class TV extends Product {

	private String type;
	private String resolution;
	private double displaySize;
	
	//Parameterized constructor
	public TV(String itemNumber, String itemName, int quantity, double price, boolean isActive, String type, String resolution, double displaySize) {
		super(itemNumber, itemName, quantity, price);
		super.setActive(isActive);
		this.type = type;
		this.resolution = resolution;
		this.displaySize = displaySize;
	}
	
	//Getter & Setters 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public double getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(double displaySize) {
		this.displaySize = displaySize;
	}
	
	//method to calculate value of stock of a TV
	public double calculateTVValue(){
		return super.getInventoryValue();
	}
	
	@Override
	public String toString() {
		return "Item number : " + getItemNumber() +
				"\nProduct name : " + getItemName() +
				"\nScreen type : " + getType() +
				"\nResolution : " + getResolution() +
				"\nDisplay size: " + getDisplaySize() +
				"\nQuantity available: " + getQuantity() +
				"\nPrice (RM) : " + getPrice() +
				"\nInventory value (RM): " + calculateTVValue() +
				"\nProduct status : " + (isActive() ? "Active" : "Discontinued");

	}
}

public abstract class Product {

	String itemName;
	int quantity;
	String itemNumber;
	double price;
	boolean isActive;
	
	public Product() {
		isActive = true;
	}
	
	public Product(String itemNumber, String itemName, int quantity, double price) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        isActive = true;     
    }
	
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public double getInventoryValue() {
		return price * quantity;
	}
	
	public void addQty(int qty) {
		if (isActive)
			quantity += qty;
//		else
//			System.out.println("This product line is discountinued. Add stock failed.");
	}
	
	public void deductQty(int qty) {
		if (isActive) {
		    quantity -= qty;
//			else if (quantity == 0)
//				System.out.println("Deduct stock failed. The current stock less than the deduct value.");			
//		}
//		else
//			
		}
	}
	
	@Override
	public String toString() {
		return ("Item number : " + getItemNumber() +
		   "\nProduct name : " + getItemName() +
		   "\nQuantity available : " + getQuantity() +
		   "\nPrice (RM) : " + getPrice() +
		   "\nInventory value (RM): " + getInventoryValue() +
		   "\nProduct status : " + (isActive() ? "Active" : "Discontinued"));
	}
}



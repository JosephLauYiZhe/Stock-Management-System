import java.time.LocalDateTime;

public class History {
	private LocalDateTime time;
	private String type;
	private String itemNumber;
	private int quantity;
	private String operation;
	
	public History(LocalDateTime time, String type, String itemNumber, int quantity, String operation) {
		this.time = time;
		this.type = type;
		this.itemNumber = itemNumber;
		this.quantity = quantity;
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		if (operation == "Discontinue" || operation == "Activate")
			return (time +" "+ type +" " +itemNumber +" "+ null +" "+ operation);
		else
			return(time +" "+ type +" " +itemNumber +" "+ quantity +" "+ operation);
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String getType() {
		return type;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getOperation() {
		return operation;
	}
	
	

}

public class Laptop extends Product {

    private double screenSize;
    private String processor;
    private String ram;
    private String storage;

    // Parameterized constructor
    public Laptop(String itemNumber, String itemName, int quantity, double price, boolean isActive, double screenSize, String processor, String ram, String storage) {
        super(itemNumber, itemName, quantity, price);
        super.setActive(isActive);
        this.screenSize = screenSize;
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
    }

    // Getters & Setters
    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    // Method to calculate value of stock of a laptop
    public double calculateLaptopValue() {
        return super.getInventoryValue();
    }

    @Override
    public String toString() {
        return "Item number: " + getItemNumber() +
                "\nProduct name: " + getItemName() +
                "\nScreen size (inches): " + getScreenSize() +
                "\nProcessor: " + getProcessor() +
                "\nRAM (GB): " + getRam() +
                "\nStorage: " + getStorage() +
                "\nQuantity available: " + getQuantity() +
                "\nPrice (RM): " + getPrice() +
                "\nInventory value (RM): " + calculateLaptopValue() +
                "\nProduct status: " + (isActive() ? "Active" : "Discontinued");
    }
}

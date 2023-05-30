package Projeto1;

public class Part {
    private String code;
    private String name;
    private String brand;
    private String category;
    private int stockQuantity;
    private double price;
    private String supplier;

    public Part(String code, String name, String brand, String category, int stockQuantity, double price, String supplier) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Part{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                '}';
    }

    // Getters e setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}

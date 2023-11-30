package domain;

import java.time.LocalDate;

public class Purchase {
    private int id;
    private LocalDate date;
    private String itemName;
    private int quantity;
    private double unitPrice;

    public Purchase(int id, LocalDate date, String itemName, int quantity, double unitPrice) {
        this.id = id;
        this.date = date;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "model.Purchase{" +
                "id=" + id +
                ", date=" + date +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

package domain;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private int age;
    private String email;
    private String telephoneNumber;
    private Address address;
    private boolean isPremium;

    private List<Purchase> purchaseHistory;

    public Client(int id, String name, int age, String email, String telephoneNumber, Address address, boolean isPremium) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.isPremium = isPremium;

        this.purchaseHistory = new ArrayList<>();
    }

    public void addPurchase(Purchase p) {
        this.purchaseHistory.add(p);
    }

    public List<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    @Override
    public String toString() {
        return "model.Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", address=" + address +
                ", isPremium=" + isPremium +
                ", purchaseHistory=" + purchaseHistory +
                '}';
    }
}

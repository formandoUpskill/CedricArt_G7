package domain;

public class Address {
    private String street;
    private String city;
    private String code;

    public Address(String street, String city, String code) {
        this.street = street;
        this.city = city;
        this.code = code;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "model.model.Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

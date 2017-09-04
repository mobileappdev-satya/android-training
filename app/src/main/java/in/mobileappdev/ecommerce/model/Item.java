package in.mobileappdev.ecommerce.model;

/**
 * Created by Techjini on 8/22/2017.
 */

public class Item {

    private int id;
    private String name;
    private String description;
    private int price;
    private int quantity;
    private int discount;
    private String url;

    public Item(int itemId, String itemName, String itemDesc, int itemCost, int itemQuantity, int disciount, String url) {
        this.id = itemId;
        this.name = itemName;
        this.description = itemDesc;
        this.price = itemCost;
        this.quantity = itemQuantity;
        this.discount = disciount;
        this.url = url;
    }



    //getter setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

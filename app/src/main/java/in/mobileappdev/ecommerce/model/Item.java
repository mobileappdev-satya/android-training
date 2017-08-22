package in.mobileappdev.ecommerce.model;

/**
 * Created by Techjini on 8/22/2017.
 */

public class Item {

    private int itemId;
    private String itemName;
    private String itemDesc;
    private int itemCost;
    private int itemQuantity;

    public Item(int itemId, String itemName, String itemDesc, int itemCost, int itemQuantity){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }


    //getter setters


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public int getItemCost() {
        return itemCost;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}

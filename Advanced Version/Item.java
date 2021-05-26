/**
 * Author: Omer Basar
 * Filename: Item.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

public class Item implements java.io.Serializable
{
    // Initialize private attributes of class
    private String id, name, description, seller, origin;
    private int price;

    /**
     * Constructor Method
     */
    public Item(String id, String name, String description, String seller, int price, String origin)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.origin = origin;
    }

    /**
     * getID: gives the id String
     *
     * @return this.id - id of item
     */
    public String getID() { return this.id; }

    /**
     * getName: gives the name String
     *
     * @return this.name - name of item
     */
    public String getName() { return this.name; }

    /**
     * getDescription: gives the description String
     *
     * @return this.response - description of item
     */
    public String getDescription() { return this.description; }

    /**
     * getSeller: gives the seller String
     *
     * @return this.response - seller of item
     */
    public String getSeller() { return this.seller; }

    /**
     * getPrice: gives the price int
     *
     * @return this.response - price of item
     */
    public int getPrice() { return this.price; }

    /**
     * getOrigin: gives the buyer/seller request origin
     *
     * @return this.response - origin of transaction request
     */
    public String getOrigin() { return this.origin; }

    /**
     * setId: changes the id with the one provided from the parameter
     *
     * @param newID - String containing new message body
     */
    public void setId(String newID) { this.id = newID; }

    /**
     * setName: changes the name with the one provided from the parameter
     *
     * @param newName - String containing new message body
     */
    public void setName(String newName) { this.name = newName; }

    /**
     * setDescription: changes the description with the one provided from the parameter
     *
     * @param newDescription - String containing new message body
     */
    public void setDescription(String newDescription) { this.description = newDescription; }

    /**
     * setSeller: changes the seller with the one provided from the parameter
     *
     * @param newSeller - String containing new message body
     */
    public void setSeller(String newSeller) { this.seller = newSeller; }

    /**
     * setPrice: changes the price with the one provided from the parameter
     *
     * @param newPrice - String containing new message body
     */
    public void setPrice(int newPrice) { this.price = newPrice; }

    /**
     * setOrigin: changes the buyer/seller status with the one provided from the parameter
     *
     * @param newOrigin - String containing new item
     */
    public void setOrigin(String newOrigin) { this.origin = newOrigin; }

    /**
     * toString: returns the attributes of the Item object in String form
     *
     * @return  String containing the id, name, description, seller, and price
     */
    public String toString()
    {
        return "ID: " + this .id + System.lineSeparator() +
                "Item: " + this.name + System.lineSeparator() +
                "Description: " + this.description + System.lineSeparator() +
                "Seller: " + this.seller + System.lineSeparator() +
                "Price: " + this.price + "₵" + System.lineSeparator() +
                "----------------------";
    }

}

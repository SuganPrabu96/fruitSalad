package OrderHistory;

/**
 * Created by Suganprabu on 26-05-2015.
 */
public class TransactionDetailsClass {

    private String name, price, quantity, total;

    public TransactionDetailsClass(String name, String price, String quantity, String total){

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getQuantity() { return quantity; }
    public String getTotal() { return total; }
}

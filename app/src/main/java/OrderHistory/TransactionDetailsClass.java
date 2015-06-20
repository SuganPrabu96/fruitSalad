package OrderHistory;

/**
 * Created by Suganprabu on 26-05-2015.
 */
public class TransactionDetailsClass {

    private String name, price, quantity, unit, total;

    public TransactionDetailsClass(String name, String price, String quantity, String unit, String total){

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.total = total;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public String getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public String getTotal() { return total; }
}

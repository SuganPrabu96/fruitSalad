package Cart;

/**
 * Created by srikrishna on 19-04-2015.
 */
public class CartItemsClass {


    public String cartitemname;
    public String cartitemprice;
    private int productId;
    private int q;
    public float quantity;
    private String unit;
    private Character changeable;

    public CartItemsClass(String itemtitle,String cartitemprice, int productId, int q, float quantity, String unit, Character changeable) {

        this.cartitemname = itemtitle;
        this.cartitemprice = cartitemprice;
        this.productId = productId;
        this.q = q;
        this.quantity = quantity;
        this.unit = unit;
        this.changeable = changeable;

    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getcartItemname() {
        return cartitemname;
    }
    public String getCartitemprice() {  return  cartitemprice; }
    public int getProductId() { return productId; }
    public int getQ() { return q; }
    public String getUnit() { return unit; }
    public Character getChangeable() { return changeable; }
    public float getQuantity() { return quantity; }
}




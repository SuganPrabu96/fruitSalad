package Cart;

/**
 * Created by srikrishna on 19-04-2015.
 */
public class CartItemsClass {


    public String cartitemname;
    public String cartitemprice;
    public int itemquantity;
    private int productId;
    private int q;
    private float quantity;
    private String unit;
    private Character changeable;

    public CartItemsClass(String itemtitle, int cartquantity,String cartitemprice, int productId, int q, float quantity, String unit, Character changeable) {

        this.cartitemname = itemtitle;
        this.itemquantity = cartquantity;
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
    public int getQuantity(){return itemquantity;
    }
    public String getCartitemprice() {  return  cartitemprice; }
    public int getProductId() { return productId; }
    public int getQ() { return q; }
    public String getUnit() { return unit; }
    public Character getChangeable() { return changeable; }
    public float getQty() { return quantity; }
}




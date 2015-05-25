package Cart;

/**
 * Created by srikrishna on 19-04-2015.
 */
public class CartItemsClass {


    public String cartitemname;
    public String cartitemprice;
    public int itemquantity;
    private int categId, subcategId, productId;

    public CartItemsClass(String itemtitle, int cartquantity,String cartitemprice, int categId, int subcategId, int productId) {

        this.cartitemname = itemtitle;
        this.itemquantity = cartquantity;
        this.cartitemprice = cartitemprice;
        this.categId = categId;
        this.subcategId = subcategId;
        this.productId = productId;

    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getcartItemname() {
        return cartitemname;
    }
    public int getQuantity(){return itemquantity;
    }
    public String getCartitemprice() {  return  cartitemprice; }
    public int getCategId() { return categId; }
    public int getSubcategId() { return subcategId; }
    public int getProductId() { return productId; }
}




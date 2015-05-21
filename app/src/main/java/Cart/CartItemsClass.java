package Cart;

/**
 * Created by srikrishna on 19-04-2015.
 */
public class CartItemsClass {


    public String cartitemname;
    public String cartitemprice;
    public int itemquantity;

    public CartItemsClass(String itemtitle, int cartquantity,String cartitemprice) {

        this.cartitemname = itemtitle;
        this.itemquantity = cartquantity;
        this.cartitemprice = cartitemprice;

    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getcartItemname() {
        return cartitemname;
    }
    public int getQuantity(){return itemquantity;
    }
    public String getCartitemprice() {  return  cartitemprice; }


}




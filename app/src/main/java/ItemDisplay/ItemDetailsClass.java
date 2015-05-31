package ItemDisplay;

/**
 * Created by srikrishna on 06-03-2015.
 */
public class ItemDetailsClass {

    public String itemtitle;

    public Double itemprice;

    public Double itemMRP;

    private int productid;


    public ItemDetailsClass(String itemtitle, Double itemprice, Double itemMRP, int productid) {

        this.itemtitle = itemtitle;
        this.itemprice = itemprice;
        this.itemMRP = itemMRP;
        this.productid = productid;
    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getItemtitle() {
        return itemtitle;
    }

    public Double getItemprice() { return itemprice; }

    public Double getItemMRP() { return itemMRP; }

    public int getProductid() { return productid; }
}

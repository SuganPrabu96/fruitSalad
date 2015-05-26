package ItemDisplay;

/**
 * Created by srikrishna on 06-03-2015.
 */
public class ItemDetailsClass {

    public String itemtitle;

    public String itemimgurl = "none";

    public Double itemprice;

    public Double itemMRP;

    private int productid;


    public ItemDetailsClass(String itemtitle, String itemimgurl, Double itemprice, Double itemMRP, int productid) {

        this.itemtitle = itemtitle;
        this.itemimgurl = itemimgurl;
        this.itemprice = itemprice;
        this.itemMRP = itemMRP;
        this.productid = productid;
    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getItemtitle() {
        return itemtitle;
    }

    public String getItemimgurl() {
        return itemimgurl;
    }

    public Double getItemprice() { return itemprice; }

    public Double getItemMRP() { return itemMRP; }

    public int getProductid() { return productid; }
}

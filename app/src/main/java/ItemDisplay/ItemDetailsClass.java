package ItemDisplay;

/**
 * Created by srikrishna on 06-03-2015.
 */
public class ItemDetailsClass {

    public String itemtitle;

    public Double itemprice;

    public Double itemMRP;

    private int productid;

    private float qty;

    private String unit;

    private char changeable;

    private int q;

    public ItemDetailsClass(String itemtitle, Double itemprice, Double itemMRP, int productid, float qty, String unit, char changeable, int q) {

        this.itemtitle = itemtitle;
        this.itemprice = itemprice;
        this.itemMRP = itemMRP;
        this.productid = productid;
        this.qty = qty;
        this.unit = unit;
        this.changeable = changeable;
        this.q = q;
    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getItemtitle() {
        return itemtitle;
    }

    public Double getItemprice() { return itemprice; }

    public Double getItemMRP() { return itemMRP; }

    public int getProductid() { return productid; }

    public Character getChangeable() { return changeable; }

    public int getQ() { return q; }

    public String getUnit() { return unit; }

    public float getQty() { return qty; }
}

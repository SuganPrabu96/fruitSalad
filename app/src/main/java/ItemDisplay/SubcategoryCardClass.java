package ItemDisplay;

/**
 * Created by srikrishna on 03-04-2015.
 */
public class SubcategoryCardClass {

    private String subcategtitle;

    private int subcategimg;

    private int subcategid;

    private int categid;


    public SubcategoryCardClass(String subcategtitle, int subcategimg, int categid, int subcategid) {

        this.subcategtitle = subcategtitle;
        this.subcategimg = subcategimg;
        this.subcategid = subcategid;
        this.categid = categid;
    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getSubcategtitle() {
        return subcategtitle;
    }

    public int getSubcategimg() {
        return subcategimg;
    }

    public int getSubcategid() { return subcategid; }

    public int getCategid() { return categid; }
}


package ItemDisplay;

/**
 * Created by srikrishna on 03-04-2015.
 */
public class CategoryCardClass {

    private String categtitle;

    private int categimg;

    private int categid;


    public CategoryCardClass(String categtitle, int categimg, int categid) {

        this.categtitle = categtitle;
        this.categimg = categimg;
        this.categid = categid;
    }

    //   public String getItemcateg(){ return itemcateg;}
    public String getCategtitle() {
        return categtitle;
    }

    public int getCategimg() {
        return categimg;
    }

    public int getCategid() { return categid; }
}


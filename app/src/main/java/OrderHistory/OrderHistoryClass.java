package OrderHistory;

/**
 * Created by Suganprabu on 22-05-2015.
 */
public class OrderHistoryClass {

    private String orderHistoryId, orderHistoryAddress, orderHistoryName, orderHistoryPrice, orderHsitoryPhone, orderHistoryDate;

    public OrderHistoryClass(String id, String name, String phone, String address, String price, String date){

        this.orderHistoryId = id;
        this.orderHistoryAddress = address;
        this.orderHistoryName = name;
        this.orderHsitoryPhone = phone;
        this.orderHistoryPrice = price;
        this.orderHistoryDate = date;
    }

    public String getOrderHistoryId(){
        return orderHistoryId;
    }
    public String getOrderHistoryAddress(){
        return orderHistoryAddress;
    }
    public String getOrderHistoryName(){
        return orderHistoryName;
    }
    public String getOrderHistoryPrice(){
        return orderHistoryPrice;
    }
    public String getOrderHsitoryPhone() { return orderHsitoryPhone; }
    public String getOrderHistoryDate() { return orderHistoryDate; }
}

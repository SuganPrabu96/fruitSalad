package OrderHistory;

/**
 * Created by Suganprabu on 22-05-2015.
 */
public class OrderHistoryClass {

    public String orderHistoryId, orderHistoryAddress, orderHistoryDate, orderHistoryPrice;

    public OrderHistoryClass(String id, String date, String address, String price){

        this.orderHistoryId = id;
        this.orderHistoryAddress = address;
        this.orderHistoryDate = date;
        this.orderHistoryPrice = price;
    }

    public String getOrderHistoryId(){
        return orderHistoryId;
    }
    public String getOrderHistoryAddress(){
        return orderHistoryAddress;
    }
    public String getOrderHistoryDate(){
        return orderHistoryDate;
    }
    public String getOrderHistoryPrice(){
        return orderHistoryPrice;
    }


}

package util;

import java.util.Arrays;
import java.util.List;

import NavigationDrawer.NavDrawerItem;
import app.TheDreamStop.R;

public class data {
    public static NavDrawerItem[] navtitles={
            new NavDrawerItem("Products", R.drawable.products),
            new NavDrawerItem("My Account", R.drawable.my_account),
            new NavDrawerItem("General Settings", R.drawable.settings),
            new NavDrawerItem("Order History", R.drawable.orders),
            new NavDrawerItem("About Us", R.drawable.about),
            new NavDrawerItem("FAQ", R.drawable.faq),
            new NavDrawerItem("Help", R.drawable.help),
            new NavDrawerItem("Invite a friend", R.drawable.invite),
            new NavDrawerItem("Logout", R.drawable.logout)
    };
    public static List<NavDrawerItem> getNavDrawerItems(){
        return Arrays.asList(navtitles);
    }
}

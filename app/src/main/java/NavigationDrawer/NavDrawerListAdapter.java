package NavigationDrawer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.TheDreamStop.R;

public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<NavDrawerItem> navDrawerItems;
    private ImageView imgIcon;
    private TextView txtTitle;
    private int selectedItem;

    public NavDrawerListAdapter(Context context, List<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    public void setSelectedItem(int selectedItem){
        this.selectedItem = selectedItem;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.nav_draw_list_element, null);
        }

        imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        if(position == selectedItem){
            txtTitle.setTextColor(context.getResources().getColor(R.color.NavigationBarSelectedItemText));
        } else{
            txtTitle.setTextColor(context.getResources().getColor(R.color.NavigationBarUnselectedItemText));
        }

        return convertView;
    }

    public void setSelectedItemTextColor(int position){

    }

}

package HelpViewPager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.TheDreamStop.R;

/**
 * Created by Suganprabu on 18-03-2015.
 */
public class Help_Page1 extends Fragment {

    public static Fragment newInstance(Context context) {
        Help_Page1 f = new Help_Page1();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_help_page1, null);
        return root;
    }

}

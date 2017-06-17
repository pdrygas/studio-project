package edu.agh.pl.mobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JaruuS on 2017-05-31.
 */

public class CustomCategoriesListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;

    public CustomCategoriesListAdapter(Activity context, ArrayList itemname) {
        super(context, R.layout.activity_list_categories, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_element_only_title, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);

        txtTitle.setText(itemname.get(position));
        return rowView;

    };
}

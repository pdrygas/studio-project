package edu.agh.pl.mobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JaruuS on 2017-04-25.
 */

public class CustomResourcesListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<String> itemdescription;

    public CustomResourcesListAdapter(Activity context, ArrayList itemname, ArrayList itemdescription) {
        super(context, R.layout.activity_list_categories, itemname);
        // TODO Auto-generated constructor stub

        this.itemdescription=itemdescription;
        this.context=context;
        this.itemname=itemname;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_element_with_description, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname.get(position));
        extratxt.setText(itemdescription.get(position)+"...");
        return rowView;

    };
}

package project.major.itemsniper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kurt on 4/7/2017.
 */
public class DrawerAdapter extends BaseAdapter {

    private DrawerListItemBuilder builder;
    private Context context;

    public DrawerAdapter(DrawerListItemBuilder builder, Context context){
        this.builder = builder;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.builder.getLayoutCount();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = builder.getView(position,parent);
        return convertView;
    }
}

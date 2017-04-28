package project.major.itemsniper;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Kurt on 4/7/2017.
 */
public class DrawerListItemBuilder {

    private ArrayList<Integer> layouts = new ArrayList<>();
    private ArrayList<DrawerBuilderInterface> modifiers = new ArrayList<>();
    private Context context;

    public DrawerListItemBuilder(Context context){
        this.context = context;
    }
    public ArrayList<Integer> getLayoutList(){

        return this.layouts;
    }

    public int getLayoutCount(){
        return this.layouts.size();
    }

    public View getView(int position,ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(layouts.get(position),parent,false);
        modifiers.get(position).modifyView(v);

        return v;
    }
    //Specify the layout we want to add to the builder
    public DrawerListItemBuilder add(int layout,DrawerBuilderInterface modifier ){
        this.layouts.add(layout);
        this.modifiers.add(modifier);
        return this;
    }
}

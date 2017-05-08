package project.major.itemsniper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kurt on 5/5/2017.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    private ArrayList<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView name;
        public TextView price;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView)v.findViewById(R.id.item_image);
            name = (TextView)v.findViewById(R.id.item_name);
            price = (TextView)v.findViewById(R.id.item_price);
        }
    }

    public ItemsAdapter(ArrayList<Item> items){
        this.items = items;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {

        Log.i("OnbindView","called");
        Item current = items.get(position);

        holder.image.setImageResource(current.getDrawable_id());
        holder.name.setText(current.getName());
        holder.price.setText("$"+current.getPrice()+" JMD");
    }


    @Override
    public int getItemCount() {
        Log.i("count",items.size()+"");
        return items.size();
    }
}

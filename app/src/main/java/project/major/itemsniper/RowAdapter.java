package project.major.itemsniper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kurt on 5/5/2017.
 */
public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ViewHolder> {

    private ArrayList<Row> rows;
    private Context context;

    public RowAdapter(ArrayList<Row> rows,Context c){
        this.rows = rows;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        ViewHolder holder = new ViewHolder(v,context);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Row row = rows.get(position);
        holder.title.setText(row.getTitle());
        holder.description.setText(row.getDescription());
        holder.recyclerView.setAdapter(new ItemsAdapter(row.getItems()));
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        RecyclerView recyclerView;
        public ViewHolder(View v,Context c) {
            super(v);
            recyclerView = (RecyclerView)v.findViewById(R.id.item_row);
            recyclerView.setLayoutManager(new LinearLayoutManager(c,LinearLayoutManager.HORIZONTAL,false));

            title = (TextView)v.findViewById(R.id.row_title);
            description = (TextView)v.findViewById(R.id.row_description);
        }
    }
}

package project.major.itemsniper;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import java.util.ArrayList;


/**
 * Created by Kurt on 4/16/2017.
 */
public class ItemsPageFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.items_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       setUpMainRecycler();
    }

    private void setUpMainRecycler(){

        ArrayList<Item> i = new ArrayList<>();
        i.add(new Item("Verizon LG","http://socks",R.drawable.phone1,10500));
        i.add(new Item("Alcatel Blu","http://socks",R.drawable.phone2,4000));
        i.add(new Item("Iphone 6+","http://socks",R.drawable.phone3,90000));
        i.add(new Item("Samsung S4","http://socks",R.drawable.phone4,2000));
        i.add(new Item("Samsung Something","http://socks",R.drawable.phone5,5000));
        i.add(new Item("Samsung g3", "http://socks", R.drawable.phon6, 5350));

        ArrayList<Item> j = new ArrayList<>();
        j.add(new Item("Audi A6","",R.drawable.car1,2000000));
        j.add(new Item("2016 Mark X","",R.drawable.car2,150000));
        j.add(new Item("Jaguar","",R.drawable.car3,5000000));

        ArrayList<Row> rows = new ArrayList<>();
        rows.add(new Row("Latest In Tech","Check out these new cell phones",i));
        rows.add(new Row("Looking for A New Ride", "These cars are in your area", j));
        rows.add(new Row("Test", "This is a test", i));

        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.item_display_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(new RowAdapter(rows, getContext()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

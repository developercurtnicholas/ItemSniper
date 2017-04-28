package project.major.itemsniper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kurt on 3/12/2017.
 */
public class InitialActivity extends AppCompatActivity{

    private ViewPager pager;
    private CustomPagerAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_layout);
        createFragments();
        initializeFragmentAdapter();
        initializeViewPager();
    }

    private void createFragments(){
        fragments.add(new SignUpFragment());
        fragments.add(new SignInFragment());
    }

    private void  initializeViewPager(){
        pager = (ViewPager) findViewById(R.id.start_pager);

        //PAGER SHOULD NOT RETURN NULL
        if(pager == null){
            Toast.makeText(getApplicationContext(),"The pager is null",Toast.LENGTH_SHORT).show();
        }else{
            pager.setAdapter(adapter);
        }

    }
    private  void initializeFragmentAdapter(){
        adapter = new CustomPagerAdapter(getSupportFragmentManager(),fragments);
    }
}

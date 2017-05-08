package project.major.itemsniper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

/**
 * Created by carva on 5/5/2017.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {// GoogleMap.OnInfoWindowClickListener{//}, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;
    LatLng uTech = new LatLng(18.018624,-76.744458);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_layout);
       final SupportMapFragment mapFragment= (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
}



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Instantiate cluster manager
        mClusterManager = new ClusterManager<>(this,mMap);

        //Set camera position for activity startup
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(18.018624,-76.744458), 10));

        //Instantiate custom cluster renderer
        final ClusterRenderer renderer = new CustomClusterRenderer(this, mMap, mClusterManager);

        //Assign custom renderer to cluster manager
        mClusterManager.setRenderer(renderer);

        //Set an Onclick method for markers
        mClusterManager
                .setOnClusterItemClickListener(
                        new ClusterManager.OnClusterItemClickListener<MyItem>() {
                            @Override
                            public boolean onClusterItemClick(MyItem myItem) {
                                Toast.makeText(MapActivity.this, "Cluster item click", Toast.LENGTH_SHORT).show();
                                //renderer.
                                return false;
                            }
                        }
                );

        //Set an Onclick method for clusters
        mClusterManager
                .setOnClusterClickListener(
                        new ClusterManager.OnClusterClickListener<MyItem>() {
                            @Override
                            public boolean onClusterClick(Cluster cluster) {
                                Toast.makeText(MapActivity.this, "Cluster click", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                );
        //cluster listeners
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        //set all visible markers' and clusters' info adapters to custom info window adapter
        mMap.setInfoWindowAdapter(
                new CustomInfoWindowAdapter(LayoutInflater.from(this)));

        //Add locations to map
        addItems();

        //reclusters once items are added
        mClusterManager.cluster();

    }


    //Method to add locations to map
     private void addItems() {
        double lat = 18.018624;
        double lng = -76.744458;

         //Test locations
        for(int i =0; i< 10; i++){
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat,lng,"title " + i+1, "avalability " + i+1);
            mClusterManager.addItem(offsetItem);

        }

        //Test locations
        MyItem sumn = new MyItem(17.987836,-76.838338, "bap","Yes");
        mClusterManager.addItem(sumn);

         MyItem sumn2 = new MyItem(17.998459, -76.865199,"sumweh","No");
         mClusterManager.addItem(sumn2);

         MyItem sumn3 = new MyItem(18.160129, -77.066871, "HAHA","NO");
         mClusterManager.addItem(sumn3);

         MyItem sumn4 = new MyItem(18.140616, -77.029817, "UP","YES");
         mClusterManager.addItem(sumn4);

        MyItem sumn5 = new MyItem(18.149231, -77.042978,"YUSH","NO");
         mClusterManager.addItem(sumn5);

         MyItem sumn6 = new MyItem(18.160985, -77.071120,"Zaga","YES");
         mClusterManager.addItem(sumn6);

         MyItem sumn7 = new MyItem(18.165990, -77.072375,"Another one","YES");
         mClusterManager.addItem(sumn7);
    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {

    }
}

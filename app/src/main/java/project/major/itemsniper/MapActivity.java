package project.major.itemsniper;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carva on 5/5/2017.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>,
        GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    public static GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    ArrayList<LatLng> MarkerPoints;
    Button mapNorm;
    Button mapHybrid;
    Button mapSatellite;
    static List<Polyline> polylines = new ArrayList<>();
    public static Polyline polyLine;
    //LatLng uTech = new LatLng(18.018624,-76.744458);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_layout);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapType();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)==
                    PackageManager.PERMISSION_GRANTED){
                buildGoogleAPiCLIENT();
                mMap.setMyLocationEnabled(true);
            }else{
                checkLocationPermission();
            }
        }
        else{
            buildGoogleAPiCLIENT();
            mMap.setMyLocationEnabled(true);
        }
    }

    private void setMapType(){
        mapNorm = (Button) findViewById(R.id.map_type_norm);
        mapNorm.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(MapActivity.this, "Map type changed: Normal",Toast.LENGTH_SHORT).show();
            }
        });

        mapHybrid = (Button) findViewById(R.id.map_type_hyb);
        mapHybrid.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(MapActivity.this, "Map type changed: Hybrid",Toast.LENGTH_SHORT).show();
            }
        });

        mapSatellite = (Button) findViewById(R.id.map_type_sate);
        mapSatellite.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(MapActivity.this, "Map type changed: Satellite",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onMapSearch(View view){
        EditText locat = (EditText) findViewById(R.id.map_search);
        String location = locat.getText().toString();
        List<Address> addressList = null;

        populateMap();

        if(!location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try{
                addressList = geocoder.getFromLocationName(location,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList != null) {
                Address address = addressList.get(0);
                LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latlng).title("marker"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
            }
            else{
                Toast.makeText(this,"nothing retrieved",Toast.LENGTH_SHORT).show();
            }

            //mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        }else
        {
            Toast.makeText(this,"No location to display",Toast.LENGTH_SHORT).show();
        }
    }

    private void populateMap() {
        //Instantiate cluster manager
        mClusterManager = new ClusterManager<>(MapActivity.this,mMap);

        //Instantiate custom cluster renderer
        final ClusterRenderer<MyItem> renderer = new CustomClusterRenderer(this, mMap, mClusterManager);

        //Assign custom renderer to cluster manager
        mClusterManager.setRenderer(renderer);

        //Set an Onclick method for markers
        mClusterManager
                .setOnClusterItemClickListener(
                        new ClusterManager.OnClusterItemClickListener<MyItem>() {
                            @Override
                            public boolean onClusterItemClick(MyItem myItem) {
                                Toast.makeText(MapActivity.this, "Cluster item click", Toast.LENGTH_SHORT).show();
                                if(polyLine != null){
                                    polyLine.remove();
                                }
                                    MarkerPoints = new ArrayList<>();
                                    MarkerPoints.add(mCurrLocationMarker.getPosition());
                                    if (MarkerPoints.size() >= 1) {


                                        MarkerPoints.add(myItem.getPosition());

                                        LatLng origin = mCurrLocationMarker.getPosition();
                                        LatLng dest = myItem.getPosition();

                                        String url = getUrl(origin, dest);
                                        Log.d("onMarkerClick", url);
                                        FetchUrl FetchUrl = new FetchUrl();

                                        FetchUrl.execute(url);


                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                                        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                                    }
                                    else
                                    {
                                        Toast.makeText(MapActivity.this,"Current location not accessible, please enable location permissions", Toast.LENGTH_SHORT).show();
                                    }
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


       //sets
        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        //set all visible cluster item markers' info adapters to custom info window adapter
        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
             new CustomInfoWindowAdapter(LayoutInflater.from(this)));


        //Add locations to map
        addItems();

        //reclusters once items are added
        mClusterManager.cluster();
    }

    protected synchronized void buildGoogleAPiCLIENT() {
        mGoogleApiClient =  new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
            //stuff.a(offsetItem);

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

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest =new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED){
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
            }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public  static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            //Asking user if explanation is needed
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)){


                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
            }
            else{
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }else {
            return true;
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permission[], @NonNull int[]grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED){
                        if(mGoogleApiClient == null){
                            buildGoogleAPiCLIENT();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if(mCurrLocationMarker != null){
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title("Current Position");
        markerOptions.snippet("   You are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if(mGoogleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
        }

    }



    private String getUrl(LatLng origin, LatLng dest) {
// Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data);
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            if (iStream != null) {
                iStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }


}

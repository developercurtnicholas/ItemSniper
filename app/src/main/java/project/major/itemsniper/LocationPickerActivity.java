package project.major.itemsniper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

/**
 * Created by carva on 13/5/2017.
 */

public class LocationPickerActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    GoogleMap picker;
    MarkerOptions pick;
    TextView editText;
    Button mapNorm;
    Button mapHybrid;
    Button mapSatellite;
    Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_picker_layout);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locat_picker_map);
        editText = (TextView) findViewById(R.id.location_picker);
        submit = (Button) findViewById(R.id.submitBtn);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        picker = googleMap;
        picker.setOnMarkerDragListener(this);
        LatLng uTech = new LatLng(18.018624,-76.744458);
        pick = new MarkerOptions().position(uTech).title("Marker title").snippet("Marker descript").draggable(true);
        picker.addMarker(pick);

        CameraPosition cam = new CameraPosition.Builder().target(uTech).zoom(11).build();
        picker.animateCamera(CameraUpdateFactory.newCameraPosition(cam));

        setMapType();
    }

    private void setMapType(){
        mapNorm = (Button) findViewById(R.id.locat_type_norm);
        mapNorm.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                picker.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(LocationPickerActivity.this, "Map type changed: Normal",Toast.LENGTH_SHORT).show();
            }
        });

        mapHybrid = (Button) findViewById(R.id.locat_type_hyb);
        mapHybrid.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                picker.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(LocationPickerActivity.this, "Map type changed: Hybrid",Toast.LENGTH_SHORT).show();
            }
        });

        mapSatellite = (Button) findViewById(R.id.locat_type_sate);
        mapSatellite.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                picker.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(LocationPickerActivity.this, "Map type changed: Satellite",Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(editText.getText().equals("") || editText == null){
                    Toast.makeText(v.getContext(), "Drag marker to select location", Toast.LENGTH_SHORT).show();
                }else {
                    String lat = editText.getText().toString().substring(0, editText.getText().toString().indexOf(",") - 1);
                    String lng = editText.getText().toString().substring(editText.getText().toString().indexOf(",") + 2, editText.getText().toString().length() - 1);
                    Log.d("TAG", lat);
                    Log.d("TAG", lng);
                    Intent intent = new Intent(v.getContext(), BusinessRegisterActivity.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    Toast.makeText(v.getContext(), "Coordinates selected", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng pos = marker.getPosition();
        Double lat = pos.latitude;
        Double lng = pos.longitude;
        DecimalFormat df_lat = new DecimalFormat("#0.000000");
        DecimalFormat df_lng = new DecimalFormat("#0.000000");
        String str_lat = df_lat.format(lat); //String.valueOf(lat);
        String str_lng = df_lng.format(lng);

        editText.setText(str_lat + ", " + str_lng);

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}

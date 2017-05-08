package project.major.itemsniper;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by carva on 6/5/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private final LayoutInflater inflater;


    public CustomInfoWindowAdapter(LayoutInflater mInflater) {
         this.inflater = mInflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //Inflates custom layout for marker info window
        final  View infoWin = inflater.inflate(R.layout.info_window_layout,null);

        //Instanciate and assign layout components
       ImageView itemPic = (ImageView) infoWin.findViewById(R.id.infoWinMarkerImage);

        TextView markerVendor = (TextView) infoWin.findViewById(R.id.infoWinMarkerVendor);

        TextView markerAvail = (TextView) infoWin.findViewById(R.id.infoWinMarkerAvail);

        //Set layout components
        itemPic.setImageResource(R.drawable.utech_test);
        markerVendor.setText(marker.getTitle());
        markerAvail.setText(marker.getSnippet());


        return infoWin;
    }
}

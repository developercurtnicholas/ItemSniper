package project.major.itemsniper;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by carva on 6/5/2017.
 */

public class MyItem implements ClusterItem{
    private final LatLng Position;
    private final String Vendor;
    private final String available;

    public MyItem(double lat, double lng, String vendor, String availability){
        available = availability;
        Position = new LatLng(lat,lng);
        Vendor = vendor;
    }



    @Override
    public LatLng getPosition() {
        return Position;
    }


   public String getVendor() {
        return Vendor;
    }


    public String getAvailable() {
        return available;
    }
}

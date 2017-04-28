package project.major.itemsniper.Reusables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Kurt on 4/17/2017.
 * Reusable class to scale a drawable resource to the specified size in pixels
 */
public class ScaleDrawable {

    private Context context;
    private int drawableId;

    public ScaleDrawable(int idOfDrawableToScale,Context context){
        this.context = context;
        this.drawableId = idOfDrawableToScale;
    }

    public Drawable scale(int widthPx,int heightPx){
        Drawable dr = context.getResources().getDrawable(drawableId);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        // Scale it to 50 x 50
        Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, widthPx, heightPx, true));
        return d;
    }
}

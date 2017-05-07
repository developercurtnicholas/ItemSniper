package project.major.itemsniper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

/**
 * Created by Kurt on 5/5/2017.
 */
public class Item {

    private String name;
    private String description;
    private String distributedBy;
    private int price;
    private String clickUrl;
    private Bitmap image;
    private int drawable_id;
    private Drawable drawable;

    ///CONSTRUCTORS///////
    public Item(String name,String clickUrl,@Nullable Bitmap image,int price){

        this.name = name;
        this.clickUrl = clickUrl;
        this.image = image;
        this.price = price;
    }
    public Item(String name,String clickUrl,@Nullable int drawable_id,int price){

        this.name = name;
        this.clickUrl = clickUrl;
        this.drawable_id = drawable_id;
        this.price = price;
    }
    public Item(String name,String clickUrl,@Nullable Drawable drawable,int price){

        this.name = name;
        this.clickUrl = clickUrl;
        this.drawable = drawable;
        this.price = price;
    }

    /// GETTERS AND SETTERS//////////////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDistributedBy() {
        return distributedBy;
    }

    public void setDistributedBy(String distributedBy) {
        this.distributedBy = distributedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}

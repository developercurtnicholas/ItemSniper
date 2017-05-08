package project.major.itemsniper;

import java.util.ArrayList;

/**
 * Created by Kurt on 5/5/2017.
 */
public class Row {

    private String title;
    private String description;
    private ArrayList<Item> items;

    public Row(String title,String description,ArrayList<Item> items){

        this.title = title;
        this.description = description;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}

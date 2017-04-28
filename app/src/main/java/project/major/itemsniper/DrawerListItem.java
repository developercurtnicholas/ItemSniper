package project.major.itemsniper;

/**
 * Created by Kurt on 4/7/2017.
 */

//Class that hold the navigation drawer data for each list item (path to icon and the title)
public class DrawerListItem {
    private String title = "";
    private int icon_resId = -1;

    public DrawerListItem(String title,int icon_resId){
        this.title = title;
        this.icon_resId = icon_resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon_resId() {
        return icon_resId;
    }

    public void setIcon_resId(int icon_resId) {
        this.icon_resId = icon_resId;
    }

    public DrawerListItem(String title){
        this.title = title;
    }
}

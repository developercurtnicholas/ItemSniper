package project.major.itemsniper;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by carva on 10/5/2017.
 */

public class FetchUrl extends AsyncTask<String,Void, String>{
    //MapActivity mapAct = new MapActivity();
    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try{
            data = MapActivity.downloadUrl(url[0]);
            Log.d("Background Task data", data);
        }catch (Exception e){
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask();
        parserTask.execute(result);
    }
}

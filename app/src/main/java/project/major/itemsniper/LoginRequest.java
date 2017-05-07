package project.major.itemsniper;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kurt on 5/6/2017.
 */
public class LoginRequest extends StringRequest {

    public static String LOGIN_URL = "http://www.topnhotch.com/itemsniper/Login.php";
    private HashMap<String,String> params;
    private String email;
    private String password;
    public LoginRequest(String email,String password,Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, LOGIN_URL, listener, errorListener);
        this.email =  email;
        this.password = password;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        params = new HashMap<>();
        params.put("email",email);
        params.put("pass",password);
        return params;
    }
}

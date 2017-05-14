package project.major.itemsniper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carva on 12/5/2017.
 */
            //Will change what this extends. Custom register for business needed.
public class BusinessRegisterActivity extends RegisterActivity{

    //Buttons
    private Button nextButton;

    //Edit texts
    private EditText businessName;
    private EditText category;
    private EditText latitude;
    private EditText longitude;
    private EditText email;
    private EditText pass;
    private EditText confirmPass;

    ArrayList<EditText> fields;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_sign_up);

        //Get the UI elements from the layout
        instantiateUIElements();

        //Register listeners on them
        registerUiListeners();
    }



    private void instantiateUIElements() {

        //Instantiate UI elements
        nextButton = (Button)findViewById(R.id.business_next_button);

        businessName = (EditText)findViewById(R.id.business_name_field);
        category = (EditText)findViewById(R.id.business_category_field);
        latitude = (EditText) findViewById(R.id.business_latitude);
        latitude.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent busi = new Intent(v.getContext(),LocationPickerActivity.class);
                //Toast.makeText(getContext(),"business clicked", Toast.LENGTH_LONG).show();
                startActivity(busi);

            }
        });
        longitude = (EditText) findViewById(R.id.business_longitude);
        email = (EditText)findViewById(R.id.business_email_field);
        pass = (EditText)findViewById(R.id.business_password_field);
        confirmPass = (EditText)findViewById(R.id.confirm_business_password_field);


        addFieldsToList();
    }

    private void addFieldsToList() {

        if(fields != null){
            fields.add(businessName);
            fields.add(category);
            fields.add(latitude);
            fields.add(longitude);
            fields.add(email);
            fields.add(pass);
            fields.add(confirmPass);
        }else{
            fields = new ArrayList<>();
            addFieldsToList();
        }
    }

    //Register listneres for click events for on screen ui elements
    private void registerUiListeners() {
        if(nextButton != null){
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onNextButtonClicked();
                }
            });
        }
    }

    private void onNextButtonClicked() {

        if(clientSideValidate()){
            //Prepare post parameters
            HashMap<String,String> params = new HashMap<>();
            params.put("businessName",businessName.getText().toString());
            params.put("category",category.getText().toString());
            params.put("latitude",latitude.getText().toString());
            params.put("longitude",longitude.getText().toString());
            params.put("email",email.getText().toString());
            params.put("pass",pass.getText().toString());

            findViewById(R.id.loadingPanel_business).setVisibility(View.VISIBLE);

            //Register the user
            registerUser(params);

        }else{
            Toast.makeText(getApplicationContext(),"Oops",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean clientSideValidate() {
        boolean success = true;

        //First check if the fields are filled out
        ArrayList<EditText> e = FormValidator.checkEmpty(fields, R.drawable.invalid_field, R.drawable.valid_background, "Can't be empty");
        Log.i("Empties",e.size()+"");
        if(e.size() >  0){
            success = false;
            Log.i("FALSE","Fields");
        }
        //If password is correct length
        if(!FormValidator.validLength(pass,6,R.drawable.invalid_field,R.drawable.valid_background,"Pass must be at least 6 characters")){
            success = false;
            Log.i("FALSE","length");
        }
        //passwords don't match
        if(!FormValidator.checkEqual(pass,confirmPass,R.drawable.invalid_field,R.drawable.valid_background,"Passwords not the same")){
            success = false;
            Log.i("FALSE","equal");
        }
        //Invalid email
        if(!FormValidator.validEmail(email,R.drawable.invalid_field,R.drawable.valid_background,"Invalid Email")){
            success = false;
            Log.i("FALSE","email");
        }
        Log.i("SUCCESS",success+"");
        return success;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        findViewById(R.id.loadingPanel_business).setVisibility(View.GONE);
        error.printStackTrace();
    }

    @Override
    public void onResponse(String response) {
        findViewById(R.id.loadingPanel_business).setVisibility(View.GONE);
        try {
            JSONObject o = new JSONObject(response);
            if(o.getBoolean("success")){
                Intent i = new Intent(getApplicationContext(),UploadPictureActivity.class);
                startActivity(i);
            }else{
                Log.i("Response","Logic");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Response",response);
    }
}

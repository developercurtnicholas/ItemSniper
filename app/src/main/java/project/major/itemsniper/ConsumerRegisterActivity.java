package project.major.itemsniper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kurt on 5/1/2017.
 */
public class ConsumerRegisterActivity extends RegisterActivity {

    //Buttons
    private Button nextButton;

    //Edit texts
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText pass;
    private EditText confirmPass;

    ArrayList<EditText> fields;


    public ConsumerRegisterActivity(){
        super();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumer_sign_up);

        //Get the UI elements from the layout
        instantiateUIElements();

        //Register listeners on them
        registerUiListners();

    }

    private void instantiateUIElements(){

        //Instantiate UI elements
        nextButton = (Button)findViewById(R.id.consumer_next_button);

        firstName = (EditText)findViewById(R.id.consumer_first_name_field);
        lastName = (EditText)findViewById(R.id.consumer_last_name_field);
        email = (EditText)findViewById(R.id.consumer_email_field);
        pass = (EditText)findViewById(R.id.consumer_password_field);
        confirmPass = (EditText)findViewById(R.id.confirm_consumer_password_field);


        addFieldsToList();
    }

    private void addFieldsToList(){

        if(fields != null){
            fields.add(firstName);
            fields.add(lastName);
            fields.add(email);
            fields.add(pass);
            fields.add(confirmPass);
        }else{
            fields = new ArrayList<>();
            addFieldsToList();
        }


    }

    //Register listneres for click events for on screen ui elements
    private void registerUiListners(){

        if(nextButton != null){
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onNextButtonClicked();
                }
            });
        }
    }

    private void onNextButtonClicked(){

        if(clientSideValidate()){
            //Prepare post parameters
            HashMap<String,String> params = new HashMap<>();
            params.put("firstName",firstName.getText().toString());
            params.put("lastName",lastName.getText().toString());
            params.put("email",email.getText().toString());
            params.put("pass",pass.getText().toString());

            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            //Register the user
            registerUser(params);

        }else{
            Toast.makeText(getApplicationContext(),"Oops",Toast.LENGTH_SHORT).show();
        }
    }


    private boolean clientSideValidate(){
        boolean success = true;

        //First check if the fields are filled out
        ArrayList<EditText> e = FormValidator.checkEmpty(fields, R.drawable.invalid_field, R.drawable.valid_background, "Can't be empty");
        Log.i("Empties",e.size()+"");
        if(e.size() >  0){
            success = false;
            Log.i("FALSE","Fields");
        }
        //If password is correct lenght
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

    //Called if registration failed due to volley error
    @Override
    public void onErrorResponse(VolleyError error) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        error.printStackTrace();
    }

    //Called when we get back a server response of some sort we check the success here
    @Override
    public void onResponse(String response) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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

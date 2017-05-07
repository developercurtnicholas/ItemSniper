package project.major.itemsniper;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kurt on 5/2/2017.
 */
public class FormValidator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static ArrayList<EditText> checkEmpty(ArrayList<EditText> fields, int invalidBground, int validBground, String hint){

        ArrayList<EditText> empties = new ArrayList<>();

        for(EditText e : fields){

            if(e.getText().toString().equalsIgnoreCase("")){

                if(invalidBground >= 0){
                    setInvalid(e,hint,invalidBground);
                }
                empties.add(e);

            }else{
                if(validBground >= 0){
                    e.setBackgroundResource(validBground);
                }
            }
        }
        return empties;
    }

    public static boolean validEmail(EditText e,int invalidBground,int validBground, String hint) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(e.getText().toString());
        if(!matcher.find()){
            if(invalidBground >= 0){
                setInvalid(e,hint,invalidBground);
            }
            return false;
        }else{
            if(validBground >= 0){
                e.setBackgroundResource(validBground);
            }
            return true;
        }
    }

    public static boolean checkEqual(EditText e1, EditText e2,int invalidBGround,int validBground,String hint){
        if(e1.getText().toString().equals(e2.getText().toString())){

            if(validBground >= 0){
                e1.setBackgroundResource(validBground);
            }
            return true;
        }else{
            if(invalidBGround >= 0){
                setInvalid(e1,hint,invalidBGround);
                setInvalid(e2,hint,invalidBGround);
            }
            return false;
        }
    }

    private static interface CustomValidate{
        void validate();
    }

    public static boolean validLength(EditText e, int length, int invalidBground, int validBground, String hint){

        if(e.getText().toString().length() < length){
            if(invalidBground >= 0){
                setInvalid(e,hint,invalidBground);
            }
            return false;
        }else{
            if(validBground >= 0){
                e.setBackgroundResource(validBground);
            }
            return true;
        }
    }

    private static void setInvalid(EditText e, String text, int invalid){

        e.setBackgroundResource(invalid);
        e.setText("");
        e.setHint(text);
    }

}

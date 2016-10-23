package construtec.mobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.*;


/**
 * A login screen that recieves name and ID
 */
public class LoginActivity extends AppCompatActivity{
    // UI references
    private EditText mUserName;
    private EditText mUserId;

    //Used to handle Http request


    /**
     * Creation activity
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/activity_login.xml file
        setContentView(R.layout.activity_login);

        //TODO change this to actual threads on the httpConnection class
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Set up the login form.
        mUserName = (EditText) findViewById(R.id.userName);

        mUserId = (EditText) findViewById(R.id.userId);
    }

    /**
     * Login method, it is called when the correct button is pressed and checks if the user is correct
     * @param view that calls this method
     */
    public void LogIn(View view){
        String userName = mUserName.getText().toString();
        String userId = mUserId.getText().toString();

        if(!userId.equals("")){
            if(userExists(userName, Integer.parseInt(userId))){

            }else{
                mUserName.setError("User Name and Id don't match");
            }
        }
    }

    /**
     * Sign up method, it calls the correspoding window and for the activity
     * @param view that calls this function
     */
    public void SignUp(View view){
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }


    /**
     * Methods that checks if a user exists
     * @param name of the user that wishes to login
     * @param userId of the user that wishes to login
     * @return boolean indicating if the user exists on the Database
     */
    private boolean userExists(String name, int userId){
        String json = httpConnection.getConnection().sendGet("Usuario/" + Integer.toString(userId));
        Log.i("recieved Jason", json);

        try {
            JSONObject obj = new JSONObject(json);

            String nombre = obj.getString("Name");
            int cedula = Integer.parseInt(obj.optString("Id_Number"));

            if(nombre.equals(name) && (userId == cedula)){
                Intent intent = new Intent(this, UserInformation.class);
                intent.putExtra("UserId", obj.optString("Id_Number"));
                intent.putExtra("role", obj.getString("Role_usuario"));
                intent.putExtra("userName", name);
                intent.putExtra("code", obj.getString("Code"));
                intent.putExtra("phone", obj.getString("Phone_Number"));
                startActivity(intent);
                return true;
            }
        }
        catch (JSONException e) {
            return false;
        }
        return false;
    }

    public void IP(View view){
        final Dialog d = new Dialog(LoginActivity.this);
        d.setContentView(R.layout.ip_address);
        Button create = (Button) d.findViewById(R.id.create);
        Button cancelAction = (Button) d.findViewById(R.id.cancel);

        final EditText ipadress = (EditText) d.findViewById(R.id.ipAdress);
        final EditText port = (EditText) d.findViewById(R.id.port);


        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                httpConnection.serviceIp = ipadress.getText().toString();
                httpConnection.port = port.getText().toString();

                d.dismiss();
            }
        });
        cancelAction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
}


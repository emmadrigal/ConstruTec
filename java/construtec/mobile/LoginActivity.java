package construtec.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.*;


/**
 * A login screen that recieves name and ID
 */
public class LoginActivity extends AppCompatActivity{
    // UI references
    private EditText mUserName;
    private EditText mUserId;

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

        // Set up the login form.
        mUserName = (EditText) findViewById(R.id.userName);

        mUserId = (EditText) findViewById(R.id.userId);
    }

    /**
     * Login method, it is called when the correct button is pressed and checks if the user is correct
     * @param view that calls this method
     */
    public void LogIn(View view){
        Intent intent = new Intent(this, ViewAllProjects.class);
        String userName = mUserName.getText().toString();
        String userId = mUserId.getText().toString();
        //TODO get this information from the DB call
        String role = "1";

        if(!userId.equals("")){
            if(userExists(userName, Integer.parseInt(userId))){
                intent.putExtra("UserId", userId);
                intent.putExtra("role", role);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }else{
                mUserName.setError("User Name and Id don't match");
            }
        }
    }

    /**
     * Sign up method, it calls the correspoding window and for the activity
     * @param view
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
    //TODO: Checkear el tipo de usario y segun eso avanzar la interfaz
    public boolean userExists(String name, int userId){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "{\"Nombre\" : \"Emmanuel\", \"Cedula\": 304960478}";

        try {
            JSONObject obj = new JSONObject(json);

            String nombre = obj.getString("Nombre");
            int cedula = Integer.parseInt(obj.optString("Cedula"));

            if(nombre.equals(name) && (userId == cedula)){
                return true;
            }
        }
        catch (JSONException e) {
            return false;
        }
        return false;
    }
}


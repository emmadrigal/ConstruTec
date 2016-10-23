package construtec.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Screen that allows a user to create a new account in the database
 */
public class RegisterUser extends AppCompatActivity {
    private EditText code;
    private Spinner spinner;

    /**
     * Creates the view
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        spinner = (Spinner)  findViewById(R.id.Role);
        code    = (EditText) findViewById(R.id.code);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.Roles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String imc_met = spinner.getSelectedItem().toString();

                if(imc_met.equals("Engineer")){
                    code.setVisibility(View.VISIBLE);
                    code.setText("");
                }
                else{
                    code.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

    }

    /**
     *Registers the user and changes screen
     * @param view that calls the method
     */
    public void LogIn(View view){
        registerUserOnDB();

        Intent intent = new Intent(this, UserInformation.class);
        EditText userName = (EditText) findViewById(R.id.userName);
        EditText userID   = (EditText) findViewById(R.id.userId);
        EditText code     = (EditText) findViewById(R.id.code);
        EditText phone     = (EditText) findViewById(R.id.phoneNumber);

        intent.putExtra("UserId", userName.getText());
        intent.putExtra("userName", userID.getText());
        intent.putExtra("code", code.getText());
        intent.putExtra("phone", phone.getText());

        if(spinner.getSelectedItem().equals("Engineer")) {
            intent.putExtra("role", "1");
        }else if(spinner.getSelectedItem().equals("Client")) {
            intent.putExtra("role", "0");
        }else{
            intent.putExtra("role", "2");
        }
        startActivity(intent);
    }

    /**
     * Makes the call to the Web Service and registers the user
     */
    private void registerUserOnDB(){
        EditText userName = (EditText) findViewById(R.id.userName);
        EditText userID = (EditText) findViewById(R.id.userId);
        EditText code = (EditText) findViewById(R.id.code);
        EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);

        JSONObject json = new JSONObject();
        try {
            json.put("Id_Number", userID.getText());
            json.put("Code", code.getText());
            json.put("Name", userName.getText());
            json.put("Phone_Number", phoneNumber.getText());
            if(spinner.getSelectedItem().equals("Engineer")) {
                json.put("Role_usuario", "1");
            }else if(spinner.getSelectedItem().equals("Client")) {
                json.put("Role_usuario", "0");
            }else{
                json.put("Role_usuario", "2");
            }
            httpConnection.getConnection().sendPost("Usuario", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

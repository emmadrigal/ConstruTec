package construtec.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CreateNewProject extends AppCompatActivity {
    private String userId;
    private EditText name;
    private EditText location;
    private EditText clientId;
    private String engineerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_project);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */
        Intent intent = getIntent();
        engineerID = intent.getStringExtra("userID");

        name = (EditText) findViewById(R.id.name);
        location = (EditText) findViewById(R.id.location);
        clientId = (EditText) findViewById(R.id.ClientId);

    }


    public void createProject(View view){
        addProject();

        //TODO check if add is succesfull before proceding
        Intent intent = new Intent(getBaseContext(), ProjectInformation.class);
        intent.putExtra("projectName", name.getText().toString());
        intent.putExtra("userID", engineerID);
        intent.putExtra("role", "1");
        startActivity(intent);
    }

    public void addProject(){
        //TODO add call to the Web Service
    }

}

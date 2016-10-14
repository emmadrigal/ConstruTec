package construtec.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Screen that allows users to add a new project
 */
public class CreateNewProject extends AppCompatActivity {
    private String engineerID;

    /**
     * Initializes the values in the screen
     * @param savedInstanceState required by android
     */
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
    }

    /**
     * Called to create a new project
     * @param view that calls this method
     */
    public void createProject(View view){
        EditText name = (EditText) findViewById(R.id.name);
        EditText location = (EditText) findViewById(R.id.location);
        EditText clientId = (EditText) findViewById(R.id.ClientId);

        addProject();

        //TODO check if add is succesfull before proceding
        Intent intent = new Intent(getBaseContext(), ProjectInformation.class);
        intent.putExtra("projectName", name.getText().toString());
        intent.putExtra("userID", engineerID);
        intent.putExtra("role", "1");
        startActivity(intent);
    }

    /**
     * Call to the database to add a new project
     */
    public void addProject(){
        //TODO add call to the Web Service
    }

}

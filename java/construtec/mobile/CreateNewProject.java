package construtec.mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CreateNewProject extends AppCompatActivity {
    private String userId;
    private EditText name;
    private EditText location;
    private EditText clientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_project);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        name = (EditText) findViewById(R.id.name);
        location = (EditText) findViewById(R.id.location);
        clientId = (EditText) findViewById(R.id.ClientId);

    }


    public void createProject(View view){
        addProject();

        finish();
    }

    public void addProject(){
        //TODO add call to the Web Service
    }

}

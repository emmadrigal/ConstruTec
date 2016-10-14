package construtec.mobile;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Show a screen with all the available proyects
 */
public class ViewAllProjects extends AppCompatActivity {

    private String userId;
    private String role;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_projects);

        list = (ListView) findViewById(R.id.List);

        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");

        role = intent.getStringExtra("role");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addProject);
        if(!role.equals("1")) {
            fab.setVisibility(View.GONE);
        }else{
            fab.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getProjects(userId) );

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String data = (String) list.getItemAtPosition(position);
                // create intent to start another activity
                Intent intent = new Intent(ViewAllProjects.this, ProjectInformation.class);
                // add the selected text item to our intent.
                intent.putExtra("projectName", data);
                intent.putExtra("userID", userId);
                intent.putExtra("role", role);

                startActivity(intent);
            }
        });
    }


    private List<String> getProjects(String id){
        String nameId = "Nombre";

        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\": \"Casa de Marco\"}, {\"Nombre\": \"Carretera a Cartago\"},{\"Nombre\": \"Libero Consulting\"},{\"Nombre\": \"Consequat Incorporated\"},{\"Nombre\": \"Vitae Industries\"},{\"Nombre\": \"Nulla LLP\"},{\"Nombre\": \"Ffringilla Euismod Enim Consulting\"},{\"Nombre\": \"Yut Mi Foundation\"},{\"Nombre\": \"Tempor Diam Foundation\"},{\"Nombre\": \"Ipsum Corp\"}]";

        List<String> projects = new ArrayList<String>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString(nameId);
                projects.add(nombre);
            }
        }
        catch (JSONException e) {}
        return projects;
    }

    public void createProject(View view){
        //TODO make a call to the WebService to create project, check existance before exiting
        // create intent to start another activity
        Intent intent = new Intent(ViewAllProjects.this, CreateNewProject.class);
        // add the selected text item to our intent.
        intent.putExtra("selected-item", userId);
        startActivity(intent);
    }
}

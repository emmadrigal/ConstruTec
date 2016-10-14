package construtec.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddStageToProject extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> arrayAdapter;

    private String userID;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stage_to_project);

        Intent intent = getIntent();
        projectName = intent.getStringExtra("projectName");
        userID = intent.getStringExtra("userID");

        list = (ListView) findViewById(R.id.List);
        EditText filter = (EditText) findViewById(R.id.filter);

        list.setTextFilterEnabled(true);

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getStages() );

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String data = (String) list.getItemAtPosition(position);
                addStageToProject(data);
                // create intent to start another activity
                finish();
            }
        });


        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                AddStageToProject.this.arrayAdapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void addStageToProject(String data){
        //TODO make call to web service
    }

    private String getAllStages(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        return "[{\"Nombre\":\"Escaleras\"}, {\"Nombre\":\"Cimientos\"}, {\"Nombre\":\"Ventanas\"}, {\"Nombre\":\"Muebles de Cocina\"}]";
    }

    private List<String> getStages(){
        String nameId = "Nombre";

        String json = getAllStages();

        List<String> stages = new ArrayList<String>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString(nameId);
                stages.add(nombre);
            }
        }
        catch (JSONException e) {}
        return stages;
    }

}

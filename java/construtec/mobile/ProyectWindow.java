package construtec.mobile;

import android.content.Intent;
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
public class ProyectWindow extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyect_window);

        Intent intent = getIntent();
        String id = intent.getStringExtra("UserId");

        list = (ListView) findViewById(R.id.List);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getProjects(id) );

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new ListClickHandler());
    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            String data = (String)adapter.getItemAtPosition(position);
            // create intent to start another activity
            Intent intent = new Intent(ProyectWindow.this, ViewAllStages.class);
            // add the selected text item to our intent.
            intent.putExtra("selected-item", data);
            startActivity(intent);
        }

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
}

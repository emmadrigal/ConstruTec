package construtec.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a screen
 */
public class ViewAllStages extends AppCompatActivity {
    private ListView list;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyect_window);

        title = (TextView) findViewById(R.id.toolbar_title);
        list = (ListView) findViewById(R.id.List);

        Intent intent = getIntent();
        String proyectName = intent.getStringExtra("selected-item");

        title.setText(proyectName);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getStages() );

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new ListClickHandler());
    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // create intent to start another activity
            Intent intent = new Intent(ViewAllStages.this, ViewMaterialsForStage.class);
            // add the selected text item to our intent.
            //intent.putExtra("selected-item", text);
            startActivity(intent);
        }

    }

    private List<String> getStages(){
        String nameId = "Nombre";

        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\":\"Escaleras\"}, {\"Nombre\":\"Cimientos\"}, {\"Nombre\":\"Ventanas\"}, {\"Nombre\":\"Muebles de Cocina\"}]";

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

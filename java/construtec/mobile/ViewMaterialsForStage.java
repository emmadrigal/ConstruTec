package construtec.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewMaterialsForStage extends AppCompatActivity {

    private ListView list;
    String nameId = "Nombre";
    String quantityID = "Cantidad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyect_window);

        list = (ListView) findViewById(R.id.List);

        SimpleAdapter adapter = new SimpleAdapter(this, getStages(),
                android.R.layout.simple_list_item_2,
                new String[] {nameId, quantityID},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        list.setAdapter(adapter);


        list.setOnItemClickListener(new ListClickHandler());
    }

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            // create intent to start another activity
            Intent intent = new Intent(ViewMaterialsForStage.this, ViewMaterialsForStage.class);
            // add the selected text item to our intent.
            //intent.putExtra("selected-item", text);
            startActivity(intent);
        }

    }

    private List<Map<String, String>> getStages(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\":\"Varillas\", \"Cantidad\" : 15}, {\"Nombre\":\"Cemento\", \"Cantidad\" : 4}, {\"Nombre\":\"Cerámica\", \"Cantidad\" : 200}, {\"Nombre\":\"Clavos\", \"Cantidad\" : 8000}, {\"Nombre\":\"Puertas\", \"Cantidad\" : 1}]";


        List<Map<String, String>> stages = new ArrayList<Map<String, String>>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put(nameId, jsonObject.getString(nameId));
                datum.put(quantityID, jsonObject.getString(nameId));
                stages.add(datum);
            }
        }
        catch (JSONException e) {}
        return stages;
    }

}

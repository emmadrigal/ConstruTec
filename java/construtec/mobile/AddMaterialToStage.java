package construtec.mobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Screen that allows a user to add a new material to a stage
 */
public class AddMaterialToStage extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> arrayAdapter;

    private final Map<String, String> dictionary = new HashMap<>();

    private String dividedID;

    private String selectedMaterial;

    /**
     * Initializes the parameters inside the view, called when a new screen is created
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_to_stage);

        Intent intent = getIntent();
        dividedID = intent.getStringExtra("dividedID");

        list = (ListView) findViewById(R.id.List);
        EditText filter = (EditText) findViewById(R.id.filter);

        list.setTextFilterEnabled(true);

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getMaterials() );

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedMaterial = (String) list.getItemAtPosition(position);
                show();
            }
        });

        list.setTextFilterEnabled(true);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                AddMaterialToStage.this.arrayAdapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {}

            @Override
            public void afterTextChanged(Editable arg0) {}
        });
    }


    /**
     * Calls to get a formated list of materials to be shown
     * @return list of strings indicating a list of available materials
     */
    private List<String> getMaterials(){
        String json = httpConnection.getConnection().sendGet("getAllMaterial");

        List<String> stages = new ArrayList<>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString("Name");
                dictionary.put(nombre, jsonObject.getString("Id_Material"));

                stages.add(nombre);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
        return stages;
    }

    /**
     * Call to the WebService to perform call to the webService
     * @param value quantity of the material
     */
    private void addMaterialToStage(int value){
        JSONObject json = new JSONObject();
        try {
            json.put("Id_Material", dictionary.get(selectedMaterial));
            json.put("Divided_Id", dividedID);
            json.put("Quantity", Integer.toString(value));
            httpConnection.getConnection().sendPost("Posseses", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call to show the popup window that allows to select the quantity of desired materials
     */
    private void show()
    {
        final Dialog d = new Dialog(AddMaterialToStage.this);
        d.setTitle("Choose the quantity");
        d.setContentView(R.layout.number_picker_dialog);
        Button setValue = (Button) d.findViewById(R.id.set);
        Button cancelAction = (Button) d.findViewById(R.id.cancel);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMinValue(1);
        np.setMaxValue(Integer.MAX_VALUE);
        np.setWrapSelectorWheel(false);

        setValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addMaterialToStage(np.getValue());

                d.dismiss();
                finish();
            }
        });
        cancelAction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }




}

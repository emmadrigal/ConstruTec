package construtec.mobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.List;

public class AddMaterialToStage extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> arrayAdapter;

    private String userID;
    private String projectName;
    static Dialog d ;

    private String selectedMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_to_stage);


        list = (ListView) findViewById(R.id.List);
        EditText filter = (EditText) findViewById(R.id.filter);

        list.setTextFilterEnabled(true);

        arrayAdapter = new ArrayAdapter<String>(
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
                // TODO Auto-generated method stub
                AddMaterialToStage.this.arrayAdapter.getFilter().filter(arg0);
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

    private String getAllMaterials(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        return "[{\"Nombre\":\"Varillas\"}, {\"Nombre\":\"Cemento\"}, {\"Nombre\":\"Cerámica\"}, {\"Nombre\":\"Clavos\"}]";
    }

    public List<String> getMaterials(){
        String nameId = "Nombre";

        String json = getAllMaterials();

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

    public void addMaterialToStage(String data, int value){
        //TODO add call to the web service
    }



    public void show()
    {
        final Dialog d = new Dialog(AddMaterialToStage.this);
        d.setTitle("Choose the quantity");
        d.setContentView(R.layout.dialog);
        Button setValue = (Button) d.findViewById(R.id.set);
        Button cancelAction = (Button) d.findViewById(R.id.cancel);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMinValue(0);
        np.setMaxValue(Integer.MAX_VALUE);
        np.setWrapSelectorWheel(false);

        setValue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addMaterialToStage(selectedMaterial, np.getValue());
                // create intent to start another activity
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

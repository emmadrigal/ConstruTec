package construtec.mobile;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class budget extends AppCompatActivity {
    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        listview = (ListView) findViewById(R.id.List);
        listview.setAdapter(new yourAdapter(this));
    }
}

class yourAdapter extends BaseAdapter {
    final private String stageName = "StageName";
    final private String stagePrice = "Price";

    private Context context;
    private List<Map<String, String>> data;
    private static LayoutInflater inflater = null;

    public yourAdapter(Context context) {
        data = getMaterials();
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.budget_row, parent, false);
        TextView name = (TextView) vi.findViewById(R.id.stageName);
        name.setText(data.get(position).get(stageName));

        TextView price = (TextView) vi.findViewById(R.id.price);
        price.setText(data.get(position).get(stagePrice));

        return vi;
    }

    private List<Map<String, String>> getMaterials(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Cimientos\", \"Price\": 123}, {\"StageName\": \"Escaleras\", \"Price\": 1654}, {\"StageName\": \"Muebles de Cocina\", \"Price\": 4568}, {\"StageName\": \"Pintura\", \"Price\": 50}, {\"StageName\": \"Pisos\", \"Price\": 165}, {\"StageName\": \"Cimientos\", \"Price\": 123}]";

        List<Map<String, String>> allMaterials = new ArrayList<>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String, String> material = new HashMap<>(2);
                material.put(stageName, jsonObject.getString(stageName));
                material.put(stagePrice, jsonObject.getString(stagePrice));
                allMaterials.add(material);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
        return allMaterials;
    }
}
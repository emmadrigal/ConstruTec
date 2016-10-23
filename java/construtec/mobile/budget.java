package construtec.mobile;

import android.content.Context;
import android.content.Intent;
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
    static String project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Intent intent = getIntent();

        project = intent.getStringExtra("projectID");

        ListView listview = (ListView) findViewById(R.id.List);
        listview.setAdapter(new yourAdapter(this));
    }
}

class yourAdapter extends BaseAdapter {
    final private String stageName = "StageName";
    final private String stagePrice = "Price";

    private Context context;
    private final List<Map<String, String>> data;
    private static LayoutInflater inflater = null;

    yourAdapter(Context context) {
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
        int totalCost = 0;
        String json = httpConnection.getConnection().sendGet("Presupuesto/" + budget.project);

        List<Map<String, String>> allMaterials = new ArrayList<>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String, String> material = new HashMap<>(2);
                material.put(stageName, jsonObject.getString("Name_Stage"));
                material.put(stagePrice, jsonObject.getString("costo"));
                totalCost = totalCost + Integer.parseInt(jsonObject.getString("costo"));
                allMaterials.add(material);
            }
            Map<String, String> material = new HashMap<>(2);
            material.put(stageName, "Total");
            material.put(stagePrice, Integer.toString(totalCost));
            allMaterials.add(material);
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
        return allMaterials;
    }
}
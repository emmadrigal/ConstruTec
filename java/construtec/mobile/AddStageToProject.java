package construtec.mobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Screen that show the available stages to be shwon to the project
 */
public class AddStageToProject extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> arrayAdapter;
    private final Context context = this;

    private final Map<String, String> dictionary = new HashMap<>();

    private String selectedItem;
    private String projectID;

    private static String startDate = "";
    private static String endDate = "";

    private static final List<String> stageList = new ArrayList<>();

    /**
     * Called to initialize values inside the view
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stage_to_project);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dia = cal.get(Calendar.DAY_OF_MONTH);
        int mes = cal.get(Calendar.MONTH);
        int año = cal.get(Calendar.YEAR);

        Intent intent = getIntent();
        projectID = intent.getStringExtra("projectID");

        list = (ListView) findViewById(R.id.List);
        EditText filter = (EditText) findViewById(R.id.filter);

        list.setTextFilterEnabled(true);

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stageList);

        list.setAdapter(arrayAdapter);

        final DatePickerDialog endDatePicker = new DatePickerDialog(AddStageToProject.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                endDate = selectedyear + "-"  + selectedmonth + "-" + selectedday;

                addStageToProject(startDate, endDate, projectID);

                finish();
            }
        },
                año, mes, dia);

        endDatePicker.setTitle("Choose finish date for your project");

        final DatePickerDialog startDatePicker = new DatePickerDialog(AddStageToProject.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                startDate = selectedyear + "-"  + selectedmonth + "-" + selectedday;
                endDatePicker.show();
            }

        },
                año, mes, dia);

        startDatePicker.setTitle("Choose start Date for your project");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startDatePicker.show();
                selectedItem = (String) list.getItemAtPosition(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newStage);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(context);
                d.setContentView(R.layout.new_stage_screen);
                Button setValue = (Button) d.findViewById(R.id.set);
                Button cancelAction = (Button) d.findViewById(R.id.cancel);

                final EditText stageName = (EditText) d.findViewById(R.id.stageName);
                final EditText description = (EditText) d.findViewById(R.id.description);

                setValue.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("Name", stageName.getText());
                            json.put("Description", description.getText());
                            httpConnection.getConnection().sendPost("Stage", json.toString());

                            getStages();
                            arrayAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        d.dismiss();
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
        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                AddStageToProject.this.arrayAdapter.getFilter().filter(arg0);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3){}
            @Override
            public void afterTextChanged(Editable arg0){}
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        getStages();
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * Call to the webservice to get the acailable stages to be added
     * @param startDate start date of the stage
     * @param endDate endDate of the stage
     */
    private void addStageToProject(String startDate, String endDate, String projectID){
        JSONObject json = new JSONObject();
        try {
            json.put("Id_Project", projectID);
            json.put("Stage_Id", dictionary.get(selectedItem));
            json.put("Start_Date", startDate);
            json.put("End_Date", endDate);
            json.put("Status", "false");
            httpConnection.getConnection().sendPost("Divided_in", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called to populate the stage list
     */
    private void getStages(){
        String json = httpConnection.getConnection().sendGet("getAllStage");

        stageList.clear();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String stageId;
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString("Name");
                stageId = jsonObject.getString("Stage_Id");

                dictionary.put(nombre, stageId);
                stageList.add(nombre);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
    }

}

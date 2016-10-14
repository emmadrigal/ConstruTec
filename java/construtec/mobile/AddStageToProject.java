package construtec.mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Screen that show the available stages to be shwon to the project
 */
public class AddStageToProject extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> arrayAdapter;

    private String userID;
    private String projectName;

    static String startDate = "";
    static String endDate = "";
    String data;

    /**
     * Called to initialize values inside the view
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stage_to_project);

        Date date = new Date();
        int dia = date.getDay() + 9;
        int mes = date.getMonth();
        int año = date.getYear() + 1900;

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

        final DatePickerDialog endDatePicker = new DatePickerDialog(AddStageToProject.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                endDate = selectedmonth + "-" + selectedday + "-" + selectedyear;

                addStageToProject(data, startDate, endDate, projectName);

                //TODO check if add is succesfull before proceding
                Intent intent = new Intent(getBaseContext(), StageInformation.class);
                intent.putExtra("stageName",   data);
                intent.putExtra("projectName", projectName);
                intent.putExtra("userID",      userID);
                startActivity(intent);
            }
        },
                año, mes, dia);

        endDatePicker.setTitle("Choose finish date for your project");

        final DatePickerDialog startDatePicker = new DatePickerDialog(AddStageToProject.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                startDate = selectedmonth + "-" + selectedday + "-" + selectedyear;
                endDatePicker.show();
            }

        },
                año, mes, dia);

        startDatePicker.setTitle("Choose start Date for your project");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                startDatePicker.show();
                data = (String) list.getItemAtPosition(position);
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

    /**
     * Call to the webservice to get the acailable stages to be added
     * @param stageName name of the stage to be added
     * @param startDate start date of the stage
     * @param endDate endDate of the stage
     * @param projectName name of the project that is going to get a new stage
     */
    private void addStageToProject(String stageName, String startDate, String endDate, String projectName){
        //TODO make call to web service
    }

    /**
     * Called to the database
     * @return json with the information from the database
     */
    private String getAllStages(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        return "[{\"Nombre\":\"Escaleras\"}, {\"Nombre\":\"Cimientos\"}, {\"Nombre\":\"Ventanas\"}, {\"Nombre\":\"Muebles de Cocina\"}]";
    }

    /**
     * Called to populate the stage list
     * @return list of stage names that are available to be added to the project
     */
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

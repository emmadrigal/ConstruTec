package construtec.mobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Screen used to display the information of a stage
 */
public class StageInformation extends AppCompatActivity {
    private static String currentProject;
    private static String currentUser;
    private static String currentStage;
    private static String dividedId;
    private static SimpleAdapter adapter;

    private static String startDate;
    private static String endDate;
    private static String status;

    final private static String nameId = "Nombre";
    final private static String quantityID = "Cantidad";

    private static String selectedMaterial;
    private static List<String> possesesID = new ArrayList<>();
    private static List<Map<String, String>> materialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_information);

        Intent intent = getIntent();
        currentProject = intent.getStringExtra("projectName");
        currentUser = intent.getStringExtra("currentUser");
        currentStage = intent.getStringExtra("stageName");
        dividedId = intent.getStringExtra("dividedId");

        adapter = new SimpleAdapter(this, materialList,
                android.R.layout.simple_list_item_2,
                new String[] {nameId, quantityID},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        String role = intent.getStringExtra("role");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addMaterial);
        if(!role.equals("1")) {
            fab.setVisibility(View.GONE);
        }else{
            fab.setVisibility(View.VISIBLE);
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();

        getMaterials();
        adapter.notifyDataSetChanged();
    }

    /**
     * Makes a call to the database that gives the current stage information
     */
    static private void setStageInfo(){
        String json = httpConnection.getConnection().sendGet("Divided_in/" + dividedId);

        JSONObject obj;
        try {
            obj = new JSONObject(json);
            startDate = obj.getString("Start_Date");
            endDate = obj.getString("End_Date");
            status = obj.getString("Status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * A view containing a list of all the stages for the selected project
     */
    public static class materialsView extends Fragment {
        private ListView list;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public materialsView() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static materialsView newInstance(int sectionNumber) {
            materialsView fragment = new materialsView();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Called on the start of thsi activity
         * @param inflater required by Android
         * @param container required by Android
         * @param savedInstanceState required by Android
         * @return the view representing this screen
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.pager_view_list, container, false);

            list = (ListView) rootView.findViewById(R.id.List);

            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(position);
                    selectedMaterial = possesesID.get(position);
                    String initialValue = (String) obj.get(quantityID);

                    show(Integer.parseInt(initialValue));
                }
            });

            return rootView;
        }

        public void show(int initialValue)
        {
            final Dialog d = new Dialog(getContext());
            d.setContentView(R.layout.update_material);
            Button setValue = (Button) d.findViewById(R.id.set);
            Button delete   = (Button) d.findViewById(R.id.delete);
            Button cancelAction = (Button) d.findViewById(R.id.cancel);

            final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
            np.setMinValue(0);
            np.setMaxValue(Integer.MAX_VALUE);
            np.setValue(initialValue);
            np.setWrapSelectorWheel(false);

            delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    deleteMaterialStage();
                    // create intent to start another activity
                    d.dismiss();
                }
            });

            setValue.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    updateMaterialStage(np.getValue());
                    // create intent to start another activity
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
    }

    private static void updateMaterialStage(int newValue){
        httpConnection.getConnection().sendPut("Posseses", selectedMaterial, "Quantity", Integer.toString(newValue));

        getMaterials();
        adapter.notifyDataSetChanged();
    }

    private static void deleteMaterialStage(){
        httpConnection.getConnection().sendDelete("Posseses", selectedMaterial);

        getMaterials();
        adapter.notifyDataSetChanged();
    }


    /**
     * A view containing a list of all the stages for the selected project
     */
    public static class stageDetails extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public stageDetails() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static stageDetails newInstance(int sectionNumber) {
            stageDetails fragment = new stageDetails();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Called whenever a new Stage details screen is created
         * @param inflater required by Android
         * @param container required by Android
         * @param savedInstanceState required by Android
         * @return view to be shown
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setStageInfo();


            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            final int dia = cal.get(Calendar.DAY_OF_MONTH);
            final int mes = cal.get(Calendar.MONTH);
            final int año = cal.get(Calendar.YEAR);

            View rootView = inflater.inflate(R.layout.stage_project_details, container, false);
            TextView name  = (TextView) rootView.findViewById(R.id.projectName);
            TextView stage = (TextView) rootView.findViewById(R.id.stageName);
            final TextView startDate = (TextView) rootView.findViewById(R.id.startDate);
            final TextView endDate = (TextView) rootView.findViewById(R.id.endDate);

            Button pay = (Button) rootView.findViewById(R.id.pay);

            if(status.equals("false")){
                pay.setVisibility(View.VISIBLE);
            }
            else{
                pay.setVisibility(View.GONE);
            }


            //Controls the click for the date
            View.OnClickListener startDateChooser = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            selectedmonth = selectedmonth + 1;
                            String newDate = Integer.toString(selectedyear) +"-" +   Integer.toString(selectedmonth) +"-" + Integer.toString(selectedday);
                            httpConnection.getConnection().sendPut("Divided_in", dividedId, "Start_Date", newDate);
                            startDate.setText(newDate);
                        }

                    },
                            año, mes, dia);
                    mDatePicker.show();  }
            };

            View.OnClickListener endDateChooser = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            selectedmonth = selectedmonth + 1;
                            String newDate = Integer.toString(selectedyear) +"-" +   Integer.toString(selectedmonth) +"-" + Integer.toString(selectedday);
                            httpConnection.getConnection().sendPut("Divided_in", dividedId, "End_Date", newDate);
                            endDate.setText(newDate);
                        }

                    },
                            año, mes, dia);
                    mDatePicker.show();  }
            };

            name.setText(currentProject);
            stage.setText(currentStage);
            startDate.setText(StageInformation.startDate);
            startDate.setOnClickListener(startDateChooser);
            endDate.setText(StageInformation.endDate);
            endDate.setOnClickListener(endDateChooser);

            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 1){
                return materialsView.newInstance(position + 1);
            }
            else{
                return stageDetails.newInstance(position+1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Stage Details";
                case 1:
                    return "Materials";
            }
            return null;
        }
    }

    /**
     * Retrieves the materials associated with this screen, since it is both material and quantity it has to allow for both to be desplayed
     * @return list of materials associated with this stage
     */
    private static void getMaterials(){
        String json = httpConnection.getConnection().sendGet("Posseses/Divided_in/" + dividedId);
        materialList.clear();
        possesesID.clear();

        String idPosseses;
        String nombre;
        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String, String> material = new HashMap<>(2);
                material.put(quantityID, jsonObject.getString("Quantity"));

                //Makes a call to the Material table to look at the name
                idPosseses = jsonObject.getString("Posseses_Id");
                String stageJson = httpConnection.getConnection().sendGet("Material/" + jsonObject.getString("Id_Material"));
                JSONObject obj = new JSONObject(stageJson);
                nombre = obj.getString("Name");

                material.put(nameId, nombre);

                possesesID.add(idPosseses);
                materialList.add(material);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
    }

    /**
     * Calls the view that allows the user to add a new material to the screen
     * @param view to be shown
     */
    public void addMaterial(View view){
        // create intent to start another activity
        Intent intent = new Intent(StageInformation.this, AddMaterialToStage.class);
        // add the selected text item to our intent.
        intent.putExtra("dividedID", dividedId);
        startActivity(intent);
    }

    /**
     * Called whenever the user wishes to delete an stage
     * @param view to be shown
     */
    public void delete(View view){
        httpConnection.getConnection().sendDelete("Divided_in", dividedId);
        //TODO ask for confirmation before paying
        finish();
    }

    /**
     * Called whenever the user wishes to pay for the material of a stage
     * @param view to be shown
     */
    public void pay(View view){
        httpConnection.getConnection().sendGet("payDivided/" + dividedId);
    }
}

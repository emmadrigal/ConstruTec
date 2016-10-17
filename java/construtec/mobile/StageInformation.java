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

/**
 * Screen used to display the information of a stage
 */
public class StageInformation extends AppCompatActivity {
    private static String currentProject;
    private static String currentUser;
    private static String currentStage;
    private static SimpleAdapter adapter;

    private static String startDate;
    private static String endDate;

    final private static String nameId = "Nombre";
    final private static String quantityID = "Cantidad";

    private static String selectedMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_information);

        Intent intent = getIntent();
        currentProject = intent.getStringExtra("projectName");
        currentUser = intent.getStringExtra("currentUser");
        currentStage = intent.getStringExtra("stageName");

        adapter = new SimpleAdapter(this, getMaterials(),
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

        //TODO disable pay button when stage is already payed

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    //TODO on return to this screen data should be refreshed

    /**
     * Makes a call to the database that gives the current stage information
     */
    static private void setStageInfo(){
        //TODO make call to the webService to retrieve project Data

        startDate = "31/oct/2016";
        endDate = "1/nov/2016";
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
                    selectedMaterial = (String) obj.get(nameId);
                    String initialValue = (String) obj.get(quantityID);

                    Log.d("selectedMaterial", selectedMaterial);
                    Log.d("initialValue", initialValue);

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
                    deleteMaterialStage(selectedMaterial);
                    // create intent to start another activity
                    d.dismiss();
                }
            });

            setValue.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    updateMaterialStage(selectedMaterial, np.getValue());
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

    private static void updateMaterialStage(String material, int newValue){
        //TODO call the database to update the value of the material
    }

    private static void deleteMaterialStage(String material){
        //TODO call the database to delete the material from this stage
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



            //Controls the click for the date
            View.OnClickListener startDateChooser = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            String newDate = "";
                            switch(selectedmonth){
                                case 0 : newDate = Integer.toString(selectedday) +  "/jan/" + Integer.toString(selectedyear);
                                    break;
                                case 1 : newDate = Integer.toString(selectedday) +  "/feb/" + Integer.toString(selectedyear);
                                    break;
                                case 2 : newDate = Integer.toString(selectedday) +  "/mar/" + Integer.toString(selectedyear);
                                    break;
                                case 3 : newDate = Integer.toString(selectedday) +  "/apr/" + Integer.toString(selectedyear);
                                    break;
                                case 4 : newDate = Integer.toString(selectedday) +  "/may/" + Integer.toString(selectedyear);
                                    break;
                                case 5 : newDate = Integer.toString(selectedday) +  "/jun/" + Integer.toString(selectedyear);
                                    break;
                                case 6 : newDate = Integer.toString(selectedday) +  "/jul/" + Integer.toString(selectedyear);
                                    break;
                                case 7 : newDate = Integer.toString(selectedday) +  "/aug/" + Integer.toString(selectedyear);
                                    break;
                                case 8 : newDate = Integer.toString(selectedday) +  "/sep/" + Integer.toString(selectedyear);
                                    break;
                                case 9 : newDate = Integer.toString(selectedday) +  "/oct/" + Integer.toString(selectedyear);
                                    break;
                                case 10 : newDate = Integer.toString(selectedday) +  "/nov/" + Integer.toString(selectedyear);
                                    break;
                                case 11 : newDate = Integer.toString(selectedday) +  "/dec/" + Integer.toString(selectedyear);
                                    break;
                            }
                            //TODO make call to the database to perform update
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
                            String newDate = "";
                            switch(selectedmonth){
                                case 0 : newDate = Integer.toString(selectedday) +  "/jan/" + Integer.toString(selectedyear);
                                    break;
                                case 1 : newDate = Integer.toString(selectedday) +  "/feb/" + Integer.toString(selectedyear);
                                    break;
                                case 2 : newDate = Integer.toString(selectedday) +  "/mar/" + Integer.toString(selectedyear);
                                    break;
                                case 3 : newDate = Integer.toString(selectedday) +  "/apr/" + Integer.toString(selectedyear);
                                    break;
                                case 4 : newDate = Integer.toString(selectedday) +  "/may/" + Integer.toString(selectedyear);
                                    break;
                                case 5 : newDate = Integer.toString(selectedday) +  "/jun/" + Integer.toString(selectedyear);
                                    break;
                                case 6 : newDate = Integer.toString(selectedday) +  "/jul/" + Integer.toString(selectedyear);
                                    break;
                                case 7 : newDate = Integer.toString(selectedday) +  "/aug/" + Integer.toString(selectedyear);
                                    break;
                                case 8 : newDate = Integer.toString(selectedday) +  "/sep/" + Integer.toString(selectedyear);
                                    break;
                                case 9 : newDate = Integer.toString(selectedday) +  "/oct/" + Integer.toString(selectedyear);
                                    break;
                                case 10 : newDate = Integer.toString(selectedday) +  "/nov/" + Integer.toString(selectedyear);
                                    break;
                                case 11 : newDate = Integer.toString(selectedday) +  "/dec/" + Integer.toString(selectedyear);
                                    break;
                            }
                            //TODO make call to the database to perform update
                            endDate.setText(newDate);
                        }

                    },
                            año, mes, dia);
                    mDatePicker.show();  }
            };

            setStageInfo();
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
    private List<Map<String, String>> getMaterials(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\":\"Varillas\", \"Cantidad\" : 15}, {\"Nombre\":\"Cemento\", \"Cantidad\" : 4}, {\"Nombre\":\"Cerámica\", \"Cantidad\" : 200}, {\"Nombre\":\"Clavos\", \"Cantidad\" : 8000}, {\"Nombre\":\"Puertas\", \"Cantidad\" : 1}]";

        List<Map<String, String>> allMaterials = new ArrayList<>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String, String> material = new HashMap<>(2);
                material.put(nameId, jsonObject.getString(nameId));
                material.put(quantityID, jsonObject.getString(quantityID));
                allMaterials.add(material);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
        return allMaterials;
    }

    /**
     * Calls the view that allows the user to add a new material to the screen
     * @param view to be shown
     */
    public void addMaterial(View view){
        // create intent to start another activity
        Intent intent = new Intent(StageInformation.this, AddMaterialToStage.class);
        // add the selected text item to our intent.
        intent.putExtra("userID", currentUser);
        intent.putExtra("projectName", currentProject);
        startActivity(intent);
    }

    /**
     * Called whenever the user wishes to delete an stage
     * @param view to be shown
     */
    public void delete(View view){
        //TODO make call to the web service to delete the current stage
        //TODO ask for confirmation before deleting
        finish();
    }

    /**
     * Called whenever the user wishes to pay for the material of a stage
     * @param view to be shown
     */
    public void pay(View view){
        //TODO make call to the web service to delete the current stage
        //TODO ask for confirmation before deleting
        finish();
    }
}

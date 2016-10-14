package construtec.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StageInformation extends AppCompatActivity {
    public static String currentProject;
    public static String currentUser;
    public static SimpleAdapter adapter;

    static String startDate;
    static String endDate;

    String nameId = "Nombre";
    String quantityID = "Cantidad";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stage_information);

        TextView title = (TextView) findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        currentProject = intent.getStringExtra("projectName");
        currentUser = intent.getStringExtra("currentUser");

        adapter = new SimpleAdapter(this, getMaterials(),
                android.R.layout.simple_list_item_2,
                new String[] {nameId, quantityID},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        title.setText(currentProject);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    static public void setStageInfo(){
        //TODO make call to the webService to retrieve project Data

        startDate = "31/oct/2016";
        endDate = "1/nov/2016";
    }

    /**
     *
     */
    @Override
    public void onResume(){
        super.onResume();
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.pager_view_list, container, false);

            list = (ListView) rootView.findViewById(R.id.List);

            list.setAdapter(adapter);

            return rootView;
        }
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.stage_project_information, container, false);
            TextView name  = (TextView) rootView.findViewById(R.id.projectName);
            TextView stage = (TextView) rootView.findViewById(R.id.stageName);
            TextView startDate = (TextView) rootView.findViewById(R.id.startDate);
            TextView endDate = (TextView) rootView.findViewById(R.id.endDate);

            setStageInfo();
            name.setText(currentProject);
            stage.setText(currentUser);
            startDate.setText(StageInformation.startDate);
            endDate.setText(StageInformation.endDate);

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
                    return "Project Details";
                case 1:
                    return "Stages";
            }
            return null;
        }
    }


    private List<Map<String, String>> getMaterials(){
        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\":\"Varillas\", \"Cantidad\" : 15}, {\"Nombre\":\"Cemento\", \"Cantidad\" : 4}, {\"Nombre\":\"Cerámica\", \"Cantidad\" : 200}, {\"Nombre\":\"Clavos\", \"Cantidad\" : 8000}, {\"Nombre\":\"Puertas\", \"Cantidad\" : 1}]";


        List<Map<String, String>> allMaterials = new ArrayList<Map<String, String>>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Map<String, String> material = new HashMap<String, String>(2);
                material.put(nameId, jsonObject.getString(nameId));
                material.put(quantityID, jsonObject.getString(quantityID));
                allMaterials.add(material);
            }
        }
        catch (JSONException e) {}
        return allMaterials;
    }

    public void addMaterial(View view){
        // create intent to start another activity
        Intent intent = new Intent(StageInformation.this, AddMaterialToStage.class);
        // add the selected text item to our intent.
        intent.putExtra("userID", currentUser);
        intent.putExtra("projectName", currentProject);
        startActivity(intent);
    }
}

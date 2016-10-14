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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows a screen
 */
public class ProjectInformation extends AppCompatActivity {
    public static String currentProject;
    public static String currentUser;
    public static ArrayAdapter<String> arrayAdapter;

    static String clientId;
    static String engineerId;
    static String Location;

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
        setContentView(R.layout.project_information);

        TextView title = (TextView) findViewById(R.id.toolbar_title);

        Intent intent = getIntent();
        currentProject = intent.getStringExtra("projectName");
        currentUser = intent.getStringExtra("currentUser");

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getStages(currentProject) );

        title.setText(currentProject);

        mSectionsPagerAdapter = new ProjectInformation.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    static public void setProjectInfo(){
        //TODO make call to the webService to retrieve project Data

        clientId = "304960478";
        engineerId = "1793566";
        Location = "Ubicado en el Tecnológico de Costa Rica, Sede San Carlos, 18 kilómetros al norte de Ciudad Quesada, carretera a Fortuna, Santa Clara, San Carlos";
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
    public static class StagesView extends Fragment {
        private ListView list;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public StagesView() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static StagesView newInstance(int sectionNumber) {
            StagesView fragment = new StagesView();
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

            list.setAdapter(arrayAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String data = (String) list.getItemAtPosition(position);
                    // create intent to start another activity
                    Intent intent = new Intent(view.getContext(), StageInformation.class);
                    // add the selected text item to our intent.
                    intent.putExtra("projectName", currentProject);
                    intent.putExtra("currentUser", currentUser);
                    startActivity(intent);
                }
            });


            return rootView;
        }
    }

    /**
     * A view containing a list of all the stages for the selected project
     */
    public static class projectDetails extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public projectDetails() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static projectDetails newInstance(int sectionNumber) {
            projectDetails fragment = new projectDetails();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.project_details, container, false);
            TextView name  = (TextView) rootView.findViewById(R.id.projectName);
            TextView client = (TextView) rootView.findViewById(R.id.clientId);
            TextView engineer = (TextView) rootView.findViewById(R.id.engineerId);
            TextView location = (TextView) rootView.findViewById(R.id.location);

            setProjectInfo();
            name.setText(currentProject);
            client.setText(clientId);
            engineer.setText(engineerId);
            location.setText(Location);

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
                return StagesView.newInstance(position + 1);
            }
            else{
                return projectDetails.newInstance(position+1);
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


    private List<String> getStages(String Project){
        String nameId = "Nombre";

        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\":\"Escaleras\"}, {\"Nombre\":\"Cimientos\"}, {\"Nombre\":\"Ventanas\"}, {\"Nombre\":\"Muebles de Cocina\"}]";

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

    public void addStage(View view){
        // create intent to start another activity
        Intent intent = new Intent(view.getContext(), AddStageToProject.class);
        // add the selected text item to our intent.
        intent.putExtra("userID", currentUser);
        intent.putExtra("projectName", currentProject);
        startActivity(intent);
    }
}

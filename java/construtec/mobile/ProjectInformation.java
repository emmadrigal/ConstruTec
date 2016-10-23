package construtec.mobile;

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
import android.widget.EditText;
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

/**
 * Shows a screen
 */
public class ProjectInformation extends AppCompatActivity {
    private static String ProjectId;
    private static String currentProject;
    private static String currentUser;
    private static String role;
    private static SimpleAdapter arrayAdapter;

    private static String clientId;
    private static String engineerId;
    private static String Location;

    private static String newValue;

    private static final List<String> listOfDivided = new ArrayList<>();

    private static final List<Map<String, String>> stageList = new ArrayList<>();

    /**
     * Populates the screen
     * @param savedInstanceState required by android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_project_information);

        Intent intent = getIntent();
        currentProject = intent.getStringExtra("projectName");
        currentUser = intent.getStringExtra("currentUser");
        role = intent.getStringExtra("role");
        ProjectId = intent.getStringExtra("projectId");

        arrayAdapter = new SimpleAdapter(this, stageList,
                android.R.layout.simple_list_item_2,
                new String[] {"Name", "Description"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        String role = intent.getStringExtra("role");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addStage);
        if(!role.equals("1")){
            fab.setVisibility(View.GONE);
        }else{
            fab.setVisibility(View.VISIBLE);
        }

        SectionsPagerAdapter mSectionsPagerAdapter = new ProjectInformation.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Updates this users list
        getStages();
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * Call to the Webservice to get project Information
     */
    static private void setProjectInfo(){
        String json = httpConnection.getConnection().sendGet("Project/" + ProjectId);

        try {
            JSONObject obj = new JSONObject(json);

            clientId = obj.getString("Id_Client");
            engineerId = obj.getString("Id_Enginner");
            Location = obj.getString("Location");

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    Map<String, String> data = (Map<String, String>) list.getItemAtPosition(position);
                    // create intent to start another activity
                    Intent intent = new Intent(view.getContext(), StageInformation.class);
                    // add the selected text item to our intent.
                    intent.putExtra("dividedId", listOfDivided.get((int) id));
                    intent.putExtra("projectName", currentProject);
                    intent.putExtra("currentUser", currentUser);
                    intent.putExtra("stageName", data.get("Name"));
                    intent.putExtra("role", role);
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
            final TextView name  = (TextView) rootView.findViewById(R.id.projectName);
            TextView client = (TextView) rootView.findViewById(R.id.clientId);
            TextView engineer = (TextView) rootView.findViewById(R.id.engineerId);
            final TextView location = (TextView) rootView.findViewById(R.id.location);

            View.OnClickListener updateLocation = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = new Dialog(getContext());
                    d.setContentView(R.layout.text_popup);
                    Button setValue = (Button) d.findViewById(R.id.set);
                    Button cancelAction = (Button) d.findViewById(R.id.cancel);

                    final EditText np = (EditText) d.findViewById(R.id.newValue);

                    setValue.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            newValue = np.getText().toString();
                            location.setText(newValue);
                            httpConnection.getConnection().sendPut("Project", currentProject, "Location", newValue);
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
            };

            View.OnClickListener updateName = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = new Dialog(getContext());
                    d.setContentView(R.layout.text_popup);
                    Button setValue = (Button) d.findViewById(R.id.set);
                    Button cancelAction = (Button) d.findViewById(R.id.cancel);

                    final EditText np = (EditText) d.findViewById(R.id.newValue);

                    setValue.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            newValue = np.getText().toString();
                            name.setText(newValue);
                            httpConnection.getConnection().sendPut("Project", currentProject, "Name", newValue);
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
            };

            setProjectInfo();
            name.setText(currentProject);
            name.setOnClickListener(updateName);
            client.setText(clientId);
            engineer.setText(engineerId);
            location.setText(Location);
            location.setOnClickListener(updateLocation);

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

    /**
     * Gets a list of the current stages associated to this projct
     */
    private void getStages(){
        String json = httpConnection.getConnection().sendGet("Divided_in/Project/" + ProjectId);

        listOfDivided.clear();
        stageList.clear();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray listOfStages = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String idStage;
            String idDivided;
            for(int i = 0; i < listOfStages.length(); i++){
                JSONObject jsonObject = listOfStages.getJSONObject(i);

                //Retrieves both values
                idDivided = jsonObject.getString("Divided_Id");
                idStage = jsonObject.getString("Stage_Id");

                //Makes a call to the Stage table to look at the name
                String stageJson = httpConnection.getConnection().sendGet("Stage/" + idStage);
                JSONObject obj = new JSONObject(stageJson);

                Map<String, String> stage = new HashMap<>(2);
                stage.put("Name", obj.getString("Name"));
                stage.put("Description", obj.getString("Description"));

                listOfDivided.add(idDivided);
                stageList.add(stage);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
    }

    /**
     * Called to associate a new pro
     * @param view that called this method
     */
    public void addStage(View view){
        // create intent to start another activity
        Intent intent = new Intent(view.getContext(), AddStageToProject.class);
        // add the selected text item to our intent.
        intent.putExtra("projectID", ProjectId);

        startActivity(intent);
    }

    /**
     * Called whenever a user wishes to delete the current project
     * @param view that called this method
     */
    public void delete(View view){
        httpConnection.getConnection().sendDelete("Project", ProjectId);
        //TODO confirmation before deleting
        finish();
    }

    /**
     * Calls a new screen showing the budget for the current project
     * @param view that called this method
     */
    public void budget(View view){
        Intent intent = new Intent(ProjectInformation.this, budget.class);
        intent.putExtra("projectID", ProjectId);

        startActivity(intent);
    }
}

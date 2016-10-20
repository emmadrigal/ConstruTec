package construtec.mobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.text.InputType.TYPE_CLASS_NUMBER;

/**
 * Show a screen with all the available proyects
 */
public class UserInformation extends AppCompatActivity {
    private static String userId;
    private static String userName;
    private static String role;

    private static String codigo;
    private static String phone;

    private static List<String> projectList = new ArrayList<>();

    private static ArrayAdapter myProjectAdapter;
    private static ArrayAdapter projectsAdapter;

    /**
     * Called when a new screen is created, it intializes the values
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        Intent intent = getIntent();
        userId = intent.getStringExtra("UserId");
        role = intent.getStringExtra("role");
        userName = intent.getStringExtra("userName");
        codigo = intent.getStringExtra("code");
        phone = intent.getStringExtra("phone");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addProject);
        if(!role.equals("1")) {
            fab.setVisibility(View.GONE);
        }else{
            fab.setVisibility(View.VISIBLE);
        }

        myProjectAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                getProjects(userId) );
        getNextProjects();
        projectsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                projectList);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    //TODO on return to this screen data should be refreshed

    /**
     * A view containing a list of all the projects for the logged in user
     */
    public static class projectsList extends Fragment {
        private ListView list;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public projectsList() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static projectsList newInstance(int sectionNumber) {
            projectsList fragment = new projectsList();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * If a view of the stage list is created this is called
         * @param inflater required by Android
         * @param container required by Android
         * @param savedInstanceState required by Android
         * @return View to be displayed
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.pager_view_list, container, false);

            list = (ListView) rootView.findViewById(R.id.List);

            list.setAdapter(myProjectAdapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String data = (String) list.getItemAtPosition(position);

                    Intent intent = new Intent(view.getContext(), ProjectInformation.class);

                    intent.putExtra("projectName", data);
                    intent.putExtra("userID", userId);
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
    public static class userInformation extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public userInformation() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static userInformation newInstance(int sectionNumber) {
            userInformation fragment = new userInformation();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Called when a new user details needs to be shown
         * @param inflater required by Android
         * @param container required by Android
         * @param savedInstanceState required by Android
         * @return View to be displayed
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.user_details, container, false);
            //This need to final in order to be changed from the OnClick Listeners
            final TextView name  = (TextView) rootView.findViewById(R.id.userName);
            final TextView id = (TextView) rootView.findViewById(R.id.userId);
            final TextView phoneNumber = (TextView) rootView.findViewById(R.id.phoneNumber);
            final TextView rol = (TextView) rootView.findViewById(R.id.role);
            final TextView code = (TextView) rootView.findViewById(R.id.code);

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
                            String newValue = np.getText().toString();
                            httpConnection.getConnection().sendPut("Usuario", userId, "Name", newValue);
                            name.setText(newValue);

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

            View.OnClickListener updatePhone = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = new Dialog(getContext());
                    d.setContentView(R.layout.text_popup);
                    Button setValue = (Button) d.findViewById(R.id.set);
                    Button cancelAction = (Button) d.findViewById(R.id.cancel);

                    final EditText np = (EditText) d.findViewById(R.id.newValue);
                    np.setInputType(TYPE_CLASS_NUMBER);

                    setValue.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            String newValue = np.getText().toString();
                            phoneNumber.setText(newValue);
                            //TODO make a call to the database to update the value
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

            View.OnClickListener updateCode = new View.OnClickListener() {
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
                            String newValue = np.getText().toString();
                            code.setText(newValue);
                            //TODO make a call to the database to update the value
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

            getUserInfo();
            name.setText(userName);
            name.setOnClickListener(updateName);
            id.setText(userId);
            phoneNumber.setText(phone);
            phoneNumber.setOnClickListener(updatePhone);
            code.setText(codigo);
            code.setOnClickListener(updateCode);

            if(!role.equals("1")){
                code.setVisibility(View.GONE);
                TextView codeTag = (TextView) rootView.findViewById(R.id.codeTag);
                codeTag.setVisibility(View.GONE);
                if(role.equals("0")){
                    rol.setText("Client");
                }else if(role.equals("1")){
                    rol.setText("User");
                }
            }else{
                rol.setText("Engineer");
            }

            return rootView;
        }
    }

    public static class projectInspection extends Fragment{
        private ListView list;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public projectInspection() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static projectInspection newInstance(int sectionNumber) {
            projectInspection fragment = new projectInspection();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * If a view of the stage list is created this is called
         * @param inflater required by Android
         * @param container required by Android
         * @param savedInstanceState required by Android
         * @return View to be displayed
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_next_projects, container, false);

            list = (ListView) rootView.findViewById(R.id.List);
            list.setAdapter(projectsAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String data = (String) list.getItemAtPosition(position);
                    //TODO change to project view list
                }
            });

            final EditText material = (EditText) rootView.findViewById(R.id.material);

            Button boton = (Button) rootView.findViewById(R.id.search);
            boton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(material.getText().toString().equals("")){
                        getNextProjects();
                    }else{
                        getNextProjectsByMaterial("");
                        //TODO change instead of choosing material, give list of options
                    }
                    projectsAdapter.notifyDataSetChanged();
                }
            });

            return rootView;
        }
    }



    private static void getUserInfo(){
        //TODO make call to the database
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
                return projectsList.newInstance(position + 1);
            }else if(position == 2){
                return projectInspection.newInstance(position + 1);
            }
            else{
                return userInformation.newInstance(position+1);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "User Information";
                case 1:
                    return "Projects";
                case 2:
                    return "Tracking construction";
            }
            return null;
        }
    }

    /**
     * Returns a list of strings that indicate the stages currently associated with this project
     * @param id of the user asking for its projects
     * @return List of strings containing the list of all current names
     */
    private List<String> getProjects(String id){
        String nameId = "Nombre";

        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\": \"Casa de Marco\"}, {\"Nombre\": \"Carretera a Cartago\"},{\"Nombre\": \"Libero Consulting\"},{\"Nombre\": \"Consequat Incorporated\"},{\"Nombre\": \"Vitae Industries\"},{\"Nombre\": \"Nulla LLP\"},{\"Nombre\": \"Ffringilla Euismod Enim Consulting\"},{\"Nombre\": \"Yut Mi Foundation\"},{\"Nombre\": \"Tempor Diam Foundation\"},{\"Nombre\": \"Ipsum Corp\"}]";

        List<String> projects = new ArrayList<>();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString(nameId);
                projects.add(nombre);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");}
        return projects;
    }

    /**
     *
     */
    private static void getNextProjects(){
        String nameId = "Nombre";

        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\": \"Casa de Marco\"}, {\"Nombre\": \"Carretera a Cartago\"},{\"Nombre\": \"Libero Consulting\"},{\"Nombre\": \"Consequat Incorporated\"},{\"Nombre\": \"Vitae Industries\"},{\"Nombre\": \"Nulla LLP\"},{\"Nombre\": \"Ffringilla Euismod Enim Consulting\"},{\"Nombre\": \"Yut Mi Foundation\"},{\"Nombre\": \"Tempor Diam Foundation\"},{\"Nombre\": \"Ipsum Corp\"}]";

        projectList.clear();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString(nameId);
                projectList.add(nombre);
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
    }

    /**
     *
     * @param materialName of the next projects
     */
    private static void getNextProjectsByMaterial(String materialName) {
        String nameId = "Nombre";

        //TODO: realizar la llamada a la base de datos para obtener esta informacion
        String json = "[{\"Nombre\": \"Consequat Incorporated\"},{\"Nombre\": \"Vitae Industries\"},{\"Nombre\": \"Nulla LLP\"},{\"Nombre\": \"Ffringilla Euismod Enim Consulting\"},{\"Nombre\": \"Yut Mi Foundation\"}]";

        projectList.clear();

        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String nombre;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString(nameId);
                projectList.add(nombre);
            }
        } catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
    }

    /**
     * Called whenever a new project is needed to be created
     * @param view that makes the call
     */
    public void createProject(View view){
        Intent intent = new Intent(UserInformation.this, CreateNewProject.class);

        intent.putExtra("userID", userId);
        startActivity(intent);
    }

    /**
     * Called when an user wishes to delete his account
     * @param view that makes the call
     */
    public void delete(View view){
        httpConnection.getConnection().sendDelete("Usuario", userId);
        finish();
    }
}

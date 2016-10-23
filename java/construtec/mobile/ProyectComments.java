package construtec.mobile;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProyectComments extends AppCompatActivity {
    private static String projectId;
    private static String projectName;
    private static String userName;
    private static String phone;

    private static List<String> Comments = new ArrayList<>();
    private static ArrayAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyect_comments);

        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectID");

        commentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Comments);
        getComments();

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A view containing a list of all the projects for the logged in user
     */
    public static class commentList extends Fragment {
        private ListView list;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public commentList() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProyectComments.commentList newInstance(int sectionNumber) {
            ProyectComments.commentList fragment = new ProyectComments.commentList();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * If a view of the project list is created this is called
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
            list.setAdapter(commentAdapter);

            return rootView;
        }
    }

    /**
     * A view containing a list of all the stages for the selected project
     */
    public static class projectInformation extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public projectInformation() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ProyectComments.projectInformation newInstance(int sectionNumber) {
            ProyectComments.projectInformation fragment = new ProyectComments.projectInformation();
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
            View rootView = inflater.inflate(R.layout.project_comment, container, false);

            String json = httpConnection.getConnection().sendGet("Project/" + projectId);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);

                TextView name = (TextView) rootView.findViewById(R.id.name);
                name.setText(jsonObject.getString("Name"));

                String cliendID = jsonObject.getString("Id_Client");
                String engineerID = jsonObject.getString("Id_Enginner");

                json = httpConnection.getConnection().sendGet("Usuario/" + cliendID);
                jsonObject = new JSONObject(json);
                TextView client = (TextView) rootView.findViewById(R.id.client);
                TextView clientNumber = (TextView) rootView.findViewById(R.id.clientNumber);
                client.setText(jsonObject.getString("Name"));
                clientNumber.setText(jsonObject.getString("Phone_Number"));

                json = httpConnection.getConnection().sendGet("Usuario/" + engineerID);
                jsonObject = new JSONObject(json);
                TextView engineer = (TextView) rootView.findViewById(R.id.engineer);
                TextView engineerName = (TextView) rootView.findViewById(R.id.engineerNumber);
                engineer.setText(jsonObject.getString("Name"));
                engineerName.setText(jsonObject.getString("Phone_Number"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
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
            if(position == 0){
                return ProyectComments.projectInformation.newInstance(position + 1);
            }else{
                return ProyectComments.commentList.newInstance(position+1);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Proyect Information";
                case 1:
                    return "Comments";
            }
            return null;
        }
    }

    public void getComments(){
        Comments.clear();

        String json = httpConnection.getConnection().sendGet("Commentary/Proyecto/" + projectId);
        try {
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray listOfComments = new JSONArray(json);

            //Iterate the jsonArray and print the info of JSONObjects
            String comment;
            for(int i=0; i < listOfComments.length(); i++){
                JSONObject jsonObject = listOfComments.getJSONObject(i);
                comment = jsonObject.getString("Comentary");
                Comments.add(comment);
                Log.i("testComment", Comments.get(i));
            }
        }
        catch (JSONException e) {
            Log.d("error", "incorrect json recieved");
        }
        commentAdapter.notifyDataSetChanged();
    }


    public void addComment(View view){
        final Dialog d = new Dialog(ProyectComments.this);
        d.setContentView(R.layout.create_comment);
        Button create = (Button) d.findViewById(R.id.create);
        Button cancelAction = (Button) d.findViewById(R.id.cancel);

        final EditText comentario = (EditText) d.findViewById(R.id.newValue);

        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                try {
                    json.put("Id_Project", projectId);
                    json.put("Comentary", comentario.getText());
                    httpConnection.getConnection().sendPost("Commentary", json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                d.dismiss();
                getComments();
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

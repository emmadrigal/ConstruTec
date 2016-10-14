package construtec.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateNewStage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_stage);
    }


    public void createStage(View view){
        //TODO make a call to the web service

        finish();
    }
}

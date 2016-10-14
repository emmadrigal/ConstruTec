package construtec.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Creates a new stage
 */
public class CreateNewStage extends AppCompatActivity {

    /**
     * Populates the screen
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_stage);
    }

    /**
     * Creates a new stage that can be used
     * @param view that called this method
     */
    public void createStage(View view){
        //TODO make a call to the web service

        finish();
    }
}

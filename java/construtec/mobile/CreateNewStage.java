package construtec.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

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
        EditText name = (EditText) findViewById(R.id.stageName);
        EditText location = (EditText) findViewById(R.id.description);

        JSONObject json = new JSONObject();
        try {
            json.put("Name", name.getText());
            json.put("Description", location.getText());
            httpConnection.getConnection().sendPost("Stage", json.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        finish();
    }
}

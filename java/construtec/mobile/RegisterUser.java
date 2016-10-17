package construtec.mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Screen that allows a user to create a new account in the database
 */
public class RegisterUser extends AppCompatActivity {
    private EditText code;
    private TextView day;
    private TextView month;
    private TextView year;
    private int dia;
    private int mes;
    private int año;
    private Spinner spinner;

    /**
     * Creates the view
     * @param savedInstanceState required by Android
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        final int dia = cal.get(Calendar.DAY_OF_MONTH);
        final int mes = cal.get(Calendar.MONTH);
        final int año = cal.get(Calendar.YEAR);

        day     = (TextView) findViewById(R.id.day);
        month   = (TextView) findViewById(R.id.month);
        year    = (TextView) findViewById(R.id.year);
        spinner = (Spinner)  findViewById(R.id.Role);
        code    = (EditText) findViewById(R.id.code);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
            R.array.Roles, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String imc_met = spinner.getSelectedItem().toString();

                if(imc_met.equals("Engineer")){
                    code.setVisibility(View.VISIBLE);
                    code.setText("");
                }
                else{
                    code.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        //Controls the click for the date
        View.OnClickListener dateChooser = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(RegisterUser.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        day.setText(Integer.toString(selectedday));
                        String mes;
                        switch(selectedmonth){
                            case 0 : mes = "January";
                                break;
                            case 1 : mes = "February";
                                break;
                            case 2 : mes = "March";
                                break;
                            case 3 : mes = "April";
                                break;
                            case 4 : mes = "May";
                                break;
                            case 5 : mes = "June";
                                break;
                            case 6 : mes = "July";
                                break;
                            case 7 : mes = "August";
                                break;
                            case 8 : mes = "September";
                                break;
                            case 9 : mes = "October";
                                break;
                            case 10 : mes = "November";
                                break;
                            case 11 : mes = "December";
                                break;
                            default: mes = "";
                                break;
                        }
                        month.setText(mes);
                        year.setText(Integer.toString(selectedyear));
                    }

                },
                        año, mes, dia);
                mDatePicker.show();  }
        };

        day.setOnClickListener(dateChooser);
        month.setOnClickListener(dateChooser);
        year.setOnClickListener(dateChooser);
    }

    /**
     *Registers the user and changes screen
     * @param view that calls the method
     */
    public void LogIn(View view){
        registerUserOnDB();

        Intent intent = new Intent(this, UserInformation.class);
        EditText userName = (EditText) findViewById(R.id.userName);
        EditText userID = (EditText) findViewById(R.id.userId);

        intent.putExtra("UserId", userName.getText());
        intent.putExtra("userName", userID.getText());

        if(spinner.getSelectedItem().equals("Engineer")) {
            intent.putExtra("role", "1");
        }else if(spinner.getSelectedItem().equals("Client")) {
            intent.putExtra("role", "0");
        }else{
            intent.putExtra("role", "2");
        }
        startActivity(intent);
    }

    /**
     * Makes the call to the Web Service and registers the user
     */
    private void registerUserOnDB(){
        //TODO: Realiza la llamada a la base de datos con los datos de la interfaz
    }
}

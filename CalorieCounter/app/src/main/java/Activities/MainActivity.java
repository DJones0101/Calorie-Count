package Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.caloriecounter.R;
import Data.Constants;
import Data.DatabaseHandler;
import Model.Food;

public class MainActivity extends AppCompatActivity {

    private EditText foodName, foodCals;
    private Button submitButton, showMyFoodsButton;
    private DatabaseHandler dba;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodName = (EditText) findViewById(R.id.foodEditText);
        foodCals = (EditText) findViewById(R.id.caloriesEditText);
        submitButton = (Button) findViewById(R.id.submitButton);
        showMyFoodsButton = (Button) findViewById(R.id.myFoodList);

        String name = foodName.getText().toString().trim();
        String calString = foodCals.getText().toString().trim();

        Log.v(TAG, "Inside on create");
        Log.v(TAG, "Inside on create");



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveDataToDB();

            }
        });


        showMyFoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to the next screen
                finish();
                startActivity(new Intent(MainActivity.this, DisplayFoodsActivity.class));
            }
        });

    }

    private void saveDataToDB() {
        Food food = new Food();
        String name = foodName.getText().toString().trim();
        String calString = foodCals.getText().toString().trim();

        Log.v(TAG, "  " + name);
        Log.v(TAG, "  " + calString);

        dba = new DatabaseHandler(this);

         int cals = Integer.parseInt(calString);
        if (name.equals("") || calString.equals("")) {
            // enter something
            //Toast.makeText(getApplicationContext(), Constants.TOAST_ERROR, Toast.LENGTH_LONG).show();

        } else {

            food.setFoodName(name);
            food.setCalories(cals);
            dba.addFood(food);
            dba.close();

            //clear the form
            foodName.setText("");
            foodCals.setText("");


        }


    }

}

package Activities;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.caloriecounter.R;
import Data.Constants;
import Data.DatabaseHandler;
import Model.Food;

public class FoodItemDetailsActivity extends AppCompatActivity {
    private TextView foodName, calories, dateEaten;
    private Button shareButton, deleteFood;
    private int foodId;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_details);
        foodName = (TextView) findViewById(R.id.detsFoodName);
        calories = (TextView) findViewById(R.id.detsCaloriesValue);
        dateEaten = (TextView) findViewById(R.id.detsDateText);
        shareButton = (Button) findViewById(R.id.ShareButton);
        deleteFood = (Button) findViewById(R.id.deleteButton);
        final Food food = (Food) getIntent().getSerializableExtra("userObj");

        foodName.setText(food.getFoodName());
        calories.setText(String.valueOf(food.getCalories()));
        dateEaten.setText(food.getRecordDate());
        foodId = food.getFoodId();
        calories.setTextSize(34.9f);
        calories.setTextColor(Color.BLUE);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCals();
            }
        });

        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFood(food);
            }
        });



    }

    public void shareCals(){

        StringBuilder dataString = new StringBuilder();
        String name = foodName.getText().toString();
        String cals = calories.getText().toString();
        String date = dateEaten.getText().toString();

        dataString.append(" Food: " + name + " \n");
        dataString.append(" Calories: " + cals + "\n");
        dataString.append(" Eaten on: " + date + "\n");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, Constants.EMAIL_TITLE);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@example.com"});
        intent.putExtra(Intent.EXTRA_TEXT, dataString.toString());

        try{
            startActivity(Intent.createChooser(intent, "Send mail..."));
        }catch ( ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(), Constants.EMAIL_ERROR, Toast.LENGTH_LONG).show();
        }
    }



    public void deleteFood(Food food) {
        dba = new DatabaseHandler(FoodItemDetailsActivity.this);
        int id = food.getFoodId();
        dba.deleteFood(foodId);
        finish();
        startActivity(new Intent(FoodItemDetailsActivity.this, DisplayFoodsActivity.class));

    }
}

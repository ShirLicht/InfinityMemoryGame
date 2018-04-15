package com.example.shir.infinitymemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private Button easyLevelButton;
    private Button mediumLevelButton;
    private Button hardLevelButton;
    private String gridLayoutSize;
    private String timer;
    private String[] inputStrings, outputStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //initialize the levels button's
        easyLevelButton = (Button)findViewById(R.id.easyLevelButton);
       // mediumLevelButton = (Button)findViewById(R.id.mediumLevelButton);
      //  hardLevelButton = (Button)findViewById(R.id.hardLevelButton);
        outputStrings = new String[4];
        if(getIntent().hasExtra("com.example.shir.infinitymemorygame.SOMETHING")) {

            //String[] inputStrings = getIntent().getStringArrayExtra("com.example.shir.infinitymemorygame.SOMETHING");
            inputStrings = getIntent().getExtras().getStringArray("com.example.shir.infinitymemorygame.SOMETHING");
            TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
            nameTextView.setText(inputStrings[0]);

            TextView ageTextView = (TextView) findViewById(R.id.ageTextView);
            ageTextView.setText(inputStrings[1]);
        }

        //Event for each level button - define the sizes (number of rows&columns) of the grid layout of the GameLevelActivity
        easyLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridLayoutSize = "2";
                timer = "30";
                //levelButtonClicked(gridLayoutSize);
                //Intent levelIntent1 = new Intent(getApplicationContext(), GameLevelActivity.class);

                //pass information to the Second Activity - size of the level activity grid layout
                //levelIntent1.putExtra("com.example.shir.infinitymemorygame.SOMETHING", gridLayoutSize);

                //startActivity(levelIntent1);
                levelButtonClicked(gridLayoutSize, timer, inputStrings);
            }
        });

        mediumLevelButton = (Button)findViewById(R.id.mediumLevelButton);
        mediumLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridLayoutSize = "4";
                timer = "45";
               // Intent levelIntent2 = new Intent(getApplicationContext(),  GameLevelActivity.class);

                //pass information to the Second Activity - size of the level activity grid layout
               // levelIntent2.putExtra("com.example.shir.infinitymemorygame.SOMETHING", gridLayoutSize);

               // startActivity(levelIntent2);
                levelButtonClicked(gridLayoutSize, timer, inputStrings);
            }
        });

        hardLevelButton = (Button)findViewById(R.id.hardLevelButton);
        hardLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gridLayoutSize = "6";
                timer = "60";
                //Intent levelIntent3 = new Intent(getApplicationContext(), GameLevelActivity.class);

                //pass information to the Second Activity - size of the level activity grid layout
               // levelIntent3.putExtra("com.example.shir.infinitymemorygame.SOMETHING", gridLayoutSize);

               // startActivity(levelIntent3);
                levelButtonClicked(gridLayoutSize, timer, inputStrings);
            }
        });
    }



//Start and move to the Game Level Activity
    protected void levelButtonClicked(String size, String timer, String [] str) {
        Intent levelIntent = new Intent(getApplicationContext(), GameLevelActivity.class);

        outputStrings[0] = str[0];
        outputStrings[1] = str[1];
        outputStrings[2] = size;
        outputStrings[3] = timer;



        //pass information to the Second Activity - level game, timer, name and age
        levelIntent.putExtra("com.example.shir.infinitymemorygame.SOMETHING",outputStrings);

        startActivity(levelIntent);

    }

    private void loadMainActivity() {
        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(startIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadMainActivity();
    }
}

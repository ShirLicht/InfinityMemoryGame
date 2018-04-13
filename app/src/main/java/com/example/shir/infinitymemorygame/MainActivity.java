package com.example.shir.infinitymemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitBtn = (Button)findViewById(R.id.submitButton);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);

                //get input from the user
                nameEditText = (EditText)findViewById(R.id.nameEditText);
               ageEditText = (EditText)findViewById(R.id.ageEditText);
               String[] input = {nameEditText.getText().toString(), ageEditText.getText().toString()};

                //pass information to the Second Activity - name & age
               startIntent.putExtra("com.example.shir.infinitymemorygame.SOMETHING",input);
               startActivity(startIntent);
            }
        });
    }
}

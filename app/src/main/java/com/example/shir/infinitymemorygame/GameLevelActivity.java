package com.example.shir.infinitymemorygame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameLevelActivity extends AppCompatActivity implements View.OnClickListener {

    //----------definition of variables----------

    //----music----
   // private MediaPlayer mediaPlayer;

    //----i/o----
    private String[] inputStrings, outputStrings;
    private int gridLayoutSize;

    //----game buttons----
    private Button backButton, pauseMusicButton, resumeMusicButton;

    private int numberOfElements;

    int numOfColumns, numOfRows, sizeOfGraphics;

    private MemoryButton[] buttons;
    //what each button resource should be
    // store all the different combination we have
    private int[] buttonGraphicLocations;
    //actual id's
    private int[] buttonGraphics;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;

    //----timer----
    private int timer;
    private final long convertMilliSeconds = 1000;
    private long timerTimeLeftInMillis;
    private CountDownTimer mCountDownTimer;

    //----xml----
    private TextView nameTextView, timerTextView;
    private GridLayout gridLayout;
    private ImageView gameOverImageView, winnerImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level);

        initVariables();

        //Get information from the previous Activity
        if (getIntent().hasExtra("com.example.shir.infinitymemorygame.SOMETHING")) {
            // gridLayoutSize = getIntent().getExtras().getInt("com.example.shir.infinitymemorygame.SOMETHING");
            inputStrings = getIntent().getExtras().getStringArray("com.example.shir.infinitymemorygame.SOMETHING");

            nameTextView = (TextView) findViewById(R.id.nameTextView);
            nameTextView.setText(inputStrings[0]);

            //save the user's details
            outputStrings[0] = inputStrings[0];
            outputStrings[1] = inputStrings[1];

            gridLayoutSize = Integer.parseInt(inputStrings[2]);
            timer = Integer.parseInt(inputStrings[3]);
            timerTimeLeftInMillis = (long) timer * convertMilliSeconds;

            //set the grid layout sizes
            gridLayout.setColumnCount(gridLayoutSize);
            gridLayout.setRowCount(gridLayoutSize);
        }

       // initButtonVariables();
        numOfColumns = gridLayout.getColumnCount();
        numOfRows = gridLayout.getRowCount();

        numberOfElements = numOfColumns * numOfRows;

        buttons = new MemoryButton[numberOfElements];

        sizeOfGraphics = numberOfElements / 2;
        //each image will appear twice
        buttonGraphics = new int[sizeOfGraphics];
        fillButtonsArray(buttonGraphics, sizeOfGraphics);

        buttonGraphicLocations = new int[numberOfElements];

        shuffleButtonsGraphics();

        createMemoryButtons(gridLayout);
        startTimer();


    }

    public void fillButtonsArray(int[] buttons, int arraySize) {

        buttons[0] = R.drawable.aladin2;
        buttons[1] = R.drawable.madagascar;

        if (arraySize > 2) {
            buttons[2] = R.drawable.lion_king;
            buttons[3] = R.drawable.lady;
            buttons[4] = R.drawable.coco;
            buttons[5] = R.drawable.beauty;
            buttons[6] = R.drawable.bambi;
            buttons[7] = R.drawable.dogs;

            if (arraySize > 8) {
                buttons[8] = R.drawable.jungle_book;
                buttons[9] = R.drawable.mermaid;
                buttons[10] = R.drawable.mickey;
                buttons[11] = R.drawable.minnie;
                buttons[12] = R.drawable.peter;
                buttons[13] = R.drawable.pinokio;
                buttons[14] = R.drawable.reponzel;
                buttons[15] = R.drawable.snow_white;
                buttons[16] = R.drawable.cinderella;
                buttons[17] = R.drawable.lilo;
            }
        }
    }

    protected void shuffleButtonsGraphics() {
        int i, temp, swapIndex;

        Random rand = new Random();

        for (i = 0; i < numberOfElements; i++) {
            buttonGraphicLocations[i] = i % sizeOfGraphics;
        }

        for (i = 0; i < numberOfElements; i++) {
            temp = buttonGraphicLocations[i];
            swapIndex = rand.nextInt(numberOfElements);
            buttonGraphicLocations[i] = buttonGraphicLocations[swapIndex];
            buttonGraphicLocations[swapIndex] = temp;
        }

    }

    protected void createMemoryButtons(GridLayout gridLayout) {
        int row, column, location;

        for (row = 0; row < numOfRows; row++) {
            for (column = 0; column < numOfColumns; column++) {
                location = row * numOfColumns + column;
                MemoryButton tempButton = new MemoryButton(this, row, column, buttonGraphics[buttonGraphicLocations[location]], gridLayoutSize);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[location] = tempButton;
                gridLayout.addView(tempButton);
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (isBusy)
            return;

        MemoryButton button = (MemoryButton) view;

        if (button.isMatched)
            return;

        //no other card has been flipped before
        if (selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }

        //check if the user pressed on the same button twice
        if (selectedButton1.getId() == button.getId())
            return;

        //if a match is found
        if (selectedButton1.getFrontDrawableId() == button.getFrontDrawableId()) {
            button.flip();
            button.setMatched(true);
            selectedButton1.setMatched(true);

            //disable the matched buttons
            selectedButton1.setEnabled(false);
            button.setEnabled(false);

            selectedButton1 = null;

            checkGameState();

            return;
        }

        //a match was not found
        else {
            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;

            //delay an execution of a method
            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton1 = null;
                    selectedButton2 = null;

                    //enable the user to do new presses
                    isBusy = false;
                }
            }, 500);
        }
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(timerTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                gameOverImageView.setVisibility(View.VISIBLE);
                loadMenuActivity();
                                    }


        }.start();
    }

    private void updateCountDownText() {
        final int minutes = 0;
        int seconds = (int) (timerTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);

    }

    private void loadMenuActivity() {
        Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
        backgroundMusic(false);
        //pass information to the Second Activity - name & age
        startIntent.putExtra("com.example.shir.infinitymemorygame.SOMETHING", outputStrings);
        startActivity(startIntent);
    }

    private void backgroundMusic(boolean play) {
        Intent newIntent = new Intent(getApplicationContext(), BackgroundMusicService.class);

        if (play == true)
            startService(newIntent);
        else
            stopService(newIntent);
    }

    private void initVariables() {

        //play background music
        backgroundMusic(true);

        //------initial xml variables------
        gridLayout = (GridLayout) findViewById(R.id.grid_layout);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        winnerImageView = (ImageView)findViewById(R.id.winnerImageView);
        gameOverImageView = (ImageView)findViewById(R.id.gameOverImageView);

        //------initialize xml buttons------
        resumeMusicButton = (Button) findViewById(R.id.unMuteButton);
        pauseMusicButton = (Button) findViewById(R.id.muteButton);
        backButton = (Button) findViewById(R.id.backButton);

        //------initialize i/o variables-------

        outputStrings = new String[2];

        //handle the mute/unmute music event
        muteUnMuteMusic();
        //handle "go back to previous page" event
        goBack ();


    }

    private void initButtonVariables() {
        numOfColumns = gridLayout.getColumnCount();
        numOfRows = gridLayout.getRowCount();

        numberOfElements = numOfColumns * numOfRows;

        buttons = new MemoryButton[numberOfElements];

        sizeOfGraphics = numberOfElements / 2;
        //each image will appear twice
        buttonGraphics = new int[sizeOfGraphics];
        fillButtonsArray(buttonGraphics, sizeOfGraphics);

        buttonGraphicLocations = new int[numberOfElements];
    }

    private void muteUnMuteMusic()
    {
        pauseMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundMusic(false);
                pauseMusicButton.setVisibility(View.INVISIBLE);
                resumeMusicButton.setVisibility(View.VISIBLE);
            }
        });

        resumeMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundMusic(true);
                pauseMusicButton.setVisibility(View.VISIBLE);
                resumeMusicButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void goBack ()
    {
        //back to the level choice memu
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMenuActivity();
            }
        });
    }

    private void checkGameState()
    {
        int i;

        //check if the user found all the pairs of pictures
        for(i=0 ; i<numberOfElements ; i++)
        {
            if(buttons[i].isEnabled() != false)
                break;
        }

        if(i == numberOfElements)
        {
            winnerImageView.setVisibility(View.VISIBLE);
            loadMenuActivity();
        }
    }
}



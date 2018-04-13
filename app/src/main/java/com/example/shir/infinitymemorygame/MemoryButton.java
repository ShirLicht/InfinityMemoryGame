package com.example.shir.infinitymemorygame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.Button;
import android.widget.GridLayout;


/**
 * Created by shir on 03/04/2018.
 */


@SuppressLint("AppCompatCustomView")
public class MemoryButton extends Button{

    protected int row;
    protected int column;
    protected int frontDrawableId;
    private int gridSize, buttonSize;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    @SuppressLint("RestrictedApi")
    public MemoryButton(Context context, int row, int column, int frontImageDrawableId, int gridSize){

        super(context);

        row = row;
        column = column;
        frontDrawableId = frontImageDrawableId;
        gridSize = gridSize;

        initButtonSize();

        front = AppCompatDrawableManager.get().getDrawable(context, frontImageDrawableId);
        back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.disney);

        setBackground(back);
       GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(row),GridLayout.spec(column));

        if(gridSize == 2) {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 200;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 200;
        }
        else if (gridSize == 4)
        {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 95;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 95;
        }
        else if(gridSize == 6)
        {
            tempParams.width = (int) getResources().getDisplayMetrics().density * 65;
            tempParams.height = (int) getResources().getDisplayMetrics().density * 65;
        }

      setLayoutParams(tempParams);


    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontDrawableId() {
        return frontDrawableId;
    }

    public void flip() {

            if(isMatched)
                return;

            if(isFlipped)
            {
                setBackground(back);
                isFlipped = false;
            }

            else {

                setBackground(front);
                isFlipped = true;
            }

    }


    private void initButtonSize()
    {
        if(gridSize == 2)
            buttonSize = 170;
        else if (gridSize == 4)
            buttonSize = 75;
        else if(gridSize == 6)
            buttonSize = 60;
    }
}

package com.example.application.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView[][] images = new ImageView[3][3];
    private int imageTag;

    private boolean player1Turn = true;

    private int roundCount=0;

    private int player1Score=0;
    private int player2Score=0;

    private TextView player1TextView;
    private TextView player2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1TextView = findViewById(R.id.text_view_p1);
        player2TextView = findViewById(R.id.text_view_p2);
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String imgId = "img_" + i + j;
                int resId = getResources().getIdentifier(imgId, "id", getPackageName());
                images[i][j] = findViewById(resId);
                images[i][j].setTag(R.drawable.empty_rectangle);
                images[i][j].setImageResource(R.drawable.empty_rectangle);
                images[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                player1Score = 0;
                player2Score = 0;
                player1TextView.setText("Player 1: " + player1Score);
                player2TextView.setText("Player 2: " + player2Score);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if ((Integer) view.getTag() == R.drawable.empty_rectangle){
            if (player1Turn) {
                ((ImageView) view).setImageResource(R.drawable.x_rectangle);
                view.setTag(R.drawable.x_rectangle);
                if (checkForWin()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reset();
                    player1Score++;
                    player1TextView.setText("Player 1: " + player1Score);
                }
                player1Turn = false;
            } else {
                ((ImageView) view).setImageResource(R.drawable.o_rectangle);
                view.setTag(R.drawable.o_rectangle);
                if (checkForWin()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    reset();
                    player2Score++;
                    player2TextView.setText("Player 2: " + player2Score);
                }
                player1Turn = true;
            }
            roundCount++;
            if (roundCount == 9) {
                reset();
            }
        } else {
            return;
        }
    }

    private void reset(){
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                images[i][j].setImageResource(R.drawable.empty_rectangle);
                images[i][j].setTag(R.drawable.empty_rectangle);
                player1Turn=true;
                roundCount=0;
            }
        }
    }

    private boolean checkForWin(){
        // check for rows
        for (int i=0; i<3; i++) {
            if ((images[i][0].getTag().equals(images[i][1].getTag()))
                && (images[i][0].getTag().equals(images[i][2].getTag()))
                    && (!images[i][0].getTag().equals(R.drawable.empty_rectangle))){
                return true;
            }
        }

        // check for columns
        for (int i=0; i<3; i++) {
            if ((images[0][i].getTag().equals(images[1][i].getTag()))
                &&(images[0][i].getTag().equals(images[2][i].getTag()))
                    && (!images[0][i].getTag().equals(R.drawable.empty_rectangle))){
                return true;
            }
        }

        // check for diagonals
        if ((images[0][0].getTag().equals(images[1][1].getTag()))
                &&(images[0][0].getTag().equals(images[2][2].getTag()))
                && (!images[0][0].getTag().equals(R.drawable.empty_rectangle))){
            return true;
        }
        if ((images[2][0].getTag().equals(images[1][1].getTag()))
                &&(images[2][0].getTag().equals(images[0][2].getTag()))
                && (!images[2][0].getTag().equals(R.drawable.empty_rectangle))){
            return true;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Score", player1Score);
        outState.putInt("player2Score", player2Score);
        outState.putBoolean("player1Turn", player1Turn);

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++){
                imageTag = (Integer) images[i][j].getTag();
                outState.putInt("boardState+"+i+j, imageTag);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Score = savedInstanceState.getInt("player1Score");
        player2Score = savedInstanceState.getInt("player2Score");
        player1Turn = savedInstanceState.getBoolean("player1Turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                imageTag = savedInstanceState.getInt("boardState+"+i+j);
                images[i][j].setTag(imageTag);
                if (imageTag == R.drawable.empty_rectangle) {
                    images[i][j].setImageResource(R.drawable.empty_rectangle);
                }
                if (imageTag == R.drawable.x_rectangle) {
                    images[i][j].setImageResource(R.drawable.x_rectangle);
                }
                if (imageTag == R.drawable.o_rectangle) {
                    images[i][j].setImageResource(R.drawable.o_rectangle);
                }
            }
        }
    }
}

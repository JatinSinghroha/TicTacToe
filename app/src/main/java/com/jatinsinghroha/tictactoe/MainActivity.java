package com.jatinsinghroha.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    String winner = "";

    enum Player {
        One, Two, No
    }

    Player[] gridPositions = new Player[9];

    Player currentPlayer = Player.One;

    boolean gameOver = false;
    int filledGrids = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<9; i++){
            gridPositions[i] = Player.No;
        }

    }

    public void onTapImage(View view){
        ImageView clickImageView = (ImageView) view;

        int index = Integer.parseInt(clickImageView.getTag().toString());

        if(gridPositions[index] == Player.No && !gameOver && filledGrids <9){
            if(currentPlayer == Player.One){
                gridPositions[index] = Player.One;
                clickImageView.setImageResource(R.drawable.lion);
                clickImageView.setAlpha(1.0f);
                currentPlayer = Player.Two;
            }
            else{
                gridPositions[index] = Player.Two;
                clickImageView.setImageResource(R.drawable.tiger);
                clickImageView.setAlpha(1.0f);
                currentPlayer = Player.One;
            }
            filledGrids++;

            if(filledGrids >=9){
                gameOver = true;
            }

            if(filledGrids >= 5 && filledGrids <=9){

                for (int[] winnerRow: winnerRowsColumns) {
                    if(gridPositions[winnerRow[0]] == gridPositions[winnerRow[1]] &&
                            gridPositions[winnerRow[1]] == gridPositions[winnerRow[2]] && gridPositions[winnerRow[0]] != Player.No){

                        if(currentPlayer == Player.One){
                            winner = "Tiger - Player 2";
                        }
                        else{
                            winner = "Lion - Player 1";
                        }
                        Toast.makeText(this, winner+" is the winner of this match", Toast.LENGTH_LONG).show();
                        gameOver = true;
                        Log.e("winnerRow", winnerRow[0]+ "," + winnerRow[1] +","+ winnerRow[2]+" - "+winner);
                        break;
                    }
                }
            }
        }
        else{
            Toast.makeText(this, "This Grid is already filled or Game is over.", Toast.LENGTH_SHORT).show();
        }

    }
}
package com.jatinsinghroha.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class MainActivity extends AppCompatActivity {

    GridLayout mGridLayout, mScoreGrid;

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
    int scorePlayer1 = 0, scorePlayer2 = 0, scoreDraw = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridLayout = findViewById(R.id.gridLayout);
        mScoreGrid = findViewById(R.id.scoreGrid);
        resetEverything();
    }

    public void onTapImage(View view){
        ImageView clickImageView = (ImageView) view;

        int index = Integer.parseInt(clickImageView.getTag().toString());

        //clickImageView.getTag().toString() == "4" = "231321" = 231321

//        String indexString = clickImageView.getTag().toString();
//
//        Player abc = gridPositions[Integer.parseInt(indexString)];

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

            if(filledGrids >= 5 && filledGrids <=9){
                /**
                 * {2, 5, 8} = int[] winnerRow = {2, 5, 8}
                 * winnerRow[0] = 2, winnerRow[1] = 5, winnerRow[2] = 8
                 *
                 * gridPositions[winnerRow[0]] == gridPositions[winnerRow[1]] == gridPositions[winnerRow[2]]
                 *
                 * gridPositions[2] == gridPositions[5] == gridPositions[8]
                 *
                 * A = B && B = C then we can say that A = C ==> A = B = C
                 */
                for (int[] winnerRow: winnerRowsColumns) {
                    Log.e("winnerRow", winnerRow[0]+ "," + winnerRow[1] +","+ winnerRow[2]+" - "+winner);
                    if(gridPositions[winnerRow[0]] == gridPositions[winnerRow[1]] &&
                            gridPositions[winnerRow[1]] == gridPositions[winnerRow[2]] && gridPositions[winnerRow[0]] != Player.No){

                        if(currentPlayer == Player.One){
                            winner = "Tiger - Player 2";
                            displayOnResult("Player 2 Won",
                                    "Player 2 have won this come,\nCome On Player 1.",
                                    R.raw.won2,
                                    2);
                        }
                        else{
                            winner = "Lion - Player 1";
                            displayOnResult("Player 1 Won",
                                    "Player 1 have won this come,\nCome On Player 2.",
                                    R.raw.won1,
                                    1);
                        }
                        gameOver = true;
                        Log.e("winnerRow", winnerRow[0]+ "," + winnerRow[1] +","+ winnerRow[2]+" - "+winner);
                        break;
                    }
                }
            }

            if(filledGrids >=9 && !gameOver){
                gameOver = true;
                displayOnResult("Game is Drawn"
                        , "No one have won the game.\nTry Again.", R.raw.draw, 0);
            }
        }
        else{
            Toast.makeText(this, "This Grid is already filled or Game is over.", Toast.LENGTH_SHORT).show();
        }

    }

    // Player 1 = 1, Player 2 = 2, Draw = 0

    private void displayOnResult(String title, String message, Integer gifImage, int whoWon){

        switch (whoWon){
            case 1:{
                TextView textView = (TextView) mScoreGrid.getChildAt(3);
                scorePlayer1++;
                textView.setText(String.valueOf(scorePlayer1));
                break;
            }
            case 2:{
                TextView textView = (TextView) mScoreGrid.getChildAt(4);
                scorePlayer2++;
                textView.setText(String.valueOf(scorePlayer2));
                break;
            }
            case 0:
            default:{
                TextView textView = (TextView) mScoreGrid.getChildAt(5);
                scoreDraw++;
                textView.setText(String.valueOf(scoreDraw));
                break;
            }
        }

        new MaterialDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Okay", R.drawable.ic_baseline_download_done_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        resetEverything();
                    }
                })
                .setNegativeButton("Exit", R.drawable.ic_baseline_cancel_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setAnimation(gifImage)
                .build().show();
    }

    private void resetEverything(){
        gameOver = false;
        filledGrids = 0;
        currentPlayer = Player.One;

        for(int i=0; i<9; i++) {
            gridPositions[i] = Player.No;

            ImageView imageView = (ImageView) mGridLayout.getChildAt(i);
            imageView.setAlpha(0.2f);
            imageView.setImageDrawable(null);
        }
    }
}
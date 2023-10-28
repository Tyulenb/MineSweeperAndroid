package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button[][] cells;
    int WIDTH = 5;
    int HEIGHT = 5;

    TextView mines;

    int MINESCONST = 3;

    int MinesCurrent = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generate();
    }

    public void generate(){
        GridLayout layout = findViewById(R.id.GRID);
        layout.removeAllViews();
        layout.setColumnCount(WIDTH);
        cells = new Button[HEIGHT][WIDTH];
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        TextView textView = findViewById(R.id.Mines);
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                cells[i][j] = (Button)inflater.inflate(R.layout.cell, layout, false);
            }
        }

        int[][] arr = new int[HEIGHT][WIDTH];
        for(int[] a: arr){
            Arrays.fill(a, 0);
        }
        Random rand = new Random();
        int[] dif = new int[3]; dif[0]=-1; dif[1]=-1; dif[2]=-1;
        for(int i = 0; i < 3; i++){
            int r = rand.nextInt(25);
            while(r == dif[0] || r==dif[1] || r==dif[2]){
                r=rand.nextInt(25);
            }
            dif[i]=r;
            arr[(r)/5][(r)%5] = 10;
            Log.i("INFO", ""+(r)/5+" "+(r)%5);
        }
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                if(arr[i][j]==10) {
                    continue;
                }
                if(i-1>-1){
                    if(arr[i-1][j]==10){
                        arr[i][j]++;
                    }
                }
                if(j-1>-1){
                    if(arr[i][j-1]==10){
                        arr[i][j]++;
                    }
                }
                if(i+1 < HEIGHT){
                    if(arr[i+1][j]==10){
                        arr[i][j]++;
                    }
                }
                if(j+1 < WIDTH){
                    if(arr[i][j+1]==10){
                        arr[i][j]++;
                    }
                }
                if(i-1>-1 && j-1>-1){
                    if(arr[i-1][j-1]==10){
                        arr[i][j]++;
                    }
                }
                if(i-1>-1 && j+1<WIDTH){
                    if(arr[i-1][j+1]==10){
                        arr[i][j]++;
                    }
                }
                if(i+1<HEIGHT && j+1<WIDTH){
                    if(arr[i+1][j+1]==10){
                        arr[i][j]++;
                    }
                }
                if(i+1<HEIGHT && j-1>-1){
                    if(arr[i+1][j-1]==10){
                        arr[i][j]++;
                    }
                }
            }
        }

        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                cells[i][j].setText("");
                int finalI = i;
                int finalJ = j;
                cells[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(arr[finalI][finalJ] != 10){
                            cells[finalI][finalJ].setText(""+arr[finalI][finalJ]);
                        }
                        else{
                            textView.setText("GAME OVER");
                        }
                    }
                });
                cells[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(arr[finalI][finalJ]==10){
                            view.setBackgroundColor(Color.RED);
                            MinesCurrent--;
                            textView.setText(""+MinesCurrent+"/"+MINESCONST);
                            if(MinesCurrent==0){
                                textView.setText("YOU WIN!");
                            }
                        }
                        return  false;
                    }
                });
                cells[i][j].setTag(""+(j+HEIGHT*i));
                layout.addView(cells[i][j]);
                textView.setText(""+MinesCurrent+"/"+MINESCONST);
            }
        }
    }
}
package com.example.iot23.mywhackamole;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class PlayGame extends ActionBarActivity {

    int mScore = 0;
    int mTime = 40;
    int[] imgValue = new int[9];
    ImageButton[] imgMole = new ImageButton[9];
    TextView txtTime;
    TextView txtScore;
    int flag = 0;
    SoundPool pool;
    int wind;
    int squeeze;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        txtScore = (TextView)findViewById(R.id.textScore);
        txtTime = (TextView)findViewById(R.id.textPlayTime);
        pool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);

        squeeze = pool.load(getBaseContext(), R.raw.squeeze, 1);
        wind = pool.load(getBaseContext(), R.raw.wind, 1);

        imgMole[0] = (ImageButton)findViewById(R.id.imageButton1);
        imgMole[1] = (ImageButton)findViewById(R.id.imageButton2);
        imgMole[2] = (ImageButton)findViewById(R.id.imageButton3);
        imgMole[3] = (ImageButton)findViewById(R.id.imageButton4);
        imgMole[4] = (ImageButton)findViewById(R.id.imageButton5);
        imgMole[5] = (ImageButton)findViewById(R.id.imageButton6);
        imgMole[6] = (ImageButton)findViewById(R.id.imageButton7);
        imgMole[7] = (ImageButton)findViewById(R.id.imageButton8);
        imgMole[8] = (ImageButton)findViewById(R.id.imageButton9);

        for(int i=0; i<9; i++){
            final int arrayI = i;
            imgMole[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    imgMole[arrayI].setImageResource(0);
                    if(imgValue[arrayI] == 1){
                        pool.play(squeeze, 1, 1, 0, 0, 1);
                        mScore += 10;
                        imgValue[arrayI] =0;
                        txtScore.setText(String.valueOf(mScore));
                    } else {
                        pool.play(wind, 1, 1, 0, 0, 1);
                    }

                }
            });
        }

    }

    Handler timeHandler = new Handler() {
        public void handleMessage(Message msg) {


            if(flag == 1) {
                timeHandler.sendEmptyMessageDelayed(0, 1000);
                updateTime();
            }


        }
    };

    Handler positionHandler = new Handler() {
        public void handleMessage(Message msg) {
            selectPosition();

            if(mTime == 0){
                for(int i=0; i<9;i++){
                    imgValue[i] = 0;
                    imgMole[i].setImageResource(0);
                }

                flag = 0;

                Toast.makeText(getBaseContext(), "게임 종료", Toast.LENGTH_SHORT).show();



            }

            if(flag==1) {
                positionHandler.sendEmptyMessageDelayed(1, 700);
            }

        }
    };

    public void onButtonRestartClicked(View v) {
        mScore = 0;
        mTime = 40;
        flag = 1;
        timeHandler.sendEmptyMessageDelayed(0, 1000);
        positionHandler.sendEmptyMessageDelayed(1, 700);

    }

    public void onButtonCloseClicked(View v) {
        finish();
    }

    private void updateTime() {
        --mTime;
        txtTime.setText(String.valueOf(mTime));
    }

    private void selectPosition() {

        Random random = new Random();

        int selectedMoleNumber = random.nextInt(4);

        int selectedIndex;

        for(int i=0; i<9;i++){
            imgValue[i] = 0;
            imgMole[i].setImageResource(0);
        }

        switch (selectedMoleNumber) {
            case 1:
                selectedIndex = random.nextInt(9);
                imgValue[selectedIndex] = 1;
                imgMole[selectedIndex].setImageResource(R.drawable.mole);
                break;
            case 2:
                for(int i = 0; i < 2; ++i) {
                    selectedIndex = random.nextInt(9);
                    imgValue[selectedIndex] = 1;
                    imgMole[selectedIndex].setImageResource(R.drawable.mole);
                }
                break;
            case 3:
                for(int i = 0; i < 3; ++i) {
                    selectedIndex = random.nextInt(9);
                    imgValue[selectedIndex] = 1;
                    imgMole[selectedIndex].setImageResource(R.drawable.mole);
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

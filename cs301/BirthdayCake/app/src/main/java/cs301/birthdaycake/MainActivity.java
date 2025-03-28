package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView findID = findViewById(R.id.cakeview);
        CakeController CC = new CakeController(findID);

        Button buttonView = findViewById(R.id.ExtinguishButton);
        buttonView.setOnClickListener(CC);

        CompoundButton candlesSwitch = findViewById(R.id.CandlesSwitch);
        candlesSwitch.setOnCheckedChangeListener(CC);

        SeekBar amtCandles = findViewById(R.id.seekBar3);
        amtCandles.setOnSeekBarChangeListener(CC);

        findID.setOnTouchListener(CC);
    }

    public void goodbye(View button) {
        Log.i("ExtinguishButton", "Goodbye");
    }
}

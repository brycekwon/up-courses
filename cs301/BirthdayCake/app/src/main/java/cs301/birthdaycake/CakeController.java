package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, View.OnTouchListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    //Instance Vars
    private CakeView pCV;
    private CakeModel pCM;


    public CakeController(CakeView cV) {
        //Setters
        pCV = cV;
        pCM = cV.getCakeController();
    }

    public void onClick(View button) {
        Log.d("ExtinguishButton", "Cake Controller onClick");

        pCM.candlesLit = false;
        pCV.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton bV, boolean checked) {
        Log.d("CandlesSwitch", "Cake Controller candleSwitch");

        if(!checked) {
            pCM.hasCandles = false;
            pCV.invalidate();
        }else {
            pCM.hasCandles = true;
            pCV.invalidate();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        pCM.candleCake = i;
        pCV.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        pCM.xfloat = motionEvent.getX();
        pCM.yfloat = motionEvent.getY();
        pCM.touched = true;

        pCM.hasBalloon = true;
        pCM.balloonX = motionEvent.getX();
        pCM.balloonY = motionEvent.getY();
        pCV.xCordTxt = (int) motionEvent.getX();
        pCV.yCordTxt = (int) motionEvent.getY();

        pCV.invalidate();
        return true;
    }
}

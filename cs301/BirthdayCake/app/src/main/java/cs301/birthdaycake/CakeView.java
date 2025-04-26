package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint squareOne = new Paint();
    Paint squareTwo = new Paint();

    Paint balloonPaint = new Paint();
    Paint balloonString = new Paint();

    Paint textPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;
    public int xCordTxt;
    public int yCordTxt;
    public String posText;



    /*
    Lab03 Instance Vars
     */
    private CakeModel cakeController;


    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cakeController = new CakeModel();

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(Color.rgb(254, 74, 73));  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        balloonPaint.setColor(Color.BLUE);
        balloonPaint.setStyle(Paint.Style.FILL);
        balloonString.setColor(Color.BLACK);
        balloonString.setStyle(Paint.Style.STROKE);
        balloonString.setStrokeWidth(8);

        setBackgroundColor(Color.WHITE);  //better than black default

        squareOne.setColor(Color.rgb(254, 215, 102));
        squareOne.setStyle(Paint.Style.FILL);
        squareTwo.setColor(Color.rgb(42, 183, 202));
        squareTwo.setStyle(Paint.Style.FILL);
    }

    /**
     * draws a balloon that is not perfectly round but, instead, more "balloon-like".
     */
    public void drawBalloon(Canvas canvas, int x, int y) {
        if (cakeController.hasBalloon) {
            canvas.drawOval(x-105, y-140, x+150, y+200, balloonPaint);
            canvas.drawLine(x+20, y+200, x+20, y+500, balloonString);
        }
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        if(cakeController.hasCandles) {
            canvas.drawRect(left, bottom - candleHeight, left + candleWidth+20, bottom, candlePaint);

            if (cakeController.candlesLit) {
                //draw the outer flame
                float flameCenterX = left + candleWidth / 2;
                float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX+10, flameCenterY, outerFlameRadius+20, outerFlamePaint);

                //draw the inner flame
                flameCenterY += outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX+10, flameCenterY, innerFlameRadius+20, innerFlamePaint);
            }

            //draw the wick
            float wickLeft = left + candleWidth / 2 - wickWidth / 2;
            float wickTop = bottom - wickHeight - candleHeight;
            canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth+20, wickTop + wickHeight, wickPaint);
        }
    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        //Now a candle in the center
        if(cakeController.hasCandles) {
            int z = cakeController.candleCake + 1;
            float y = candleWidth / 2;
            float x = cakeWidth / z;
            for(int i = 1; i <= cakeController.candleCake; i++) {
                drawCandle(canvas, (i * x) - y, cakeTop);
            }

            posText = xCordTxt + ", " + yCordTxt;
            textPaint.setColor(Color.RED);
            textPaint.setTextSize(125);
            canvas.drawText(posText,1800,1000,textPaint);
        }

        drawBalloon(canvas, (int)cakeController.balloonX, (int)cakeController.balloonY);

        Squares(canvas, cakeController.xfloat, cakeController.yfloat);
    }//onDraw


    public void Squares(Canvas canvas, float x, float y) {
        if(cakeController.touched) {
            canvas.drawRect(x - 50, y - 50, x, y, squareOne);
            canvas.drawRect(x, y-50, x+50, y, squareTwo);
            canvas.drawRect(x, y, x+50, y + 50, squareOne);
            canvas.drawRect(x, y, x-50, y + 50, squareTwo);
            cakeController.touched = false;
        }
    }

    //Getting for Cake Controller
    public CakeModel getCakeController() {
        return cakeController;
    }
}//class CakeView


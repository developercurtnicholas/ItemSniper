package project.major.itemsniper.Reusables;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import project.major.itemsniper.R;

/**
 * Created by Kurt on 4/17/2017.
 */
public class PieChart extends View {

    private Paint redPaint;
    private Paint bluePaint;
    private Paint greenPaint;
    private Context c;
    private int size = 100;
    private int r = 0;

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        init();
    }

    private void init(){
        this.bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.bluePaint.setStrokeWidth(20);
        this.bluePaint.setColor(getResources().getColor(R.color.pictonBlue));

        this.redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.redPaint.setStrokeWidth(20);
        this.redPaint.setColor(getResources().getColor(R.color.brightRed));

        this.greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.greenPaint.setStrokeWidth(20);
        this.greenPaint.setColor(getResources().getColor(R.color.shammrockGreen));
    }

    public void growShrink(int from, int to,int times,int speed){

        int s = speed;

        if(times <= 0){
            return;
        }else{
            int originalSize = size;
            //Grow
            for(int i = from; i < to;i++){
                this.size = i;
                this.r = i;
                try {
                    if(size == to/4){
                        s = 1;
                    }
                    if(size == to/3){
                        s = 2;
                    }
                    if(size == to/2){
                        s = 2;
                    }
                    Log.i("SPEED:",s+"");
                    Thread.sleep(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
            int grownSize = this.size;
            //Shrink
            for(int i = to;i > from;i--){
                this.size = i;
                try {
                    if(size == to/4){
                        s = 1;
                    }
                    if(size == to/3){
                        s = 2;
                    }
                    if(size == to/2){
                        s = 2;
                    }
                    Thread.sleep(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }

        growShrink(from,to,times-1,speed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int heigh = getMeasuredHeight();
        DisplayMetrics m = new DisplayMetrics();
        ((Activity) c).getWindowManager().getDefaultDisplay().getMetrics(m);
        float xPos =(float) m.widthPixels;
        float yPos = (float)m.heightPixels;

        int x = 100;
        int y = 100;

        canvas.drawLine(x+r,y+r,x+r,y+size+r,this.redPaint);
        canvas.drawLine(x+r,y,x+size,y,this.greenPaint);
        canvas.drawLine(x,y+size+r,x+size,y,this.bluePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingBottom() + getPaddingTop());

        float ww = (float)w-xpad;
        float hh = (float)h-ypad;

        float diameter = Math.min(ww,hh);
    }
}

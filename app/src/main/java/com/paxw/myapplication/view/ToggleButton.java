package com.paxw.myapplication.view;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.paxw.myapplication.R;
public class ToggleButton extends View{
    public ToggleButton(Context context) {
        super(context);
        init();
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setToggleButtonBackground(R.drawable.switch_background);
        setSlideButtonBackgroundResource(R.drawable.slide_button_background);
    }
    private  Bitmap mToggleBitmap;
    private  Bitmap  slideButtonBackgroundBitmap;
    private boolean currentState = false;
    private boolean isSliding = false;
    private  int currentX;
    private OnToggleButtonStateChangedListener listener;
    public void setToggleButtonBackground(int resid){
        mToggleBitmap = BitmapFactory.decodeResource(getResources(), resid);
    }
    public void setSlideButtonBackgroundResource(int slideButtonBackground) {
        slideButtonBackgroundBitmap = BitmapFactory.decodeResource(getResources(), slideButtonBackground);
    }
    public void setToggleState(boolean state) {
        currentState = state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mToggleBitmap.getWidth(), mToggleBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
       canvas.drawBitmap(mToggleBitmap, 0, 0, null);
        if (isSliding){
            int left = currentX - slideButtonBackgroundBitmap.getWidth()/2;
            int rightAlign = mToggleBitmap.getWidth() - slideButtonBackgroundBitmap.getWidth();
            if (left < 0){
                left = 0;
            }else if (left >rightAlign ){
                left = rightAlign;
            }

            canvas.drawBitmap(slideButtonBackgroundBitmap,left,0,null);
        }else{
            if (currentState){
                int left = mToggleBitmap.getWidth() - slideButtonBackgroundBitmap.getWidth();
                canvas.drawBitmap(slideButtonBackgroundBitmap,left,0,null);
            }else {
                canvas.drawBitmap(slideButtonBackgroundBitmap,0,0,null);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isSliding = true;
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                currentX = (int) event.getX();
                int center = mToggleBitmap.getWidth() / 2;
                boolean state = currentX > center;
                if (state != currentState && listener != null){
                    listener.onToggleButtonStateChanged();
                }
                currentState = state;
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
    public void setOnToggleButtonStateChangedListener(OnToggleButtonStateChangedListener listener){
        this.listener = listener;
    }
    public interface OnToggleButtonStateChangedListener{
        public void onToggleButtonStateChanged();
    }
    public boolean getCurrentState(){
        return currentState;
    }
}

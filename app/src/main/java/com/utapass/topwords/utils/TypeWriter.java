package com.utapass.topwords.utils;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.widget.EditText;


public class TypeWriter extends android.support.v7.widget.AppCompatEditText{
    private static int parentWidth;
    private int mOriginalTextSize;
    private int mMinTextSize;
    private CharSequence mText;
    private int mIndex;
    private long textDelay = 0; // in ms
    private long slideDelay = 2500 ;
    private final static int sMinSize = 5;
    private final static int paddingSize = 100;
    private Callback callback;
    private Boolean isOnTop = true;
    private int lstColor = 0;
    private Animation lstAnimation = null;
    private ResizeTextCallback resizeCallBack;

    public void setParentWidth(int parentWidth) {
        TypeWriter.parentWidth = parentWidth;
    }

    public interface Callback {
        void animationFinished();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface ResizeTextCallback {
        void onResizeCursor(float textSize);
    }

    public void setResizeCallback(ResizeTextCallback callback) {
        this.resizeCallBack = callback;
    }

    public TypeWriter(Context context) {
        super(context);
    }

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOriginalTextSize = (int) getTextSize();
        mMinTextSize =  sMinSize;
        setText("");
        setSelection(0);
        setFocusable(false);
        setSelected(false);
        clearFocus();
        setCursorVisible(false);
    }

    private Handler mHandler = new Handler();

    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, textDelay);
            }
            else {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callback.animationFinished();
                    }
                }, slideDelay);
            }
        }
    };

    public void animateText(CharSequence txt) {
        mText = txt;
        mIndex = 0;
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, textDelay);
    }

    public void setCharacterDelay(long m) {
        textDelay = m;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        resizeText(this, mOriginalTextSize, mMinTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resizeText(this, mOriginalTextSize, mMinTextSize);
    }

    private void resizeText(EditText textView, int originalTextSize, int minTextSize) {
        final Paint paint = textView.getPaint();
        int width = parentWidth;
        if (width == 0 || TextUtils.isEmpty(textView.getText())) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, originalTextSize);
        width = width - paddingSize;
        float ratio = width / paint.measureText(textView.getText().toString());
        if (ratio <= 1.0f) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    Math.max(minTextSize, originalTextSize * ratio));
        }
        resizeCallBack.onResizeCursor(textView.getTextSize());
    }

    public boolean getIsOnTop() {
        return isOnTop;
    }

    public void setIsOnTop(boolean isOnTop){
        this.isOnTop = isOnTop;
    }

    public void setLastColor(int color){
        this.lstColor = color;
    }

    public void setLastAnimation(Animation anim){
        this.lstAnimation = anim;
    }

    public int getLastColor() {
        return lstColor;
    }

    public Animation getLastAnimation() {
        return lstAnimation;
    }
}

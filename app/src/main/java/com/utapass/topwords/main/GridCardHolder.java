package com.utapass.topwords.main;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.utapass.topwords.R;
import com.utapass.topwords.utils.TypeWriter;
import com.utapass.topwords.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class GridCardHolder extends RecyclerView.ViewHolder implements TypeWriter.Callback,TypeWriter.ResizeTextCallback{

    private final ArrayList<String> words;
    private final int columnNumber;
    @BindView(R.id.first_edittext)
    TypeWriter typeWriter1;
    @BindView(R.id.second_edittext)
    TypeWriter typeWriter2;
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.custom_cursor1)
    ImageView imageView1;
    @BindView(R.id.custom_cursor2)
    ImageView imageView2;

    private Context context;
    int[] anim = {R.anim.slide_in_bottom, R.anim.slide_in_up, R.anim.slide_in_right, R.anim.slide_in_left};

    private String currentDisplayedStr = "";

    @OnTouch(R.id.first_edittext)
    public boolean onTypeWriter1Clicked(){
        searchWord(typeWriter1.getTag().toString());
        return true;
    }

    @OnTouch(R.id.second_edittext)
    public boolean onTypeWriter2Clicked() {
        searchWord(typeWriter2.getTag().toString());
        return true;
    }

    @Override
    public void animationFinished() {
        updateContent(words.get(generateRandomStringIndex()));
    }

    public GridCardHolder(Context context, View itemView, ArrayList<String> words, int columnNumber) {
        super(itemView);
        this.context = context;
        this.words = words;;
        this.columnNumber = columnNumber;
        ButterKnife.bind(this,itemView);
        setEditTextParam();
        setCursorAnim();
    }

    private void setEditTextParam() {
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params1.addRule(RelativeLayout.CENTER_VERTICAL);
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        typeWriter1.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                60);
        typeWriter1.setLayoutParams(params1);
        typeWriter2.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                60);
        typeWriter2.setLayoutParams(params1);
        typeWriter1.setParentWidth(calcWidth());
        typeWriter2.setParentWidth(calcWidth());
    }

    public void updateContent(String strings){
        if(typeWriter2.getIsOnTop()) {
            update(relativeLayout1,typeWriter1, typeWriter2, strings);
        }
        else {
            update(relativeLayout2,typeWriter2, typeWriter1, strings);
        }
    }

    private void update(RelativeLayout currentLayout, TypeWriter currentTypeWriter, TypeWriter beforeTypeWriter , String strings){
        currentDisplayedStr = strings;

        beforeTypeWriter.setIsOnTop(false);
        int color = setRandomColor(context, beforeTypeWriter.getLastColor());
        Animation animation = setSlideAnimation(beforeTypeWriter.getLastAnimation());
        currentTypeWriter.setIsOnTop(true);
        currentLayout.setBackgroundColor(color);
        currentLayout.setAnimation(animation);
        currentLayout.bringToFront();
        currentLayout.setAnimation(animation);
        currentTypeWriter.setLastAnimation(animation);
        currentTypeWriter.setLastColor(color);
        currentTypeWriter.setTag(strings);
        currentTypeWriter.animateText(strings);
        currentTypeWriter.setTextColor(Color.WHITE);
        currentTypeWriter.setCallback(this);
        currentTypeWriter.setCharacterDelay(300);
        currentTypeWriter.setResizeCallback(this);

    }

    private Animation setSlideAnimation(Animation lstAnimIndex){
       return generateRandomAnimation(anim, lstAnimIndex);
    }

    private int setRandomColor(Context context, int lstColor){
        int[] colors = context.getResources().getIntArray(R.array.colors);
        return generateRandomColor(colors,lstColor);
    }

    private int generateRandomColor(int[] colors , int lstColor){
        Random r = new Random();
        int colorIndex = r.nextInt(colors.length);
        while (lstColor == colors[colorIndex]){
            colorIndex = r.nextInt(colors.length );
        }
        return colors[colorIndex];
    }

    private Animation generateRandomAnimation(int[] list, Animation lstAnim){
        Random r = new Random();
        int index = r.nextInt(list.length);
        while (lstAnim == AnimationUtils.loadAnimation(context, anim[index])){
            index = r.nextInt(list.length);
        }
        return AnimationUtils.loadAnimation(context, anim[index]);
    }

    private int generateRandomStringIndex(){
        Random r = new Random();
        return r.nextInt(words.size() - 1);
    }

    private void searchWord(String searchWords)
    {
        Intent browserIntent = new Intent(Intent.ACTION_WEB_SEARCH);
        browserIntent.putExtra(SearchManager.QUERY, searchWords);
        context.startActivity(browserIntent);
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setCursorAnim(){
        int width = (int) context.getResources().getDimension(R.dimen.text_cursor_default_width);
        int height = (int) context.getResources().getDimension(R.dimen.text_cursor_default_height);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width, height);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(width, height);
        params1.addRule(RelativeLayout.CENTER_VERTICAL);
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params2.addRule(RelativeLayout.CENTER_VERTICAL);
        params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if (Utils.isRtlString(currentDisplayedStr)) {
            params1.addRule(RelativeLayout.LEFT_OF, R.id.first_edittext);
            params2.addRule(RelativeLayout.LEFT_OF, R.id.second_edittext);
        } else {
            params1.addRule(RelativeLayout.RIGHT_OF, R.id.first_edittext);
            params2.addRule(RelativeLayout.RIGHT_OF, R.id.second_edittext);
        }
        imageView1.setLayoutParams(params1);
        imageView2.setLayoutParams(params2);
        Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.fade_in_out);
        animation2.setFillAfter(true);
        imageView1.startAnimation(animation2);
        imageView2.startAnimation(animation2);
    }

    @Override
    public void onResizeCursor(float max) {

        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, max, context.getResources().getDisplayMetrics());
        if (typeWriter2.getIsOnTop()) {
            ViewGroup.LayoutParams params2 = imageView2.getLayoutParams();
            params2.height = (int) pixels;
            imageView2.setLayoutParams(params2);
        }else{
            ViewGroup.LayoutParams params = imageView1.getLayoutParams();
            params.height = (int) pixels;
            imageView1.setLayoutParams(params);
        }
    }

    private int calcWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if(columnNumber > 0 )
            return (width / columnNumber);
        return width;
    }
}

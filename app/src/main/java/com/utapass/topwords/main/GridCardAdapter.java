package com.utapass.topwords.main;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utapass.topwords.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class GridCardAdapter extends RecyclerView.Adapter<GridCardHolder> {

    private final Context context;
    private final int columnNumber;
    private final ArrayList<String> words;
    private final int rowNumber;

    public GridCardAdapter(Context context, ArrayList<String> countryList, int columnNumber, int rowNumber) {
        this.context = context;
        this.words = countryList;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }

    @Override
    public GridCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gridcard_item, parent, false);
        return new GridCardHolder(context, view, words, columnNumber);
    }

    @Override
    public void onBindViewHolder(GridCardHolder holder, int position) {
        holder.updateContent(words.get(new Random().nextInt(words.size())));
        setHeight(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return columnNumber * rowNumber;
    }

    private void setHeight(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = calcHeight();
        view.setLayoutParams(params);
    }

    private int calcHeight() {
        final DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Method mGetRawH;
        int realHeight = context.getResources().getDisplayMetrics().heightPixels;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(metrics);
                realHeight = metrics.heightPixels;
            } else {
                mGetRawH = Display.class.getMethod("getRawHeight");
                realHeight = (Integer) mGetRawH.invoke(display);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (rowNumber > 0) {
            return realHeight / rowNumber;
        }
        return realHeight;
    }
}

package com.utapass.topwords.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.dragselectrecyclerview.IDragSelectAdapter;
import com.utapass.topwords.R;

public class CustomGridDialogAdapter extends RecyclerView.Adapter<CustomGridDialogHolder> implements IDragSelectAdapter {
    private final int number;
    private final Context context;
    public static int selectedIndices;
    private CustomGridDialogHolder.Listener callback;
    private CustomGridDialogHolder dialogHolder;

    public CustomGridDialogAdapter(Context context, int columnNumber, CustomGridDialogHolder.Listener callback) {
        this.context = context;
        this.number = columnNumber;
        this.selectedIndices = -1;
        this.callback = callback;
    }

    @Override
    public CustomGridDialogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_layout, parent, false);
        return dialogHolder=new CustomGridDialogHolder(view,callback);
    }

    @Override
    public void onBindViewHolder(CustomGridDialogHolder holder, int position) {
        dialogHolder.updateColor(holder,position);
    }

    @Override
    public void setSelected(int index, boolean selected) {
        notifyItemsIsChanged(index);
    }

    @Override
    public boolean isIndexSelectable(int index) {
        return true;
    }

    @Override
    public int getItemCount() {
        return number * number;
    }

    public int getSelectedIndices() {
        return selectedIndices;
    }

    public void toggleSelected(int index) {
        notifyItemsIsChanged(index);
    }

    private void notifyItemsIsChanged(int index) {
        if (selectedIndices == index) {
            selectedIndices = -1;
        } else {
            selectedIndices = index;
        }

        notifyItemRangeChanged(0,25);
        if (callback != null) {
            callback.onSelectionChanged(selectedIndices+1);
        }
    }

}

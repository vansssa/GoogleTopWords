package com.utapass.topwords.utils;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.utapass.topwords.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomGridDialogHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    @BindView(R.id.dialog_cell)
    ImageView dialogCell;


    private final Listener callback;

    public CustomGridDialogHolder(View itemView, Listener callback) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.itemView.setOnClickListener(this);
        this.itemView.setOnLongClickListener(this);
        this.itemView.setOnTouchListener(this);
        this.callback = callback;
    }


    @Override
    public void onClick(View view) {
        if (callback != null) {
            callback.onClick(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (callback != null) {
            callback.onTouchDown(getAdapterPosition());
        }
        return true;
    }

    public void updateColor(CustomGridDialogHolder holder, int position) {
        int[] index_result = {CustomGridDialogAdapter.selectedIndices/5, CustomGridDialogAdapter.selectedIndices %5};
        int[] position_result = {position / 5 , position % 5 };

        if(position_result[0]<= index_result[0] && position_result[1] <= index_result[1])
            holder.dialogCell.setBackgroundResource(R.drawable.select_border);
        else {
            holder.dialogCell.setBackgroundResource(R.drawable.border);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN: {
                if (callback != null) {
                    callback.onTouchDown(getAdapterPosition());
                }
                break;
            }
        }
        return true;
    }

    interface Listener {
        void onClick(int index);

        void onTouchDown(int index);

        void onSelectionChanged(int count);
    }
}

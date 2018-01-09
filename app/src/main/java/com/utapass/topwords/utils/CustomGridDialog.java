package com.utapass.topwords.utils;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.utapass.topwords.R;
import com.utapass.topwords.main.MainFragmentPresenterIml;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CustomGridDialog extends DialogFragment implements View.OnClickListener , CustomGridDialogHolder.Listener{

    private Unbinder unbinder;

    @BindView(R.id.dialog_grid_layout)
    DragSelectRecyclerView gridLayout;
    @BindView(R.id.confirm)
    Button confirm;

    private MainFragmentPresenterIml mainFragmentPresenter;
    private CustomGridDialogAdapter adapter;

    public CustomGridDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFragmentPresenter = new MainFragmentPresenterIml(getFragmentManager().findFragmentById(R.id.main_fragment));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_grid_layout, null);
        unbinder = ButterKnife.bind(this,dialogView);
        initUI();

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setView(dialogView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    public void initUI(){
        gridLayout.setLayoutManager(new GridLayoutManager(getContext(), 5));
        adapter = new CustomGridDialogAdapter(getContext(), 5,this);
        gridLayout.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.confirm)
    public void onClick(View view) {
        ArrayList<String> result = mainFragmentPresenter.getTopWords(mainFragmentPresenter.targetCountry);
        mainFragmentPresenter.updateString(result);
        dismiss();
    }

    @Override
    public void onClick(int index) {
        adapter.toggleSelected(index);
    }

    @Override
    public void onTouchDown(int index) {
        gridLayout.setDragSelectActive(true, index);
    }

    @Override
    public void onSelectionChanged(int count) {
        if (count > 0) {
            int[] result = {adapter.getSelectedIndices()%5,adapter.getSelectedIndices()/5};
            mainFragmentPresenter.columnNumber = result[0]+1;
            mainFragmentPresenter.rowNumber = result[1]+1 ;
            confirm.setBackground(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        }
        else {
            confirm.setBackground(new ColorDrawable(getResources().getColor(R.color.dialog_confirm_button)));
        }

    }
}

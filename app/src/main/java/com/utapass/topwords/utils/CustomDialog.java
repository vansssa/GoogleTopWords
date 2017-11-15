package com.utapass.topwords.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.utapass.topwords.R;
import com.utapass.topwords.main.MainFragmentPresenterIml;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CustomDialog extends DialogFragment implements View.OnClickListener {

    private Unbinder unbinder;

    @BindView(R.id.seekBar_X)
    SeekBar seekBarX;
    @BindView(R.id.seekBar_Y)
    SeekBar seekBarY;
    @BindView(R.id.textview_x)
    TextView textViewX;
    @BindView(R.id.textview_y)
    TextView textViewY;
    @BindView(R.id.confirm)
    Button confirm;

    private MainFragmentPresenterIml mainFragmentPresenter;

    public CustomDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainFragmentPresenter = new MainFragmentPresenterIml(getFragmentManager().findFragmentById(R.id.main_fragment));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_relayout, null);
        unbinder = ButterKnife.bind(this,dialogView);
        mBuilder.setView(dialogView);
        mBuilder.setTitle(R.string.relayout_tittle);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        textViewX.setText(String.valueOf(MainFragmentPresenterIml.columnNumber));
        textViewY.setText(String.valueOf(MainFragmentPresenterIml.rowNumber));
        seekBarX.setProgress(MainFragmentPresenterIml.columnNumber);
        seekBarY.setProgress(MainFragmentPresenterIml.rowNumber);
        confirm.setOnClickListener(this);
        seekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 0){
                    seekBarX.setProgress(1);
                    return;
                }
                textViewX.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainFragmentPresenterIml.columnNumber = seekBar.getProgress();
            }
        });
        seekBarY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 0) {
                    seekBarY.setProgress(1);
                    return;
                }
                textViewY.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MainFragmentPresenterIml.rowNumber = seekBar.getProgress();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> result = mainFragmentPresenter.getTopWords(mainFragmentPresenter.targetCountry);
        mainFragmentPresenter.updateString(result);
        dismiss();
    }
}

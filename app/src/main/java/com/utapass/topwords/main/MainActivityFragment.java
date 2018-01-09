package com.utapass.topwords.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.utapass.topwords.R;
import com.utapass.topwords.utils.CustomGridDialog;
import com.utapass.topwords.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivityFragment extends Fragment implements MainFragmentView {

    private static final String STATE_COUNTRY = "currentCountry";
    private  String CUSTOM_DIALOG = "CUSTOM_DIALOG";
    @BindView(R.id.grid_recyclerview)
    RecyclerView recyclerView;

    private final MainFragmentPresenterIml mainFragmentPresenter;
    private Unbinder unbinder;
    private AlertDialog dialog;


    public MainActivityFragment() {
        mainFragmentPresenter = new MainFragmentPresenterIml(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_COUNTRY, mainFragmentPresenter.targetCountry);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
            mainFragmentPresenter.targetCountry = savedInstanceState.getString(STATE_COUNTRY);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Utils.isNetWorkConnected(getContext())) {
            mainFragmentPresenter.getAllWords();
        }
        else
            openAlertDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void initUI(ArrayList<String> strings) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mainFragmentPresenter.columnNumber));
        recyclerView.setAdapter(new GridCardAdapter(getContext(), strings ,mainFragmentPresenter.columnNumber , mainFragmentPresenter.rowNumber));
        Utils.fullScreen(((Activity)getContext()).getWindow());
    }

    @Override
    public void openAlertDialog() {
        dialog = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.no_network))
                .setMessage(getResources().getString(R.string.no_network_desc))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                }).show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_relayout){

            CustomGridDialog customDialog = new CustomGridDialog();
            customDialog.show(getFragmentManager(),CUSTOM_DIALOG);
        }
        else {
            mainFragmentPresenter.targetCountry = mainFragmentPresenter.getCountryNo(item.getTitle().toString().toUpperCase());
            ArrayList<String> result = mainFragmentPresenter.getTopWords(mainFragmentPresenter.targetCountry);
            mainFragmentPresenter.updateString(result);
            setTittle(item.getTitle().toString().toUpperCase());
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTittle(String string) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(string);
    }

}

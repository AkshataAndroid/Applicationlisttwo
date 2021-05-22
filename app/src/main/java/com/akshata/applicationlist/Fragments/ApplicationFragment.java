package com.akshata.applicationlist.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

import com.akshata.applicationlist.R;

public class ApplicationFragment extends Fragment {

    private FragmentTabHost tabHost;
    Context context=getContext();
    private Object Bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabHost = new FragmentTabHost(getActivity());
        tabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_application);

        Bundle arg1 = new Bundle();
        arg1.putInt("USER", 1);

        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("USER"),
                UserInstalledappFragment.class, arg1);

        Bundle arg2 = new Bundle();
        arg2.putInt("SYSTEM", 2);



        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("SYSTEM"),
                SystemInstalledappFragment.class, arg2);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                   // tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#636363")); // unselected
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));
                }

              //  tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF")); // selected
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#0088FF"));

            }
        });

        return tabHost;
    }
}
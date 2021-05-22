package com.akshata.applicationlist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.akshata.applicationlist.Fragments.AnalyticsFragment;
import com.akshata.applicationlist.Fragments.ApplicationFragment;
import com.akshata.applicationlist.R;
import com.akshata.applicationlist.Service.SocketService;

public class MainActivity  extends AppCompatActivity {
Context context;
    final SocketService mSocketservice = new SocketService();

    BottomNavigationView bottomNavigationView;
    Context con=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SocketConnectionService.instance().Expermemnt(this);
//        SocketService.instance().Expermemnt(this);
//        if(SocketService.instance().isConnected == true){
//            Toast.makeText(MainActivity.this,"Socket  connected",Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(MainActivity.this,"Socket not able connect",Toast.LENGTH_SHORT).show();
//        }
       // mSocketservice.stream.connect();
      //  SocketService.instance().startService(Sock);


        //  toolbar = getSupportActionBar();
        bottomNavigationView= findViewById(R.id.navigation);

        // load the store fragment by default
//        toolbar.setTitle("Shop");
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new ApplicationFragment());
        //database

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_analytics:
                    fragment = new AnalyticsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_app:
                    fragment = new ApplicationFragment();
                    loadFragment(fragment);
                    return true;

            }

            return false;
        }
    };

    private boolean loadFragment(Fragment fragment) {
        // load fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
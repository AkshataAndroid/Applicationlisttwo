package com.akshata.applicationlist.Service;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.akshata.applicationlist.Constants.Const;
import com.akshata.applicationlist.Fragments.SystemInstalledappFragment;
import com.akshata.applicationlist.Preferances.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class SocketService extends Service {
    public static Socket stream = null;
    public Boolean isConnected = false;
    public static String apiUrl = null;
    static SocketService socketService;

    private static final SocketService mSocket = new SocketService();


    public static SocketService instance() {
        return mSocket;
    }

    public void Expermemnt(Activity mainActivity) {
        //   Toast.makeText(mainActivity, "Hello there", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction().equals("START")) {
            socketService = new SocketService();

            Log.i("SocketService", "started Socket service");

            String apiStatus = UserPreferences.getPreferences(getApplicationContext(), Const.KEY_API_URL);
            if (apiStatus == null) {
                apiUrl = SocketConnectionService.instance().api();
                UserPreferences.setPreferences(getApplicationContext(), Const.KEY_API_URL, apiUrl);
            } else {
                apiUrl = apiStatus;
            }
            attachLifecycleListeners();
            socketConnections();

        }
        socketConnections();
        return START_STICKY;
    }
    public void clearActivity(Activity activity) {
        Activity current = SocketConnectionService.instance().getActivity();
        if (current == activity) {
           // LogDetail.LogD("CobrowseService", "clearing active activity " + activity);
            SocketConnectionService.instance().setActivity(null);
        }
    }
    public void setActivity(Activity activity) {
        SocketConnectionService.instance().setActivity(activity);
    }


    private void attachLifecycleListeners() {
        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Log.i("SocketService", "activity created " + activity);
            }

            public void onActivityStarted(Activity activity) {
                Log.i("SocketService", "activity started " + activity);
            }

            public void onActivityResumed(Activity activity) {
                Log.i("SocketService", "activity resumed " + activity);
                SocketService.this.setActivity(activity);
            }

            public void onActivityPaused(Activity activity) {
                Log.i("SocketService", "activity paused " + activity);
                SocketService.this.clearActivity(activity);
            }

            public void onActivityStopped(Activity activity) {
                Log.i("SocketService", "activity stopped " + activity);
                SocketService.this.clearActivity(activity);
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.i("SocketService", "activity save " + activity);
            }

            public void onActivityDestroyed(Activity activity) {
                Log.i("SocketService", "activity destroy " + activity);
                SocketService.this.clearActivity(activity);
            }
        });
    }

    public void socketConnections() {
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = false;
            opts.reconnection = true;
            opts.reconnectionDelay = 1000;
            opts.reconnectionDelayMax = 5000;
            opts.reconnectionAttempts = 99999;
            stream = IO.socket(apiUrl, opts);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        stream.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.d("Socket Connected", stream.toString());
                if (!isConnected) {
                    isConnected = true;
                    sendToServer();
                }


            }
        });

        stream.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Socket disconnected", stream.toString());

                isConnected = false;
            }
        });

        stream.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Socket not able connect", stream.toString());

            }
        });

        stream.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("Socket connect timeout", stream.toString());


            }
        });

        stream.on("response", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject maindata = (JSONObject) args[0];
                


            }
        });

        stream.connect();
    }



    private void sendToServer() {
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        final String systemPackageName = intent.getStringExtra(SystemInstalledappFragment.VERSION_NAME);
                        final String systemAppName=intent.getStringExtra(SystemInstalledappFragment.APP_NAME);
                        final String userPackageName = intent.getStringExtra(SystemInstalledappFragment.VERSION_NAME);
                        final String userAppName=intent.getStringExtra(SystemInstalledappFragment.APP_NAME);
                       final  Bitmap bitmap = (Bitmap)intent.getParcelableExtra("image");

                        Timer t = new Timer();
                        //Set the schedule function and rate
                        t.scheduleAtFixedRate(new TimerTask() {
                                                  @Override
                                                  public void run() {
                                                      //Called each time when 3000 milliseconds (3 second) (the period parameter)


                                                      JSONObject applicationDetail = new JSONObject();
                                                      try {
                                                          applicationDetail.put("systempackageName", systemPackageName);
                                                          applicationDetail.put("systemappName", systemAppName);
                                                          applicationDetail.put("useropackageName",userPackageName);
                                                          applicationDetail.put("userappname",userAppName);
                                                          applicationDetail.put("image",bitmap);


                                                      } catch (JSONException e) {
                                                          e.printStackTrace();
                                                      }



                                                      stream.emit("sending", applicationDetail);
                                                  }
                                              },
                                //Set how long before to start calling the TimerTask (in milliseconds)
                                0,
                                //Set the amount of time between each execution (in milliseconds)
                                30000);


                        //  timelyupdate

                    }
                }, new IntentFilter(SystemInstalledappFragment.ACTION_LOCATION_BROADCAST)
        );


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

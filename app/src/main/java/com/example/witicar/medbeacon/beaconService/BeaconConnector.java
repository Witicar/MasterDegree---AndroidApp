package com.example.witicar.medbeacon.beaconService;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.witicar.medbeacon.R;
import com.example.witicar.medbeacon.models.Visit;
import com.example.witicar.medbeacon.resources.RegistrationResource;
import com.example.witicar.medbeacon.retrofitConfiguration.RetrofitConfiguration;
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BeaconConnector extends IntentService {

    private ProximityManager proximityManager;
    private List<Integer> timeVisitList = new ArrayList<>();
    private int fakeTime;

    public BeaconConnector() {
        super("BeaconConnector");
    }

    public ProximityManager getProximityManager() {
        return proximityManager;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        KontaktSDK.initialize("MedBeaconKey");
        proximityManager = ProximityManagerFactory.create(getApplicationContext());
        proximityManager.setIBeaconListener(createIBeaconListener());
        startScanning();

        Retrofit retrofit = RetrofitConfiguration.startRetrofit();
        RegistrationResource registrationResource = retrofit.create(RegistrationResource.class);

        Call<List<Visit>> call = registrationResource.getVisites(intent.getStringExtra("login"));
        call.enqueue(new Callback<List<Visit>>() {
            @Override
            public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                for(Visit v : response.body())
                    timeVisitList.add(v.getVisitTime());
            }

            @Override
            public void onFailure(Call<List<Visit>> call, Throwable t) {

            }
        });

        fakeTime = Integer.parseInt(intent.getStringExtra("fakeTime"));
    }

    public void startScanning() {

        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {

            }

            @Override
            public void onIBeaconLost(IBeaconDevice ibeacon, IBeaconRegion region) {
                for(Integer i : timeVisitList) {
                    if (i > fakeTime)
                        if((i - fakeTime) <= 15) {
                        showNotification("Proszę podejść do gabinetu lekarza, za 15 minut następuje wizyta");
                        break;
                    }
                }
            }
        };
    }

    private void showNotification(String text)

    {
        int notifyID = 1;
        String CHANNEL_ID = "medBeacon_channel_1";
        CharSequence name = getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

                notification.setSmallIcon(R.drawable.ic_launcher_background);
                notification.setContentTitle(getString(R.string.app_name));
                notification.setAutoCancel(true);
                notification.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text));
                notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notification.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
                notification.setContentText("Przychodnia");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification.setChannelId(CHANNEL_ID);
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(0, notification.build());

    }
}

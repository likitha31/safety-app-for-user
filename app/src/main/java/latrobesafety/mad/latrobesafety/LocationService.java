package latrobesafety.mad.latrobesafety;


import android.annotation.SuppressLint;
/*import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;*/
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
//import android.os.Build;

import android.os.IBinder;
import android.os.Looper;
//import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocationService extends Service {

    private String mLastUpdateTime = null;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location lastLocation;

    private FirebaseFirestore db; //Firestore
    private String docId;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;

    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 200;


    public class LocalBinder extends Binder {
        LocationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocationService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LOCATION GET DURATION", "start in service");
        docId = intent.getStringExtra("ID");
        db = FirebaseFirestore.getInstance();
        init();

        return START_STICKY;
    }

    public Location getLocation() {

        return lastLocation;
    }

    /**
     * Initialize Location Apis
     * Create Builder if Share location true
     */

    @SuppressLint("RestrictedApi")
    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                receiveLocation(locationResult);
            }
        };

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        startLocationUpdates();
    }

    /**
     * Request Location Update
     */

    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    /**
     * onLocationResult
     * on Receive Location
     */
    private void receiveLocation(LocationResult locationResult) {

        //update location to firestore
        lastLocation = locationResult.getLastLocation();
        String lat= lastLocation.getLatitude()+"";
        String lon= lastLocation.getLongitude()+"";

        DocumentReference request = db.collection("Request").document(docId);
        request.update("lat",lat,"lon",lon);
        //request.update("lon",lon);
        Log.d("UPDATE" , lastLocation.toString());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    /**
     * Remove Location Update
     */
    public void stopLocationUpdates() {
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback);

    }


    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }
}
package com.example.newagesafety;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class mainsos extends AppCompatActivity {
    Button b1;
    Button emergency;
    DatabaseHandler mydb;
    //private LocationSettingsRequest.Builder builder;
    String x,y;
    private static final int REQUEST_LOCATION=1;
    LocationManager locationManager;
    private LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsos);
        b1 = findViewById(R.id.addcontact);
        emergency= findViewById(R.id.emergency);
        mydb= new DatabaseHandler(this);
        final MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.emergency_alarm);
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //for add contact button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(mainsos.this,Register.class);
                startActivity(i1);
                Toast.makeText(mainsos.this, "Moved to Register", Toast.LENGTH_SHORT).show();
            }
        });

        //for emergency button
        ActivityCompat.requestPermissions(this,new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);


        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            onGPS();
        }
        else {
            startTrack();
        }

        emergency.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mp.start();
                Toast.makeText(mainsos.this, "Panic button pressed", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        ArrayList<String> theList=new ArrayList<>();
        Cursor cursor=mydb.getText();
        String msg="";


        if(cursor.getCount()==0)
        {
            Toast.makeText( mainsos.this, "No current Content found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            msg="NEED HELP LATITUDE: "+x+" LONGITUDE: "+y;
            String number="";

            while(cursor.moveToNext())
            {
                theList.add(cursor.getString(1));
                number=number+cursor.getString(1)+(cursor.isLast()?"":";");

            }

            if (!theList.isEmpty()) {
                callPolice();
                call(theList);
                sendSMS(number,msg);
            }
        }
    }

    private void callPolice() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+100));

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
        }
    }

    private void sendSMS(String number, String msg) {

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.putExtra("sms_body", msg);
        smsIntent.setData(Uri.parse("smsto:" + number));
        startActivity(smsIntent);
    }


    private void call(ArrayList<String> numbers) {

        for (String number:numbers) {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + number));
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(i);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }
        }
    }

    private void startTrack() {
        if(ActivityCompat.checkSelfPermission(mainsos.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainsos.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

            //ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            ActivityCompat.requestPermissions(this,new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
        {
            Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(location!=null)
            {
                double lat=location.getLatitude();
                double lon=location.getLongitude();
                x=String.valueOf(lat);
                y=String.valueOf(lon);
            }
            else if(locationNetwork!=null)
            {
                double lat=locationNetwork.getLatitude();
                double lon=locationNetwork.getLongitude();
                x=String.valueOf(lat);
                y=String.valueOf(lon);
            }
            else if(locationPassive!=null)
            {
                double lat=locationPassive.getLatitude();
                double lon=locationPassive.getLongitude();
                x=String.valueOf(lat);
                y=String.valueOf(lon);
            }
            else
            {
                Toast.makeText(this,"Unable to find location",Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void onGPS() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("GPS Enabled").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    }



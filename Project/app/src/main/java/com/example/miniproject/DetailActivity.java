package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniproject.databinding.ActivityDetailBinding;

import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    int count=1;
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final DBHelper helper = new DBHelper(this);

        if(getIntent().getIntExtra("type", 0)==1) {

            final int image = getIntent().getIntExtra("image", 0);
            final int price = Integer.parseInt(getIntent().getStringExtra("price"));
            final String name = getIntent().getStringExtra("name");
            final String description = getIntent().getStringExtra("desc");

            binding.detailimage.setImageResource(image);
            binding.priceLbl.setText(String.format("%d", price));
            binding.prodLbl.setText(name);
            binding.detailDescription.setText(description);

            if(ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(DetailActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
                }, 100);
            }

            binding.locBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getLocation();
                }
            });

            binding.minusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=Integer.parseInt(binding.quantity.getText().toString());
                    if(count<=1) count=1;
                    else count--;
                    binding.quantity.setText("" + count);
                }
            });

            binding.plusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=Integer.parseInt(binding.quantity.getText().toString());
                    count++;
                    binding.quantity.setText("" + count);
                }
            });

            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInserted = helper.insertOrder(
                            binding.nameBox.getText().toString(),
                            binding.phoneBox.getText().toString(),
                            binding.mailBox.getText().toString(),
                            binding.addressBox.getText().toString(),
                            price,
                            image,
                            Integer.parseInt(binding.quantity.getText().toString()),
                            name
                    );
                    if (isInserted)
                        Toast.makeText(DetailActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(DetailActivity.this, "Error: Order Could not be placed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            int id=getIntent().getIntExtra("id",0);
            Cursor cursor=helper.getOrderById(id);
            final int image=cursor.getInt(6);
            binding.detailimage.setImageResource(cursor.getInt(6));
            binding.priceLbl.setText(String.format("%d", cursor.getInt(5)));
            binding.quantity.setText(String.format("%d", cursor.getInt(7)));
            binding.prodLbl.setText(cursor.getString(8));
            binding.detailDescription.setText("");

            binding.minusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=Integer.parseInt(binding.quantity.getText().toString());
                    if(count<=1) count=1;
                    else count--;
                    binding.quantity.setText("" + count);
                }
            });

            binding.plusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count=Integer.parseInt(binding.quantity.getText().toString());
                    count++;
                    binding.quantity.setText("" + count);
                }
            });

            binding.nameBox.setText(cursor.getString(1));
            binding.phoneBox.setText(cursor.getString(2));
            binding.mailBox.setText(cursor.getString(3));
            binding.addressBox.setText(cursor.getString(4));
            binding.insertBtn.setText("Update Order");
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isUpdated=helper.updateOrder(
                            binding.nameBox.getText().toString(),
                            binding.phoneBox.getText().toString(),
                            binding.mailBox.getText().toString(),
                            binding.addressBox.getText().toString(),
                            Integer.parseInt(binding.priceLbl.getText().toString()),
                            image,
                            Integer.parseInt(binding.quantity.getText().toString()),
                            binding.prodLbl.getText().toString(),
                            id
                    );
                    if(isUpdated){
                        Toast.makeText(DetailActivity.this, "Order Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(DetailActivity.this, "Error updating your order", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try{
            locationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, DetailActivity.this);
        }catch(Exception e) {
            Toast.makeText(DetailActivity.this, "Error getting location... Please try checking your location services", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        try{
            Geocoder geocoder=new Geocoder(DetailActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            binding.addressBox.setText(address);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
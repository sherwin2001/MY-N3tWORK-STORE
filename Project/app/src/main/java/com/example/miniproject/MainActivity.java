package com.example.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.miniproject.Adapters.MainAdapter;
import com.example.miniproject.Models.MainModel;
import com.example.miniproject.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<MainModel> list=new ArrayList<>();

        list.add(new MainModel(R.drawable.wifi, "Wifi Router", "2199", "Enjoy ultra-fast speeds, smooth streaming, and lower latency for 4K streaming, gaming, and video conferencing."));
        list.add(new MainModel(R.drawable.fibre_optic, "Fibre-Optic Cable (1 M)", "10", "Highest rate of data transmission without any interruption !!"));
        list.add(new MainModel(R.drawable.switch1, "Switch", "1499", "Connect devices in a network and use packet switching to transmit data over network."));
        list.add(new MainModel(R.drawable.cat_cable, "Cat6 Cable (1 M)", "25", "A standardized twisted pair cable for Ethernet and other network physical layers"));
        list.add(new MainModel(R.drawable.rj45, "RJ45 Connector (15 pcs)", "50", "Used to connect computers onto Ethernet-based local area networks (LAN)"));
        list.add(new MainModel(R.drawable.usbhub, "USB Hub", "999", "Expands single USB port into several to make more ports available to connect devices to a host"));
        list.add(new MainModel(R.drawable.coaxial_cable, "Coaxial Cable (1 M)", "20", "Used as a transmission line for radio frequency signals. "));

        MainAdapter adapter=new MainAdapter(list, this);
        binding.recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerview.setLayoutManager(layoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.orders:
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
package com.zht.hermesuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import xiaofei.library.hermes.Hermes;
import xiaofei.library.hermes.HermesService;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView textView = findViewById(R.id.tv_send);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        Hermes.connect(this, HermesService.HermesService0.class);
    }

    public void send(){
        IUserManager userManager = Hermes.getInstance(IUserManager.class);
        Toast.makeText(getApplicationContext(), "  "+userManager.getName(), Toast.LENGTH_SHORT).show();
    }
}

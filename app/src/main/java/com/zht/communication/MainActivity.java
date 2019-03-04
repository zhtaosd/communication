package com.zht.communication;

import android.content.Intent;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zht.communication.core.Hermes;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_jump);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });
//        EventBus.getInstance().register(this);
        Hermes.getDefault().init(this);
        Hermes.getDefault().register(UserManager.class);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public  void handle(Friend friend){
        Toast.makeText(this,friend.toString(),Toast.LENGTH_LONG).show();
    }
}

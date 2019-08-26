package com.example.lenovo.intentserviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);

        Button sendButton = (Button) findViewById(R.id.button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString();
                Intent inputIntent = new Intent(MainActivity.this,
                        MyIntentService.class);
                inputIntent.putExtra(MyIntentService.TEXT_INPUT, inputText);
                startService(inputIntent);
            }
        });
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String LOCAL_ACTION =
                "com.example.myintentserviceapp.intent_service.ALL_DONE";

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView outputTextView = (TextView) findViewById(R.id.textView);
            String outputText = intent.getStringExtra(MyIntentService.TEXT_OUTPUT);
            outputTextView.setText(outputText);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter broadcastFilter = new IntentFilter(
                ResponseReceiver.LOCAL_ACTION);
        receiver = new ResponseReceiver();
        LocalBroadcastManager localBroadcastManager =
                LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver,
                broadcastFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager localBroadcastManager =
                LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }
}

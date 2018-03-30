package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private Button computeButton;
    private Button addButton;
    private EditText nextTermEditText;
    private TextView allTermsTextView;
    private Integer sum = -1;
    private String prevAllTerms = "";
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    ComputeButtonListener computeButtonListener = new ComputeButtonListener();
    protected class ComputeButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent toSecondaryIntent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
            String allTerms = allTermsTextView.getText().toString();
            if (prevAllTerms == "")
                prevAllTerms = allTerms;
            else {
                if (prevAllTerms.equals(allTerms)) {
                    Log.d("ALL TERMS", sum.toString());
                    return;
                }
            }
            toSecondaryIntent.putExtra("ALL_TERMS", allTerms);
            startActivityForResult(toSecondaryIntent, 1);
        }
    }
    ButtonListener buttonListener = new ButtonListener();
    protected class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("debug", "in");
            if (nextTermEditText.getText() != null) {

                Integer nextNumber = Integer.valueOf(nextTermEditText.getText().toString());
                if (allTermsTextView.getText() != null)
                    allTermsTextView.setText(allTermsTextView.getText().toString() + "+" + nextNumber);
                else
                    allTermsTextView.setText(nextNumber.toString());
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        addButton = findViewById(R.id.addButton);
        computeButton = findViewById(R.id.computeButton);
        nextTermEditText = findViewById(R.id.nextTerm);
        allTermsTextView = findViewById(R.id.allTerms);

        addButton.setOnClickListener(buttonListener);
        computeButton.setOnClickListener(computeButtonListener);

        if (savedInstanceState != null && savedInstanceState.getInt("SUM") != -1)
            sum = savedInstanceState.getInt("SUM");

        intentFilter.addAction("BROADCAST");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("SUM", sum);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getInt("SUM") != -1)
            sum = savedInstanceState.getInt("SUM");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            Toast.makeText(this, "Returned with result " + resultCode, Toast.LENGTH_LONG).show();
            sum = resultCode;
            if (sum > 10) {
                Log.d("HELP", "SERVICE");
                Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                serviceIntent.setAction("START_SERVICE");
                serviceIntent.putExtra("SUMA", sum);
                startService(serviceIntent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var05Service.class);
        stopService(intent);
        super.onDestroy();
    }
}

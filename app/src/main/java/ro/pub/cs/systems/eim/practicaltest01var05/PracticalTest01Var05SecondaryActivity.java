package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    private Button returnButton;
    private EditText resultEditText;

    ReturnButtonListener returnButtonListener = new ReturnButtonListener();
    protected class ReturnButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String[] terms = resultEditText.getText().toString().split("\\+");
            int sum = 0;
            for (int i = 0;i < terms.length; ++i)
                sum += Integer.parseInt(terms[i]);
            setResult(sum);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        returnButton = findViewById(R.id.returnButton);
        resultEditText = findViewById(R.id.result);

        returnButton.setOnClickListener(returnButtonListener);
        Intent intent = getIntent();
        if (intent != null) {
            resultEditText.setText(intent.getStringExtra("ALL_TERMS"));
        }

    }

}

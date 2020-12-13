package edu.cs570.lindiechenou.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    float tip;
    //int tip;
    double tiptotal, totalbill;
    DecimalFormat df = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDisplayButton();

    }
    private void initDisplayButton() {
        Button displayButton = findViewById(R.id.button);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editTextName);
                String bill = editText.getText().toString();
                TextView textDisplay = findViewById(R.id.textView2);
                tip = Float.parseFloat(bill);
               // tip = Integer.parseInt(tipamount);
                tiptotal = tip * 0.15;
                totalbill = tiptotal + tip;
                textDisplay.setText("Tip: $" + df.format(tiptotal) + ", Total Bill: $" + df.format(totalbill));


            }
        });
        Button displayButton2 = findViewById(R.id.button2);
        displayButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editTextName);
                String bill = editText.getText().toString();
                TextView textDisplay = findViewById(R.id.textView2);
                tip = Float.parseFloat(bill);
               // tip = Integer.parseInt(tipamount);
                tiptotal = tip * 0.18;
                totalbill = tiptotal + tip;
                textDisplay.setText("Tip: $" + df.format(tiptotal) + ", Total Bill: $" + df.format(totalbill));


            }
        });
        Button displayButton3 = findViewById(R.id.button3);
        displayButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editTextName);
                String bill = editText.getText().toString();
                TextView textDisplay = findViewById(R.id.textView2);
                tip = Float.parseFloat(bill);
                // tip = Integer.parseInt(tipamount);
                tiptotal = tip * 0.20;
                totalbill = tiptotal + tip;
                textDisplay.setText("Tip: $" + df.format(tiptotal) + ", Total Bill: $" + df.format(totalbill));


            }
        });

    }
}
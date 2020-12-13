package edu.cs570.lindiechenou.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDisplayButton();
    }
    private void initDisplayButton() {
        Button displayButton = findViewById(R.id.buttonDisplay);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = findViewById(R.id.editTextName);
                TextView textDisplay = findViewById(R.id.textViewDisplay);
                EditText editName2 = findViewById(R.id.editTextName2);
                String firstnameToDisplay = editName.getText().toString();
                String lastnameToDisplay = editName2.getText().toString();
                textDisplay.setText("Hello " + firstnameToDisplay + " " + lastnameToDisplay + "!");
            }
        });
        Button displayButton2 = findViewById(R.id.button2);
        displayButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editName = findViewById(R.id.editTextName);
                TextView textDisplay = findViewById(R.id.textViewDisplay);
                EditText editName2 = findViewById(R.id.editTextName2);
                textDisplay.setText("Hello World!");
                editName.setText("");
                //editName.setHint("Enter Your First Name");
                editName2.setText("");
                //editName2.setHint("Enter Your Last Name");
            }
        });
    }
}
package com.github.greenmarker.calc.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.github.greenmarker.calc.*;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TouchCalculator";

    private TextView mStackText;
    private TextView userInputText;
    private TextView memoryStatText;

    private GridView mKeypadGrid;
    private KeypadAdapter mKeypadAdapter;

    private Calculator calc = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference to the keypad button GridView
        mKeypadGrid = (GridView)findViewById(R.id.grdButtons);

        // Create Keypad Adapter
        mKeypadAdapter = new KeypadAdapter(this); // this means context

        // Set adapter of the keypad grid
        mKeypadGrid.setAdapter(mKeypadAdapter);

        mKeypadAdapter.setmOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                // Get the KeypadButton value, which is used to identify keypad button from the Button's  tag
                KeypadButton keypadButton = (KeypadButton) btn.getTag();

                // Process keypad button
                Toast.makeText(MainActivity.this, keypadButton.getText(), Toast.LENGTH_SHORT).show();
                processKeypadInput(keypadButton);
            }
        });

        userInputText = (TextView)findViewById(R.id.txtInput);
        userInputText.setText("0");

        mStackText = (TextView)findViewById(R.id.txtStack);
        mStackText.setText("");

        memoryStatText = (TextView)findViewById(R.id.txtMemory);
        memoryStatText.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void processKeypadInput(KeypadButton keypadButton) {
        CalcResult result = calc.processKeypadInput(keypadButton);
        showResult(result);
    }

    public void showResult(CalcResult result){
        mStackText.setText(result.stack);
        userInputText.setText(result.userInput);
        memoryStatText.setText(result.memory);
    }

}

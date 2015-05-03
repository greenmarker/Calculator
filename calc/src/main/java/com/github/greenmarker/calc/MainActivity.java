package com.github.greenmarker.calc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Stack;

import static com.github.greenmarker.calc.KeypadButton.*;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TouchCalculator";

    private GridView mKeypadGrid;
    private KeypadAdapter mKeypadAdapter;

    private TextView userInputText;
    private TextView mStackText;
    private TextView memoryStatText;

    boolean resetInput;
    boolean hasFinalResult;
    private Stack<String> mInputStack = new Stack<>();
    private Stack<String> mOperationStack = new Stack<>();
    private CharSequence mDecimalSeparator = ",";
    private double memoryValue;

    public MainActivity() {
    }

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

        // Catch clicks on buttons
        mKeypadGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mKeypadAdapter.setmOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                // Get the KeypadButton value, which is used to identify keypad button from the Button's  tag
                KeypadButton keypadButton = (KeypadButton) btn.getTag();

                // Process keypad button
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


    private void processKeypadInput(KeypadButton keypadButton){
        Toast.makeText(this, keypadButton.getText(), Toast.LENGTH_SHORT).show();
        String text = keypadButton.getText().toString();
        String currentInput = userInputText.getText().toString();

        int currentInputLen = currentInput.length();
        String evalResult = null;
        double userInputValue = Double.NaN;

        switch (keypadButton){
            case BACKSPACE:
                if (resetInput)
                    return;

                int endIndex = currentInputLen -1;
                if (endIndex<1){
                    userInputText.setText("0");
                } else {
                    userInputText.setText(currentInput.subSequence(0, endIndex));
                }
                break;

            case SIGN:
                // input has text and is different than initial value 0
                if (currentInputLen>0 && currentInput!="0"){
                    if (currentInput.charAt(0) == '-'){
                        // remove -
                        userInputText.setText(currentInput.subSequence(1, currentInputLen));
                    } else {
                        // prepend -
                        userInputText.setText("-" + currentInput);
                    }
                }
                break;

            case CE:
                userInputText.setText("0");
                break;

            case C:
                userInputText.setText("0");
                clearStacks();
                break;

            case DECIMAL_SEP:
                if (hasFinalResult || resetInput){
                    userInputText.setText("0" + mDecimalSeparator);
                    hasFinalResult = false;
                    resetInput = false;
                } else if (currentInput.contains(mDecimalSeparator)){
                    return;
                } else {
                    userInputText.append(mDecimalSeparator);
                }
                break;

            case DIV:
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case SQRT:
                if (resetInput){
                    mInputStack.pop();
                    mOperationStack.pop();
                } else {
                    if (currentInput.charAt(0) == '-'){
                        mInputStack.add("(" + currentInput + ")");
                    } else {
                        mInputStack.add(currentInput);
                    }
                    mOperationStack.add(currentInput);
                }
                mInputStack.add(text);
                mOperationStack.add(text);

                dumpInputStack();
                evalResult = evaluateResult(false);
                if (evalResult != null){
                    userInputText.setText(evalResult);
                }
                resetInput = true;
                break;

            case CALCULATE:
                if (mOperationStack.size() == 0){
                    break;
                }

                mOperationStack.add(currentInput);
                evalResult = evaluateResult(true);
                if (evalResult != null){
                    clearStacks();
                    userInputText.setText(evalResult);
                    resetInput = false;
                    hasFinalResult = true;
                }
                break;

            case M_ADD:
                userInputValue = tryParseUserInput();
                if (Double.isNaN(userInputValue)){
                    return;
                }
                if (Double.isNaN(memoryValue)){
                    memoryValue = 0;
                }
                memoryValue += userInputValue;
                displayMemoryStat();

                hasFinalResult = false;
                break;

            default:
                if (Character.isDigit(text.charAt(0))){
                    if (currentInput.equals("0") || resetInput || hasFinalResult){
                        userInputText.setText(text);
                        resetInput = false;
                        hasFinalResult = false;

                    } else {
                        userInputText.append(text);
                        resetInput = false;
                    }
                }
        }
    }

    private void clearStacks() {
        mInputStack.clear();
        mOperationStack.clear();
        mStackText.setText("");
    }

    private void dumpInputStack() {
        StringBuilder sb = new StringBuilder();
        for (String s: mInputStack){
            sb.append(s);
        }
        mStackText.setText(sb.toString());
    }

    private String evaluateResult(boolean requestedByUser) {
        if ((!requestedByUser && mOperationStack.size()!=4)
                || (requestedByUser && mOperationStack.size()!=3)) {
            return null;
        }

        String left = mOperationStack.get(0);
        String operator = mOperationStack.get(1);
        String right = mOperationStack.get(2);
        String tmp = null;
        if (!requestedByUser)
            tmp = mOperationStack.get(3);

        double leftVal = Double.parseDouble(left.toString());
        double rightVal = Double.parseDouble(right.toString());
        double result = Double.NaN;

        if (operator.equals(DIV.getText())){
            result = leftVal / rightVal;
        } else if (operator.equals(MULTIPLY.getText())){
            result = leftVal * rightVal;
        } else if (operator.equals(PLUS.getText())){
            result = leftVal + rightVal;
        } else if (operator.equals(MINUS.getText())){
            result = leftVal - rightVal;
        } else if (operator.equals(SQRT.getText())){
            result = Math.sqrt(leftVal);
        } else if (operator.equals(RECIPROC.getText())){
            result = (1.0 / leftVal);
        } else if (operator.equals(PERCENT.getText())){
            result = Double.NaN;
        }

        String resultStr = doubleToString(result);
        if (resultStr == null)
            return null;

        mOperationStack.clear();
        if (!requestedByUser){
            mOperationStack.add(resultStr);
            mOperationStack.add(tmp);
        }

        return resultStr;
    }

    private String doubleToString(double value) {
        if (Double.isNaN(value)){
            return null;
        }
        long longVal = (long)value;
        if (longVal == value)
            return Long.toString(longVal);
        else
            return Double.toString(value);
    }

    private double tryParseUserInput() {
        String inputStr = userInputText.getText().toString();
        double result = Double.NaN;
        try {
            result = Double.parseDouble(inputStr);
        } catch (NumberFormatException nfe){
            Log.e(TAG, "Can't parse '" + inputStr + "' to double");
        }
        return result;
    }

    private void displayMemoryStat(){
        if (Double.isNaN(memoryValue)){
            memoryStatText.setText("");
        } else {
            memoryStatText.setText("M = " + doubleToString(memoryValue));
        }
    }


}

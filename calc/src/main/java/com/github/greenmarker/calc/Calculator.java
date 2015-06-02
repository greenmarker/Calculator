package com.github.greenmarker.calc;

import android.util.Log;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Stack;

import static com.github.greenmarker.calc.KeypadButton.*;
import static com.github.greenmarker.calc.gui.MainActivity.*;

/**
 * Created by Kamil on 2015-05-06.
 */
public class Calculator {

    private String currentInput = "";
    private String mStackText;
    private double memoryValue;

    private boolean resetInput;
    private boolean hasFinalResult;

    private Stack<String> mInputStack = new Stack<>();
    private Stack<String> mOperationStack = new Stack<>();

    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private CharSequence mDecimalSeparator = "" + DecimalFormatSymbols.getInstance().getDecimalSeparator();


    public CalcResult processKeypadInput(KeypadButton keypadButton){
        updateState(keypadButton);

        CalcResult result = new CalcResult();
        result.stack = mStackText;
        result.userInput = currentInput;
        result.memory = getMemoryText();

        return result;
    }

    private void updateState(KeypadButton keypadButton) {
        String text = keypadButton.getText().toString();

        int currentInputLen = currentInput.length();
        String evalResult = null;
        double userInputValue = Double.NaN;

        switch (keypadButton){
            case BACKSPACE:
                if (resetInput)
                    return;

                int endIndex = currentInputLen -1;
                if (endIndex<1){
                    currentInput = "0";
                } else {
                    currentInput = currentInput.substring(0, endIndex);
                }
                break;

            case SIGN:
                // input has text and is different than initial value 0
                if (currentInputLen>0 && currentInput!="0"){
                    if (currentInput.charAt(0) == '-'){
                        // remove -
                        currentInput = currentInput.substring(1, currentInputLen);
                    } else {
                        // prepend -
                        currentInput = "-" + currentInput;
                    }
                }
                break;

            case CE:
                currentInput = "0";
                break;

            case C:
                currentInput = "0";
                clearStacks();
                break;

            case DECIMAL_SEP:
                if (hasFinalResult || resetInput){
                    currentInput = "0" + mDecimalSeparator;
                    hasFinalResult = false;
                    resetInput = false;
                } else if (currentInput.contains(mDecimalSeparator)){
                    return;
                } else {
                    currentInput += mDecimalSeparator;
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
                    currentInput = evalResult;
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
                    currentInput = evalResult;
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
                //displayMemoryStat();

                hasFinalResult = false;
                break;

            default:
                if (Character.isDigit(text.charAt(0))){
                    if (currentInput.equals("0") || resetInput || hasFinalResult){
                        currentInput = text;
                        resetInput = false;
                        hasFinalResult = false;

                    } else {
                        currentInput += text;
                        resetInput = false;
                    }
                }
        }
    }

    private double tryParseUserInput() {
        //String inputStr = userInputText.getText().toString();
        double result = Double.NaN;
        try {
            result = Double.parseDouble(currentInput /*inputStr*/);
        } catch (NumberFormatException nfe){
            Log.e(TAG, "Can't parse '" + /*inputStr*/ currentInput + "' to double");
        }
        return result;
    }

    private void clearStacks() {
        mInputStack.clear();
        mOperationStack.clear();
        mStackText = "";
    }

    private void dumpInputStack() {
        StringBuilder sb = new StringBuilder();
        for (String s: mInputStack){
            sb.append(s);
        }
        mStackText = sb.toString();
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

        left = left.replace(',', '.');
        right = right.replace(',', '.');

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

    private String getMemoryText() {
        // displayMemoryStat
        String memoryStatText = "";
        if (Double.isNaN(memoryValue)){
            memoryStatText = "";
        } else {
            memoryStatText = "M = " + doubleToString(memoryValue);
        }
        return memoryStatText;
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

}

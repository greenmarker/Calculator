package com.github.greenmarker.calc.gui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Kamil on 2015-06-01.
 */
public class CalculatorButton extends Button{
    public CalculatorButton(Context context) {
        super(context);
    }
    public CalculatorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CalculatorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalculatorButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}

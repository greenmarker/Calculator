package com.github.greenmarker.calc;

import static com.github.greenmarker.calc.KeypadButton.*;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.TreeMap;

/**
 * Created by Kamil on 2015-04-28.
 */
public class KeypadAdapter extends BaseAdapter {
    private Context mContext;

    // Declaree button click listener variable
    private View.OnClickListener mOnButtonClick;

    // Create and populate keypad buttons array with KeypadButton values
    private KeypadButton[] mButtons = {
            MC, MR, MS,
            M_ADD, M_REMOVE,
            BACKSPACE, CE, C, SIGN, SQRT,
            SEVEN, EIGHT, NINE, DIV, PERCENT,
            FOUR, FIVE, SIX, MULTIPLY, RECIPROC,
            ONE, TWO, THREE, MINUS,
            DECIMAL_SEP,
            DUMMY, ZERO, DUMMY, PLUS, CALCULATE,
            SINUS, COSINUS, TANGENS, COTANGENS
    };

    private static final TreeMap<KeypadButtonCategory, Integer> BACKGROUNDS = new TreeMap<KeypadButtonCategory, Integer>(){{
        put(KeypadButtonCategory.CLEAR, R.color.keypad_clear);
        put(KeypadButtonCategory.MEMORYBUFFER, R.color.keypad_memorybuffer);
        put(KeypadButtonCategory.NUMBER, R.color.keypad_number);
        put(KeypadButtonCategory.OPERATOR, R.color.keypad_operator);
        put(KeypadButtonCategory.RESULT, R.color.keypad_result);
        put(KeypadButtonCategory.DUMMY, R.color.keypad_dummy);
    }};

    public KeypadAdapter(Context c){
        mContext = c;
    }

    // method to set button click listener
    public void setmOnButtonClick(View.OnClickListener mOnButtonClick) {
        this.mOnButtonClick = mOnButtonClick;
    }

    @Override
    public int getCount() {
        return mButtons.length;
    }

    @Override
    public Object getItem(int position) {
        return mButtons[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null){ // if it's not recycled, initialize some attributes
            btn = new Button(mContext);
            KeypadButton keypadButton = mButtons[position];

            if (keypadButton != DUMMY){
                btn.setOnClickListener(mOnButtonClick);
            }

            Integer res = BACKGROUNDS.get(keypadButton.mCategory);
            if (res!=null) {
                btn.setBackgroundResource(res);
            }

            // Set KeypadButton enumeration as tag of the button so that we
            // will use this information from ur main view to identify what to do
            btn.setTag(keypadButton);

        } else {
            btn = (Button)convertView;
        }

        btn.setText(mButtons[position].getText());
        return btn;
    }
}

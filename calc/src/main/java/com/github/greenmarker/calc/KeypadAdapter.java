package com.github.greenmarker.calc;

import static com.github.greenmarker.calc.KeypadButton.*;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Kamil on 2015-04-28.
 */
public class KeypadAdapter extends BaseAdapter {
    private Context mContext;

    private KeypadButton[] mButtons = {
            MC, MR, MS,
            M_ADD, M_REMOVE,
            BACKSPACE, CE, C, SIGN, SQRT,
            SEVEN, EIGHT, NINE, DIV, PERCENT,
            FOUR, FIVE, SIX, MULTIPLY, RECIPROC
            ONE, TWO, THREE, MINUS,
            DECIMAL_SEP,
            DUMMY, ZERO, DUMMY, PLUS, CALCULATE
    }

    public KeypadAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

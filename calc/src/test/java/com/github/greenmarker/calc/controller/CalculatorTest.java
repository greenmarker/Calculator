package com.github.greenmarker.calc.controller;

import com.github.greenmarker.calc.KeypadButton;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Ignore;

import static junit.framework.Assert.*;

/**
 * Created by Kamil on 2015-06-07.
 */
public class CalculatorTest {

    @Test
    public void changeSign(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.ONE);
        assertEquals("Wrong userInput after entering 1", "1", res.userInput);

        res = calc.processKeypadInput(KeypadButton.SIGN);
        assertEquals("Wrong userInput after first changing sign", "-1", res.userInput);

        res = calc.processKeypadInput(KeypadButton.SIGN);
        assertEquals("Wrong userInput after second changing sign", "1", res.userInput);
    }

    @Test
    public void add(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.TWO);
        assertEquals("Wrong userInput after 1st operation", "2", res.userInput);

        res = calc.processKeypadInput(KeypadButton.PLUS);
        assertEquals("Wrong userInput after 2nd operation", "2", res.userInput);

        res = calc.processKeypadInput(KeypadButton.TWO);
        assertEquals("Wrong userInput after 3rd operation", "2", res.userInput);

        res = calc.processKeypadInput(KeypadButton.CALCULATE);
        assertEquals("Wrong userInput after 4th operation", "4", res.userInput);
    }

    @Test
    public void subtract(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.THREE);
        assertEquals("Wrong userInput after 1st operation", "3", res.userInput);

        res = calc.processKeypadInput(KeypadButton.MINUS);
        assertEquals("Wrong userInput after 2nd operation", "3", res.userInput);

        res = calc.processKeypadInput(KeypadButton.FIVE);
        assertEquals("Wrong userInput after 3rd operation", "5", res.userInput);

        res = calc.processKeypadInput(KeypadButton.CALCULATE);
        assertEquals("Wrong userInput after 4th operation", "-2", res.userInput);
    }

    @Test
    public void multiply(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.FOUR);
        assertEquals("Wrong userInput after 1st operation", "4", res.userInput);

        res = calc.processKeypadInput(KeypadButton.MULTIPLY);
        assertEquals("Wrong userInput after 2nd operation", "4", res.userInput);

        res = calc.processKeypadInput(KeypadButton.SIX);
        assertEquals("Wrong userInput after 1st operation", "6", res.userInput);

        res = calc.processKeypadInput(KeypadButton.CALCULATE);
        assertEquals("Wrong userInput after 4th operation", "24", res.userInput);
    }

    public void divide(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.FIVE);
        assertEquals("Wrong userInput after 1st operation", "5", res.userInput);

        res = calc.processKeypadInput(KeypadButton.DIV);
        assertEquals("Wrong userInput after 2nd operation", "5", res.userInput);

        res = calc.processKeypadInput(KeypadButton.TWO);
        assertEquals("Wrong userInput after 1st operation", "2", res.userInput);

        res = calc.processKeypadInput(KeypadButton.CALCULATE);
        assertEquals("Wrong userInput after 4th operation", "2,5", res.userInput);  // TODO , - it's locale sensitive!
    }

    public void decimalSeparator(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.EIGHT);
        assertEquals("Wrong userInput after 1st operation", "6", res.userInput);

        res = calc.processKeypadInput(KeypadButton.DECIMAL_SEP);
        assertEquals("Wrong userInput after 1st operation", "6", res.userInput);

        res = calc.processKeypadInput(KeypadButton.ONE);
        assertEquals("Wrong userInput after 1st operation", "6,1", res.userInput); // TODO , - it's locale sensitive!

        // second DECIMAL_SEP is ignored

        res = calc.processKeypadInput(KeypadButton.DECIMAL_SEP);
        assertEquals("Wrong userInput after 1st operation", "6,1", res.userInput);

        res = calc.processKeypadInput(KeypadButton.TWO);
        assertEquals("Wrong userInput after 1st operation", "6,12", res.userInput); // TODO , - it's locale sensitive!
    }

    // 7
    public void backspace() {
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.SEVEN);
        assertEquals("Wrong userInput after 1st operation", "7", res.userInput);

        res = calc.processKeypadInput(KeypadButton.ZERO);
        assertEquals("Wrong userInput after 2n operation", "70", res.userInput);

        res = calc.processKeypadInput(KeypadButton.BACKSPACE);
        assertEquals("Wrong userInput after 3rd operation", "7", res.userInput);

        res = calc.processKeypadInput(KeypadButton.BACKSPACE);
        assertEquals("Wrong userInput after 4th operation", "0", res.userInput);
    }

    /**
     * 1/x
     */
    public void multiplicativeInverse(){
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.EIGHT);
        assertEquals("Wrong userInput after 1st operation", "8", res.userInput);

        res = calc.processKeypadInput(KeypadButton.RECIPROC);
        assertEquals("Wrong userInput after 2nd operation", "0,125", res.userInput);
    }

    public void sqrt() {
        Calculator calc = new Calculator();
        CalcResult res;

        res = calc.processKeypadInput(KeypadButton.NINE);
        assertEquals("Wrong userInput after 1st operation", "9", res.userInput);

        res = calc.processKeypadInput(KeypadButton.SQRT);
        assertEquals("Wrong userInput after 2nd operation", "3", res.userInput);
    }

}

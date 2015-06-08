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
}

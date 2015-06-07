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
    public void testAdding(){
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
    @Ignore
    public void nok() {
        assertTrue(false);
    }

}

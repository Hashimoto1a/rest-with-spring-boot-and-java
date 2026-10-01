package br.com.hashimoto1a.controllers;

import br.com.hashimoto1a.convert.ConvertToDouble;
import br.com.hashimoto1a.exception.UnsupportedMathOperationException;
import br.com.hashimoto1a.math.SimpleMath;
import br.com.hashimoto1a.numeric.IsNumeric;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    private SimpleMath math = new SimpleMath();

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    )throws Exception{
        if(!IsNumeric.isNumeric(numberOne) || !IsNumeric.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.sum(ConvertToDouble.convertToDouble(numberOne), ConvertToDouble.convertToDouble(numberTwo));
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    )throws Exception{
        if(!IsNumeric.isNumeric(numberOne) || !IsNumeric.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.subtraction(ConvertToDouble.convertToDouble(numberOne), ConvertToDouble.convertToDouble(numberTwo));
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable("numberOne") String numberOne,
            @PathVariable("numberTwo") String numberTwo
    )throws Exception{
        if(!IsNumeric.isNumeric(numberOne) || !IsNumeric.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.multiplication(ConvertToDouble.convertToDouble(numberOne), ConvertToDouble.convertToDouble(numberTwo));
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo)throws Exception{
        if(!IsNumeric.isNumeric(numberOne) || !IsNumeric.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.division(ConvertToDouble.convertToDouble(numberOne), ConvertToDouble.convertToDouble(numberTwo));
    }

    @RequestMapping("/average/{numberOne}/{numberTwo}")
    public Double average(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) throws Exception{
        if(!IsNumeric.isNumeric(numberOne) || !IsNumeric.isNumeric(numberTwo)) throw new UnsupportedMathOperationException("please set a numeric value!");
        return math.average(ConvertToDouble.convertToDouble(numberOne), ConvertToDouble.convertToDouble(numberTwo));
    }

    @RequestMapping("/squareroot/{number}")
    public Double squareRoot(
            @PathVariable("number") String number
    ) throws Exception{
        if(!IsNumeric.isNumeric(number)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.squareRoot(ConvertToDouble.convertToDouble(number));
    }
}

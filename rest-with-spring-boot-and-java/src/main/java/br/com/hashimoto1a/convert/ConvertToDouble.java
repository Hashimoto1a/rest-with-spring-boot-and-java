package br.com.hashimoto1a.convert;

import br.com.hashimoto1a.exception.UnsupportedMathOperationException;

public class ConvertToDouble {
    public static Double convertToDouble(String strNumber) throws IllegalArgumentException {
        if(strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replace(",",".");
        return Double.parseDouble(number);
    }
}

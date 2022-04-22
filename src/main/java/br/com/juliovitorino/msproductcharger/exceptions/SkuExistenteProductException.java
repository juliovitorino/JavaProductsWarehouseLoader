package br.com.juliovitorino.msproductcharger.exceptions;

public class SkuExistenteProductException extends Exception {
    public SkuExistenteProductException (String str)
    {
        // calling the constructor of parent Exception
        super(str);
    }
}

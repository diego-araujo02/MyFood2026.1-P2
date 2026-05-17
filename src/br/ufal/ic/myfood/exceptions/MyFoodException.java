package br.ufal.ic.myfood.exceptions;

public abstract class MyFoodException extends Exception {
    public MyFoodException(String message) {
        super(message);
    }
}
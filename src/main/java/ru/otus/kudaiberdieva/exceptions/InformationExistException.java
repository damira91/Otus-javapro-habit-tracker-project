package ru.otus.kudaiberdieva.exceptions;

public class InformationExistException extends RuntimeException {
    public InformationExistException(String message){
        super(message);
    }
}

package DesignPatterns;

import DesignPatterns.Memento;

import java.util.ArrayList;

public class Caretaker {

    ArrayList<Memento> savedQuestions = new ArrayList<Memento>();

    public void addMemento(Memento m){savedQuestions.add(m);}

    public Memento getMemento(int index){
        return savedQuestions.get(index);}
}

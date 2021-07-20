package org.vova.model.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class MyArrayList<E> extends ArrayList<E> {
    @Override
    public E set(int index, E element) {
        System.out.println("sorry this method was turned off");
        return element;
    }

    @Override
    public E remove(int index) {
        System.out.println("sorry this method was turned off");
        return this.get(index);
    }

    @Override
    public boolean remove(Object o) {
        System.out.println("sorry this method was turned off");
        return false;
    }

    @Override
    public void clear() {
        System.out.println("sorry this method was turned off");
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        System.out.println("sorry this method was turned off");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        System.out.println("sorry this method was turned off");
        return false;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        System.out.println("sorry this method was turned off");
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        System.out.println("sorry this method was turned off");
        return false;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        System.out.println("sorry this method was turned off");
    }
    
}

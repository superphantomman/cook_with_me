package com.superphantomman.cook_with_me.util;

import java.util.Comparator;
import java.util.List;

//TODO test it
public interface DaoService<T> {

    List<? extends T> getAll(String search);
    List<? extends T> getAll();

    List<? extends T> getAllSorted(Comparator<? super T> comp);
    boolean add(T e);
    boolean remove(T e);
    T get(Long id);
    boolean removeAll( List<T> elements );
    boolean addAll(List<T>  elements);
    boolean contains(T e);

    void clear();



}

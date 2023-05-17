package com.superphantomman.cook_with_me.util;

import java.util.Comparator;
import java.util.List;

public interface DaoService<T> {

    List<T> getAll(String search);
    List<T> getAll();

    List<? extends T> getAllSorted(Comparator<? super T> comp);
    boolean add(T e);
    boolean add(Form<T> form);
    boolean remove(T e);
    T get(Long id);
    boolean removeAll( List<T> elements );
    boolean addAll(List<T>  elements);
    boolean contains(T e);

    void clear();



}

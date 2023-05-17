package com.superphantomman.cook_with_me.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public abstract class AbstractDaoService<T> implements DaoService<T> {
    @Autowired
    protected final JpaRepository<T, Long> repository;

    public AbstractDaoService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public List<? extends T> getAllSorted(Comparator<? super T> comp) {

        final List<? extends T> result = new ArrayList<>(getAll());
        result.sort(comp);

        return result;
    }

    public boolean add(T e) {
        repository.save(e);
        return contains(e);
    }

    @Override
    public boolean add(Form<T> form) {
        return add(form.toEntity());
    }

    public boolean remove(T e) {
        repository.delete(e);
        return !contains(e);
    }


    public boolean addAll(List<T> elements) {
        repository.saveAll(elements);
        return existsAll(elements);
    }

    public boolean existsAll(List<T> elements) {
        return new HashSet<>(repository.findAll()).containsAll(elements);
    }

    public boolean removeAll(List<T> elements) {
        repository.deleteAll(elements);
        return !existsAll(elements);
    }

    public T get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public T remove(Long i) {
        T result = get(i);
        return remove(result) ? result : null;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public boolean contains(T e) {
        return repository.exists(Example.of(e));
    }

    public void clear() {
        repository.deleteAll();
    }
}

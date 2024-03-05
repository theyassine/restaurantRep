package org.example.entities;

import java.util.List;

public interface ISevice<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    void update1(T t);
    List<T> readAll();
    T readById(int id);
    List<T> searchByPlace(String place);

    public List<T> getRestaurantsByCategory(int categoryId);
}

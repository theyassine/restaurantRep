package org.example.entities;

import java.util.List;

public interface Iservlist<T> {

        void ajouter(T t);
        void modifier(T t);
        void supprimer(int id);
        List<T> afficher();
    }



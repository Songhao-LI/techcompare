package com.bootcamp.techcompare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String location;

    public Store(String location) {
        this.location = location;
    }

    public Store() {

    }
}

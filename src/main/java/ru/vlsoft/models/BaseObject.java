package ru.vlsoft.models;

import java.util.UUID;

public abstract class BaseObject {
    private UUID id;
    private String name;

    public BaseObject(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

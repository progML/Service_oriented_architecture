package ru.itmo.soalab2.model;

import java.time.LocalDateTime;

public class CarFromClient {
    private int id;
    private boolean cool;

    public CarFromClient(int id, boolean cool) {
        this.id = id;
        this.cool = cool;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }

    public int getId() {
        return id;
    }

    public boolean isCool() {
        return cool;
    }
}

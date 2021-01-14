package com.example.habiticasmaextension.core.models;

import java.io.Serializable;

public class Stats implements Serializable {
    public double hp;
    public int level;
    public String klass;

    public Stats(double hp, int level, String klass) {
        this.hp = hp;
        this.level = level;
        this.klass = klass;
    }
}

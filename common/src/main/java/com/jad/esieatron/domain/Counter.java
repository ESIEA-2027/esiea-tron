package com.jad.esieatron.domain;

public final class Counter {
    private Integer value = 0;

    public void increment() {
        this.value++;
    }

    public Integer getValue() {
        return this.value;
    }
}

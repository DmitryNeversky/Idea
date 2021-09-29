package org.dneversky.idea.model;

public enum Status {
    ACCEPTED("Принято"),
    LOOKING("Рассматривается"),
    REFUSED("Отказано");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

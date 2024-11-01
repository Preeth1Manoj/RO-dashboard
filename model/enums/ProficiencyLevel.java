package com.report.ro.model.enums;

public enum ProficiencyLevel {
    BEGINNER("Beginner", 1),
    INTERMEDIATE("Intermediate", 2),
    ADVANCED("Advanced", 3),
    EXPERT("Expert", 4),
    MASTER("Master", 5);

    private String level;
    private int value;

    ProficiencyLevel(String level, int value) {
        this.level = level;
        this.value = value;
    }

    public String getLevel() {
        return level;
    }

    public int getValue() {
        return value;
    }
}



package com.unpri.charactermanager.model;

public class Character {
    private static int totalCharacterCreated = 0;

    private int id;
    private String name;
    private CharacterType type;
    private int level;
    private int hitPoints;
    private int attackPower;
    private int mana;

    // Constructor 1: karakter baru, HP & attack dihitung dari level
    public Character(String name, CharacterType type, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level minimal adalah 1");
        }
        this.name = name;
        this.type = type;
        this.level = level;
        this.hitPoints = calculateBaseHP();
        this.attackPower = calculateBaseAttack();
        this.mana = 0;
        totalCharacterCreated++;
        this.id = totalCharacterCreated;
    }

    // Constructor 2: memuat data yang sudah tersimpan, tanpa hitung ulang
    public Character(int id, String name, CharacterType type, int level,
                     int hitPoints, int attackPower) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attackPower = attackPower;
        this.mana = 0;
    }

    private int calculateBaseHP() {
        return 100 + level * 10;
    }

    private int calculateBaseAttack() {
        return 10 + level * 5;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public CharacterType getType() { return type; }
    public int getLevel() { return level; }
    public int getHitPoints() { return hitPoints; }
    public int getAttackPower() { return attackPower; }
    public int getMana() { return mana; }

    public void setLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level minimal adalah 1");
        }
        this.level = level;
        this.hitPoints = calculateBaseHP();
        this.attackPower = calculateBaseAttack();
    }

    public void setMana(int mana) {
        if (mana < 0) {
            throw new IllegalArgumentException("Mana tidak boleh negatif");
        }
        this.mana = mana;
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public static int getTotalCharacterCreated() {
        return totalCharacterCreated;
    }

    @Override
    public String toString() {
        return "Character[id=" + id + ", name=" + name + ", type=" + type
                + ", level=" + level + ", HP=" + hitPoints
                + ", ATK=" + attackPower + ", mana=" + mana + "]";
    }
}
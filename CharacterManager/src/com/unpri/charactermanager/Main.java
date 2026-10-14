package com.unpri.charactermanager;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Character Manager ===");

        // Bagian A: 3 karakter
        System.out.println("\n--- Daftar Karakter ---");
        Character c1 = new Character("Yapriani", CharacterType.WARRIOR, 5);
        Character c2 = new Character("Yulya", CharacterType.ARCHER, 3);
        Character c3 = new Character("Juandi", CharacterType.MAGE, 4);

        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);

        // Demo level up
        System.out.println("\n--- Demo Level Up ---");
        System.out.println("Sebelum: " + c3);
        c3.setLevel(7);
        System.out.println("Sesudah: " + c3);

        // Bagian B: karakter baru + mana + isAlive()
        System.out.println("\n--- Mini-Challenge ---");
        Character c4 = new Character("Budjus", CharacterType.HEALER, 2);
        c4.setMana(50);
        System.out.println(c4);
        System.out.println("Apakah " + c4.getName() + " hidup? " + c4.isAlive());
    }
}
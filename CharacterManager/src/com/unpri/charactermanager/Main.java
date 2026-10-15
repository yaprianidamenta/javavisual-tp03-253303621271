package com.unpri.charactermanager;

// Import class Character dan enum CharacterType dari package model
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Character Manager ===");

        // ===== Bagian A: membuat 3 karakter =====
        System.out.println("\n--- Daftar Karakter ---");

        // Memakai constructor 1: HP dan attack dihitung otomatis dari level,
        // id juga dibuat otomatis oleh class Character
        Character c1 = new Character("Yapriani", CharacterType.WARRIOR, 5);
        Character c2 = new Character("Yulya", CharacterType.ARCHER, 3);
        Character c3 = new Character("Juandi", CharacterType.MAGE, 4);

        // println memanggil toString() milik Character secara otomatis
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);

        // ===== Demo level up =====
        System.out.println("\n--- Demo Level Up ---");
        System.out.println("Sebelum: " + c3);

        // setLevel() menghitung ulang HP dan attack di dalam class Character,
        // jadi di sini cukup panggil setLevel(7) saja
        c3.setLevel(7);
        System.out.println("Sesudah: " + c3);

        // ===== Bagian B: karakter baru + mana + isAlive() =====
        System.out.println("\n--- Mini-Challenge ---");
        Character c4 = new Character("Budjus", CharacterType.HEALER, 2);

        // Mengisi mana lewat setter, karena atribut mana bersifat private
        c4.setMana(50);
        System.out.println(c4);
        System.out.println("Mana: " + c4.getMana());

        // isAlive() mengembalikan true kalau hitPoints > 0
        System.out.println("Apakah " + c4.getName() + " hidup? " + c4.isAlive());

        // ===== Bukti validasi setMana() =====
        System.out.println("\n--- Validasi Mana ---");
        try {
            // Nilai negatif akan ditolak, setMana() melempar IllegalArgumentException
            c4.setMana(-10);
        } catch (IllegalArgumentException e) {
            // Exception ditangkap supaya program tidak berhenti,
            // lalu pesan error dari setMana() ditampilkan
            System.out.println("Validasi berfungsi: " + e.getMessage());
        }
    }
}
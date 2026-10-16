package com.unpri.charactermanager.model;

public class Character {
    // Atribut static: dimiliki class, bukan tiap objek.
    // Dipakai sebagai penghitung untuk membuat id otomatis
    private static int totalCharacterCreated = 0;

    // Semua atribut dibuat private (encapsulation) supaya tidak bisa
    // diubah sembarangan dari luar class
    private int id;
    private String name;
    private CharacterType type;
    private int level;
    private int hitPoints;
    private int attackPower;
    private int mana;

    // Constructor 1: karakter baru, HP & attack dihitung dari level
    public Character(String name, CharacterType type, int level) {
        // Validasi: level tidak boleh kurang dari 1
        if (level < 1) {
            throw new IllegalArgumentException("Level minimal adalah 1");
        }
        this.name = name;
        this.type = type;
        this.level = level;
        // level harus diisi dulu sebelum menghitung HP dan attack
        this.hitPoints = calculateBaseHP();
        this.attackPower = calculateBaseAttack();
        this.mana = 0; // mana awal 0, bisa diisi lewat setMana()
        // Counter naik 1 setiap karakter baru dibuat, lalu dipakai sebagai id
        totalCharacterCreated++;
        this.id = totalCharacterCreated;
    }

    // Constructor 2: memuat data yang sudah tersimpan, tanpa hitung ulang
    public Character(int id, String name, CharacterType type, int level,
                     int hitPoints, int attackPower) {
        // Validasi yang sama dengan constructor 1 supaya aturan level konsisten
        if (level < 1) {
            throw new IllegalArgumentException("Level minimal adalah 1");
        }
        // Semua nilai diisi langsung dari parameter (data yang sudah ada),
        // counter tidak bertambah karena ini bukan karakter yang benar-benar baru
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.hitPoints = hitPoints;
        this.attackPower = attackPower;
        this.mana = 0;
    }

    // Method private: hanya dipakai di dalam class ini untuk rumus HP
    private int calculateBaseHP() {
        return 100 + level * 10;
    }

    // Method private: rumus attack berdasarkan level
    private int calculateBaseAttack() {
        return 10 + level * 5;
    }

    // Getter: cara membaca atribut private dari luar class
    public int getId() { return id; }
    public String getName() { return name; }
    public CharacterType getType() { return type; }
    public int getLevel() { return level; }
    public int getHitPoints() { return hitPoints; }
    public int getAttackPower() { return attackPower; }

    public int getMana() { return mana; }

    // Setter level: HP dan attack ikut dihitung ulang di sini,
    // jadi pemanggil tidak perlu menghitung sendiri
    public void setLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Level minimal adalah 1");
        }
        this.level = level;
        this.hitPoints = calculateBaseHP();
        this.attackPower = calculateBaseAttack();
    }

    // Setter mana dengan validasi: nilai negatif ditolak
    public void setMana(int mana) {
        if (mana < 0) {
            throw new IllegalArgumentException("Mana tidak boleh negatif");
        }
        this.mana = mana;
    }

    // Package-private: hanya CharacterManager (satu package) yang boleh memanggil
    void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }
        this.name = name;
    }

    // Karakter dianggap hidup selama hitPoints lebih dari 0
    public boolean isAlive() {
        return hitPoints > 0;
    }

    // Method static: dipanggil lewat nama class, tanpa perlu membuat objek
    public static int getTotalCharacterCreated() {
        return totalCharacterCreated;
    }

    // Mengubah objek jadi teks supaya bisa langsung dicetak dengan println
    @Override
    public String toString() {
        return "Character[id=" + id + ", name=" + name + ", type=" + type
                + ", level=" + level + ", HP=" + hitPoints
                + ", ATK=" + attackPower + ", mana=" + mana + "]";
    }

      // ===== equals(): membandingkan ISI DATA, bukan alamat memori =====
    // Dua Character dianggap "sama" jika NAMA-nya identik (case-sensitive)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;                    // optimasi: jika memang object yang sama persis
        if (!(obj instanceof Character)) return false;    // jika bukan Character, pasti tidak sama
        Character other = (Character) obj;                // casting aman karena sudah dicek instanceof
        return this.name.equals(other.name);               // bandingkan isi atribut name (String punya equals sendiri)
    }
}
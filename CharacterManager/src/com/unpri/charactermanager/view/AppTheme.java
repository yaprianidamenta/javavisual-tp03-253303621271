package com.unpri.charactermanager.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import com.unpri.charactermanager.model.CharacterType;

/**
 * Class ini tempat nyimpen semua urusan tampilan (warna, font, border).
 * Jadi kalau mau ganti tema, cukup edit file ini aja,
 * nggak usah bongkar MainWindow satu-satu.
 */
public final class AppTheme {

    // ---------- Warna ----------
    // Warna latar jendela, sengaja gelap biar kartu warna-warninya kelihatan jelas
    public static final Color BG_MAIN    = Color.decode("#13131f");
    // Warna teks judul, abu terang biar nggak terlalu silau kayak putih murni
    public static final Color TEXT_MAIN  = Color.decode("#dfe6e9");
    // Teks di dalam kartu, putih karena latarnya berwarna
    public static final Color TEXT_LIGHT = Color.WHITE;

    // ---------- Font ----------
    // Pakai nama font logical (SansSerif/Monospaced), soalnya kalau nama font
    // tertentu bisa nggak ada di laptop orang lain
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);  // buat judul
    public static final Font FONT_BODY  = new Font("SansSerif", Font.PLAIN, 14); // buat isi kartu
    public static final Font FONT_MONO  = new Font("Monospaced", Font.PLAIN, 13); // buat angka biar sejajar

    // ---------- Border ----------
    // Border ini immutable (nggak bisa berubah) jadi aman dipakai banyak komponen sekaligus
    public static final Border CARD_BORDER  = BorderFactory.createEmptyBorder(8, 12, 8, 12);   // padding dalam kartu
    public static final Border PANEL_BORDER = BorderFactory.createEmptyBorder(16, 16, 16, 16); // padding luar panel

    // ---------- Ukuran & jarak ----------
    // Aku jadiin konstanta biar nggak ada angka "ajaib" yang nyelip di MainWindow
    public static final int CARD_GAP   = 8;    // jarak antar kartu
    public static final int MIN_WIDTH  = 420;  // lebar minimum jendela
    public static final int MIN_HEIGHT = 300;  // tinggi minimum jendela

    // Konstruktor dibuat private karena class ini isinya static semua,
    // jadi nggak perlu (dan nggak boleh) dibuat objeknya
    private AppTheme() { }

    /**
     * Ngubah role karakter (data dari Model) jadi warna kartu.
     * Sengaja ditaruh di sini, bukan di Character, karena warna itu urusan tampilan.
     */
    public static Color colorFor(CharacterType type) {
        switch (type) {
            case WARRIOR: return Color.decode("#d63031"); // merah
            case MAGE:    return Color.decode("#0984e3"); // biru
            case ARCHER:  return Color.decode("#00b894"); // hijau
            case HEALER:  return Color.decode("#6c5ce7"); // ungu, tadi lupa nambahin role ini
            default:      return Color.GRAY;              // jaga-jaga kalau nanti ada role baru
        }
    }

        /**
     * Teks penjelasan tiap role, nanti dipakai buat tooltip pas kartu di-hover.
     * Sama kayak warna, ini juga urusan tampilan jadi ditaruh di AppTheme.
     */
    public static String describe(CharacterType type) {
        switch (type) {
            case WARRIOR: return "Warrior: jarak dekat, HP tinggi";
            case MAGE:    return "Mage: serangan sihir, HP rendah";
            case ARCHER:  return "Archer: jarak jauh, seimbang";
            case HEALER:  return "Healer: penyembuh tim";
            default:      return "Role belum terdaftar";
        }
    }
}
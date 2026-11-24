package com.unpri.charactermanager.view;

import java.awt.Color;
import java.awt.Dimension;
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
    public static final Color BG_MAIN    = Color.decode("#13131f"); // latar jendela
    public static final Color BG_STATUS  = Color.decode("#0d0d1a"); // latar bar status, lebih gelap
    public static final Color TEXT_MAIN  = Color.decode("#dfe6e9"); // teks judul
    public static final Color TEXT_MUTED = Color.decode("#9d9dbf"); // teks sekunder / metadata
    public static final Color TEXT_LIGHT = Color.WHITE;             // teks di dalam kartu
    public static final Color GOLD       = Color.decode("#fdcb6e"); // aksen emas
    public static final Color ERROR      = Color.decode("#ff7675"); // pesan error, merah terang
    public static final Color SUCCESS    = Color.decode("#55efc4"); // pesan sukses, hijau terang

    // ---------- Font ----------
    // Pakai nama font logical supaya aman di laptop mana pun
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);   // judul
    public static final Font FONT_BODY  = new Font("SansSerif", Font.PLAIN, 14);  // isi kartu
    public static final Font FONT_MONO  = new Font("Monospaced", Font.PLAIN, 13); // angka sejajar
    // Turunan FONT_BODY, jadi harus ditulis SETELAH FONT_BODY
    public static final Font FONT_BODY_BOLD  = FONT_BODY.deriveFont(Font.BOLD);       // judul legenda
    public static final Font FONT_FORM_TITLE = FONT_BODY.deriveFont(Font.BOLD, 16f);  // judul form

    // ---------- Border ----------
    public static final Border CARD_BORDER   = BorderFactory.createEmptyBorder(8, 12, 8, 12);
    public static final Border PANEL_BORDER  = BorderFactory.createEmptyBorder(16, 16, 16, 16);
    public static final Border HEADER_BORDER = BorderFactory.createEmptyBorder(16, 16, 8, 16);
    public static final Border STATS_BORDER  = BorderFactory.createEmptyBorder(0, 16, 8, 16);
    public static final Border LIST_BORDER   = BorderFactory.createEmptyBorder(8, 8, 8, 8);
    public static final Border LEGEND_BORDER = BorderFactory.createEmptyBorder(8, 8, 8, 16);
    public static final Border STATUS_BORDER = BorderFactory.createEmptyBorder(6, 16, 6, 16);
    public static final Border FORM_BORDER   = BorderFactory.createEmptyBorder(8, 16, 8, 8);

    // ---------- Ukuran & jarak ----------
    public static final int CARD_GAP    = 8;    // jarak antar kartu
    public static final int MIN_WIDTH   = 860;  // lebar minimum jendela (termasuk form)
    public static final int MIN_HEIGHT  = 560;  // tinggi minimum jendela
    public static final Dimension LIST_SIZE = new Dimension(420, 280); // ukuran ideal area daftar
    public static final Dimension FORM_SIZE = new Dimension(250, 0);   // lebar form di WEST
    public static final int LEGEND_GAP  = 8;    // spasi judul legenda ke chip pertama
    public static final int CHIP_GAP    = 6;    // spasi antar chip
    public static final int STATS_GAP   = 8;    // jarak antar kotak statistik
    public static final int FORM_ROW_GAP = 4;   // jarak antar baris form
    public static final int ERROR_BORDER_WIDTH = 2; // tebal border merah pada field bermasalah

    // ---------- Aturan tampilan ----------
    public static final int HIGH_LEVEL_THRESHOLD = 5; // batas level "tinggi"
    public static final int DISABLED_LEVEL = 1;       // level pemula

    // ---------- Info aplikasi ----------
    public static final String APP_VERSION = "v0.6";  // satu sumber versi

    // Konstruktor private: class ini isinya static semua
    private AppTheme() { }

    /** Ngubah role karakter (data dari Model) jadi warna kartu. */
    public static Color colorFor(CharacterType type) {
        switch (type) {
            case WARRIOR: return Color.decode("#d63031"); // merah
            case MAGE:    return Color.decode("#0984e3"); // biru
            case ARCHER:  return Color.decode("#00b894"); // hijau
            case HEALER:  return Color.decode("#6c5ce7"); // ungu
            default:      return Color.GRAY;              // jaga-jaga kalau ada role baru
        }
    }

    /** Teks penjelasan tiap role, dipakai buat tooltip. */
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
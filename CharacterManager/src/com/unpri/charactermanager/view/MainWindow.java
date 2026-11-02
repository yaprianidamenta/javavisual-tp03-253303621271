package com.unpri.charactermanager.view;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.unpri.charactermanager.model.Character;

public class MainWindow extends JFrame {

    private JPanel mainPanel;                     // panel utama tempat semua kartu
    private ArrayList<Character> characterList;   // data karakter dari Main.java

    public MainWindow(ArrayList<Character> characterList) {
        super("Character Manager v0.4");          // judul jendela, versinya naik jadi 0.4
        this.characterList = characterList;

        initComponents();   // susun semua komponen dulu, baru ukuran jendela dihitung

        pack();             // ukuran jendela ngikutin isinya, jadi nggak perlu setSize manual
        setMinimumSize(new Dimension(AppTheme.MIN_WIDTH, AppTheme.MIN_HEIGHT)); // biar nggak bisa dikecilin berlebihan
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // taruh di tengah layar, harus SETELAH pack() kalau nggak posisinya miring
    }

    private void initComponents() {
        // GridLayout 1 kolom, baris menyesuaikan jumlah karakter, jarak antar kartu dari AppTheme
        mainPanel = new JPanel(new GridLayout(0, 1, 0, AppTheme.CARD_GAP));
        mainPanel.setBackground(AppTheme.BG_MAIN);
        mainPanel.setBorder(AppTheme.PANEL_BORDER);

        mainPanel.add(createTitleLabel());           // judul paling atas

        for (Character c : characterList) {          // tiap karakter dibikinin satu kartu
            mainPanel.add(createCharacterLabel(c));
        }

        add(mainPanel);   // terakhir, pasang panel ke jendela
    }

    // Bikin label judul di bagian atas
    private JLabel createTitleLabel() {
        JLabel lbl = new JLabel("Daftar Karakter (" + characterList.size() + ")",
                                SwingConstants.CENTER);  // teksnya di tengah
        lbl.setFont(AppTheme.FONT_TITLE);
        lbl.setForeground(AppTheme.TEXT_MAIN);
        return lbl;
    }

    // Bikin satu kartu buat satu karakter
    private JLabel createCharacterLabel(Character c) {
        // View cuma BACA data lewat getter, nggak ngubah apa-apa di Model
        String info = c.getName() + " | " + c.getType()
                    + " | Lv." + c.getLevel()
                    + " | HP:" + c.getHitPoints()
                    + " | ATK:" + c.getAttackPower();

        JLabel lbl = new JLabel(info);
        // Level >= batas tampil emas dan tebal, sisanya tampil biasa.
        // Batasnya diambil dari AppTheme, bukan ditulis angka langsung di sini
        if (c.getLevel() >= AppTheme.HIGH_LEVEL_THRESHOLD) {
            // deriveFont(Font.BOLD) bikin versi tebal dari font tema tanpa bikin Font baru
            lbl.setFont(AppTheme.FONT_BODY.deriveFont(Font.BOLD));
            lbl.setForeground(AppTheme.GOLD);
        } else {
            lbl.setFont(AppTheme.FONT_BODY);
            lbl.setForeground(AppTheme.TEXT_LIGHT);
        }
        lbl.setBackground(AppTheme.colorFor(c.getType())); // warna kartu ngikutin role
        lbl.setOpaque(true);   // WAJIB! JLabel itu transparan bawaannya, tanpa ini background nggak kelihatan
        lbl.setBorder(AppTheme.CARD_BORDER);
        lbl.setToolTipText(AppTheme.describe(c.getType())); // muncul pas mouse diarahkan ke kartu
        return lbl;
    }
}
package com.unpri.charactermanager.view;

import java.awt.BorderLayout;
// import java.awt.Color; kuhapus pak karna ada problems unused import, biar bersih aja
import java.awt.Cursor;
import java.awt.Dimension;
// import java.awt.FlowLayout; dihapus karena sudah tidak dipakai (status bar pakai BorderLayout)
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;

public class MainWindow extends JFrame {

    private ArrayList<Character> characterList;     // data dari Main.java (Model)

    public MainWindow(ArrayList<Character> characterList) {
        super("Character Manager " + AppTheme.APP_VERSION);
        this.characterList = characterList;

        initComponents();                           // 1. susun semua area

        pack();                                     // 2. ukuran awal = preferred size isi
        setMinimumSize(new Dimension(AppTheme.MIN_WIDTH, AppTheme.MIN_HEIGHT)); // 3. batas terkecil
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);                // 4. pusatkan SETELAH pack()
    }

    private void initComponents() {
        getContentPane().setBackground(AppTheme.BG_MAIN);  // warna di balik semua panel
        setLayout(new BorderLayout());                     // kerangka 4 wilayah

        add(createNorthArea(),   BorderLayout.NORTH);      // header + statistik
        add(createListArea(),    BorderLayout.CENTER);     // daftar berscroll: satu-satunya yang tumbuh
        add(createLegendPanel(), BorderLayout.EAST);       // legenda role
        add(createStatusPanel(), BorderLayout.SOUTH);      // bar status
    }

    /** NORTH: header di atas, panel statistik di bawahnya. */
    private JPanel createNorthArea() {
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(AppTheme.BG_MAIN);
        north.add(createHeaderPanel(), BorderLayout.NORTH);
        north.add(createStatsPanel(),  BorderLayout.CENTER);
        return north;
    }

    /** Judul di kiri, jumlah karakter di kanan. */
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.BG_MAIN);
        header.setBorder(AppTheme.HEADER_BORDER);

        JLabel title = new JLabel("Character Manager");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        JLabel count = new JLabel(characterList.size() + " karakter");
        count.setFont(AppTheme.FONT_BODY);
        count.setForeground(AppTheme.GOLD);

        header.add(title, BorderLayout.WEST);               // rata kiri
        header.add(count, BorderLayout.EAST);               // rata kanan
        return header;
    }

    /** Panel statistik 2x2 dengan GridLayout. */
    private JPanel createStatsPanel() {
        int total = characterList.size();
        int maxLevel = 0;
        int sumLevel = 0;
        for (Character c : characterList) {
            maxLevel = Math.max(maxLevel, c.getLevel());
            sumLevel += c.getLevel();
        }
        double avg = total == 0 ? 0 : (double) sumLevel / total;

        JPanel stats = new JPanel(new GridLayout(2, 2, AppTheme.STATS_GAP, AppTheme.STATS_GAP));
        stats.setBackground(AppTheme.BG_MAIN);
        stats.setBorder(AppTheme.STATS_BORDER);

        stats.add(createStatCell("Total karakter", String.valueOf(total)));
        stats.add(createStatCell("Level tertinggi", String.valueOf(maxLevel)));
        stats.add(createStatCell("Rata-rata level", String.format("%.1f", avg)));
        stats.add(createStatCell("Jumlah role", String.valueOf(CharacterType.values().length)));
        return stats;
    }

    /** Satu kotak statistik: label kecil di atas, angka di bawah. */
    private JPanel createStatCell(String label, String value) {
        JPanel cell = new JPanel();
        cell.setLayout(new BoxLayout(cell, BoxLayout.Y_AXIS));
        cell.setBackground(AppTheme.BG_STATUS);
        cell.setBorder(AppTheme.CARD_BORDER);

        JLabel lbl = new JLabel(label);
        lbl.setFont(AppTheme.FONT_MONO);
        lbl.setForeground(AppTheme.TEXT_MUTED);

        JLabel val = new JLabel(value);
        val.setFont(AppTheme.FONT_TITLE);
        val.setForeground(AppTheme.GOLD);

        cell.add(lbl);
        cell.add(val);
        return cell;
    }

    /** CENTER: kartu karakter di dalam JScrollPane. */
    private JScrollPane createListArea() {
        JPanel cards = new JPanel(new GridLayout(0, 1, 0, AppTheme.CARD_GAP));
        cards.setBackground(AppTheme.BG_MAIN);
        for (Character c : characterList) {
            cards.add(createCharacterLabel(c));              // kartu ditambah ke PANEL, bukan ke scroll pane
        }

        // holder: menaruh cards di NORTH agar kartu mempertahankan tinggi alaminya
        JPanel holder = new JPanel(new BorderLayout());
        holder.setBackground(AppTheme.BG_MAIN);
        holder.setBorder(AppTheme.LIST_BORDER);
        holder.add(cards, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(holder);        // bungkus HOLDER
        scroll.setBorder(null);                              // buang border bawaan
        scroll.getViewport().setBackground(AppTheme.BG_MAIN);
        scroll.setPreferredSize(AppTheme.LIST_SIZE);         // cegah jendela raksasa kalau data banyak
        return scroll;
    }

    /** Satu kartu karakter. */
    private JLabel createCharacterLabel(Character c) {
        String info = c.getName() + " | " + c.getType()
                    + " | Lv." + c.getLevel()
                    + " | HP:" + c.getHitPoints()
                    + " | ATK:" + c.getAttackPower();

        JLabel lbl = new JLabel(info);
        lbl.setFont(AppTheme.FONT_BODY);
        lbl.setForeground(AppTheme.TEXT_LIGHT);
        lbl.setBackground(AppTheme.colorFor(c.getType()));
        lbl.setOpaque(true);                                 // wajib agar background tampak
        lbl.setBorder(AppTheme.CARD_BORDER);
        lbl.setToolTipText(AppTheme.describe(c.getType()));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return lbl;
    }

    /** EAST: legenda warna per role, ditumpuk vertikal dengan BoxLayout. */
    private JPanel createLegendPanel() {
        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS)); // target = panel ini sendiri
        legend.setBackground(AppTheme.BG_MAIN);
        legend.setBorder(AppTheme.LEGEND_BORDER);

        JLabel head = new JLabel("Legenda Role");
        head.setFont(AppTheme.FONT_BODY_BOLD);
        head.setForeground(AppTheme.TEXT_MAIN);
        legend.add(head);
        legend.add(Box.createVerticalStrut(AppTheme.LEGEND_GAP)); // spasi tetap

        for (CharacterType type : CharacterType.values()) {  // satu chip per role
            JLabel chip = new JLabel(type.name());
            chip.setOpaque(true);
            chip.setBackground(AppTheme.colorFor(type));
            chip.setForeground(AppTheme.TEXT_LIGHT);
            chip.setFont(AppTheme.FONT_MONO);
            chip.setBorder(AppTheme.CARD_BORDER);
            chip.setToolTipText(AppTheme.describe(type));
            legend.add(chip);
            legend.add(Box.createVerticalStrut(AppTheme.CHIP_GAP));
        }
        legend.add(Box.createVerticalGlue());               // dorong semua ke atas
        return legend;
    }

    /** SOUTH: status rata kiri, versi rata kanan. */
    private JPanel createStatusPanel() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(AppTheme.BG_STATUS);
        status.setBorder(AppTheme.STATUS_BORDER);

        JLabel lbl = new JLabel("Total: " + characterList.size() + " karakter  |  Siap");
        lbl.setFont(AppTheme.FONT_MONO);
        lbl.setForeground(AppTheme.TEXT_MUTED);

        JLabel version = new JLabel(AppTheme.APP_VERSION);
        version.setFont(AppTheme.FONT_MONO);
        version.setForeground(AppTheme.TEXT_MUTED);

        status.add(lbl, BorderLayout.WEST);
        status.add(version, BorderLayout.EAST);
        return status;
    }
}
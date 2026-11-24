package com.unpri.charactermanager.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

public class MainWindow extends JFrame {

    private final CharacterManager manager;   // v0.6: terima MANAGER, bukan salinan list
    private JPanel cardsPanel;                // field, isinya diganti tiap refresh
    private JLabel countLabel;                // jumlah karakter di header
    private JLabel statusLabel;               // teks di bar status

    public MainWindow(CharacterManager manager) {
        super("Character Manager " + AppTheme.APP_VERSION);
        this.manager = manager;

        initComponents();

        pack();
        setMinimumSize(new Dimension(AppTheme.MIN_WIDTH, AppTheme.MIN_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        getContentPane().setBackground(AppTheme.BG_MAIN);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(new CharacterFormPanel(manager, this::refreshList), BorderLayout.WEST); // form input
        add(createListArea(),    BorderLayout.CENTER);
        add(createLegendPanel(), BorderLayout.EAST);
        add(createStatusPanel(), BorderLayout.SOUTH);

        refreshList();   // isi daftar pertama kali, harus PALING AKHIR setelah semua komponen dibuat
    }

    /** Bangun ulang kartu dari data Model, lalu perbarui semua penghitung. */
    private void refreshList() {
        cardsPanel.removeAll();                          // buang kartu lama
        for (Character c : manager.getAllCharacters()) {
            cardsPanel.add(createCharacterLabel(c));     // kartu dari data terbaru
        }
        cardsPanel.revalidate();                         // layout dihitung ulang
        cardsPanel.repaint();                            // gambar ulang

        int total = manager.getTotalCharacters();
        countLabel.setText(total + " karakter");
        statusLabel.setText("Total: " + total + " karakter  |  Siap");
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppTheme.BG_MAIN);
        header.setBorder(AppTheme.HEADER_BORDER);

        JLabel title = new JLabel("Character Manager");
        title.setFont(AppTheme.FONT_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        countLabel = new JLabel(" ");                    // diisi oleh refreshList()
        countLabel.setFont(AppTheme.FONT_BODY);
        countLabel.setForeground(AppTheme.GOLD);

        header.add(title, BorderLayout.WEST);
        header.add(countLabel, BorderLayout.EAST);
        return header;
    }

    private JScrollPane createListArea() {
        cardsPanel = new JPanel(new GridLayout(0, 1, 0, AppTheme.CARD_GAP));
        cardsPanel.setBackground(AppTheme.BG_MAIN);

        JPanel holder = new JPanel(new BorderLayout());
        holder.setBackground(AppTheme.BG_MAIN);
        holder.setBorder(AppTheme.LIST_BORDER);
        holder.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(holder);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(AppTheme.BG_MAIN);
        scroll.setPreferredSize(AppTheme.LIST_SIZE);
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
        lbl.setOpaque(true);                             // wajib biar background kelihatan
        lbl.setBorder(AppTheme.CARD_BORDER);
        lbl.setToolTipText(AppTheme.describe(c.getType()));
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return lbl;
    }

    /** EAST: legenda warna per role, ditumpuk vertikal pakai BoxLayout. */
    private JPanel createLegendPanel() {
        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS)); // target = panel ini sendiri
        legend.setBackground(AppTheme.BG_MAIN);
        legend.setBorder(AppTheme.LEGEND_BORDER);

        JLabel head = new JLabel("Legenda Role");
        head.setFont(AppTheme.FONT_BODY_BOLD);
        head.setForeground(AppTheme.TEXT_MAIN);
        legend.add(head);
        legend.add(Box.createVerticalStrut(AppTheme.LEGEND_GAP));

        for (CharacterType type : CharacterType.values()) {   // satu chip per role
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
        legend.add(Box.createVerticalGlue());            // dorong semua ke atas
        return legend;
    }

    private JPanel createStatusPanel() {
        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        status.setBackground(AppTheme.BG_STATUS);
        status.setBorder(AppTheme.STATUS_BORDER);

        statusLabel = new JLabel(" ");                   // diisi oleh refreshList()
        statusLabel.setFont(AppTheme.FONT_MONO);
        statusLabel.setForeground(AppTheme.TEXT_MUTED);
        status.add(statusLabel);
        return status;
    }
}
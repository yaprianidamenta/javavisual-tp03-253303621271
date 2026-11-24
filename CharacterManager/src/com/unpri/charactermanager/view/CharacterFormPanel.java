package com.unpri.charactermanager.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.text.ParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

/** Form "Tambah Karakter": baca input, validasi, lalu serahkan ke Model. */
public class CharacterFormPanel extends JPanel {

    // Aturan bentuk nama (urusan View), dijadiin konstanta biar nggak ada angka nyelip
    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 20;
    private static final String NAME_PATTERN = "[A-Za-z ]+";
    private static final String DOUBLE_SPACE = "  ";   // dua spasi berurutan
    private static final int NAME_FIELD_COLUMNS = 14;
    private static final String MSG_NAME_TAKEN = "Nama sudah dipakai";

    // Batas tombol panah spinner. Aturan aslinya tetap dijaga Model (konstruktor Character)
    private static final int LEVEL_START = 1;
    private static final int LEVEL_MIN = 1;
    private static final int LEVEL_MAX = 99;
    private static final int LEVEL_STEP = 1;

    private final CharacterManager manager;   // tujuan data yang lolos validasi
    private final Runnable onAdded;           // dipanggil setelah sukses, buat refresh daftar

    // Komponen input jadi field karena dibaca di method lain
    private final JTextField nameField = new JTextField(NAME_FIELD_COLUMNS);
    private final JComboBox<CharacterType> roleBox = new JComboBox<>(CharacterType.values());
    private final JSpinner levelSpinner =
            new JSpinner(new SpinnerNumberModel(LEVEL_START, LEVEL_MIN, LEVEL_MAX, LEVEL_STEP));
    private final JLabel messageLabel = new JLabel(" ");  // spasi, bukan "", biar tinggi label stabil

    private final Border nameBorder;    // border asli, disimpan buat dipulihkan
    private final Border levelBorder;

    public CharacterFormPanel(CharacterManager manager, Runnable onAdded) {
        this.manager = manager;
        this.onAdded = onAdded;
        this.nameBorder = nameField.getBorder();     // simpan SEBELUM pernah diganti merah
        this.levelBorder = levelSpinner.getBorder();

        setLayout(new BorderLayout());
        setBackground(AppTheme.BG_MAIN);
        setBorder(AppTheme.FORM_BORDER);
        setPreferredSize(AppTheme.FORM_SIZE);

        JLabel title = new JLabel("Tambah Karakter");
        title.setFont(AppTheme.FONT_FORM_TITLE);
        title.setForeground(AppTheme.TEXT_MAIN);

        messageLabel.setFont(AppTheme.FONT_BODY);

        JButton saveButton = new JButton("Simpan");
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> onSave());   // sementara, dibahas penuh di Pertemuan 9
        nameField.addActionListener(e -> onSave());    // Enter di kolom nama = Simpan

        JPanel rows = new JPanel(new GridLayout(0, 1, 0, AppTheme.FORM_ROW_GAP));
        rows.setOpaque(false);                         // transparan, warna panel induk tetap kelihatan
        rows.add(title);
        rows.add(makeLabel("Nama:", nameField));
        rows.add(nameField);
        rows.add(makeLabel("Role:", roleBox));
        rows.add(roleBox);
        rows.add(makeLabel("Level:", levelSpinner));
        rows.add(levelSpinner);
        rows.add(messageLabel);
        rows.add(saveButton);
        add(rows, BorderLayout.NORTH);                 // NORTH biar tingginya nggak melar
    }

    private JLabel makeLabel(String text, JComponent target) {
        JLabel label = new JLabel(text);
        label.setFont(AppTheme.FONT_BODY);
        label.setForeground(AppTheme.TEXT_MAIN);
        label.setLabelFor(target);                     // kaitkan label dengan komponen inputnya
        return label;
    }

    /** Dipanggil saat Simpan ditekan: baca, bersihkan, validasi, lalu serahkan ke Model. */
    private void onSave() {
        clearMessage();                                // hapus sisa error sebelumnya

        String name = nameField.getText().trim();      // baca + buang spasi tepi
        String problem = validateName(name);
        if (problem != null) {
            showError(problem, nameField);             // tampilkan pesan + tandai field
            nameField.requestFocusInWindow();          // kursor balik ke field bermasalah
            return;                                    // WAJIB return, kalau nggak data jelek tetap masuk
        }

        try {
            levelSpinner.commitEdit();                 // pastiin angka yang diketik sudah dikomit
        } catch (ParseException ex) {
            showError("Level harus berupa angka", levelSpinner);
            focusLevel();
            return;
        }

        CharacterType role = (CharacterType) roleBox.getSelectedItem(); // cast Object ke enum
        int level = (Integer) levelSpinner.getValue();                  // cast Object ke Integer

        boolean added;
        try {
            added = manager.addCharacter(new Character(name, role, level)); // Model jaga aturannya sendiri
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage(), levelSpinner);  // pesan dari Model dipakai apa adanya
            focusLevel();
            return;
        }
        if (!added) {                                  // jaga-jaga kalau Manager menolak
            showError(MSG_NAME_TAKEN, nameField);
            nameField.requestFocusInWindow();
            return;
        }

        nameField.setText("");                         // sukses: kosongkan form
        roleBox.setSelectedIndex(0);
        levelSpinner.setValue(LEVEL_START);
        showSuccess("Tersimpan: " + name);
        nameField.requestFocusInWindow();
        onAdded.run();                                 // kabari MainWindow supaya daftar disegarkan
    }

    /** Balikin pesan error pertama, atau null kalau nama lolos. Cuma MEMERIKSA, nggak nampilin. */
    private String validateName(String name) {
        if (name.isEmpty()) {
            return "Nama wajib diisi";                                   // 1. kelengkapan
        }
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            return "Nama harus " + NAME_MIN_LENGTH + "-" + NAME_MAX_LENGTH + " karakter"; // 2. panjang
        }
        if (!name.matches(NAME_PATTERN)) {
            return "Nama hanya huruf dan spasi";                         // 3. format
        }
        if (name.contains(DOUBLE_SPACE)) {
            return "Nama tidak boleh memakai spasi ganda";               // 3b. format (aturan baru)
        }
        if (manager.isNameTaken(name)) {
            return MSG_NAME_TAKEN;                                       // 4. keunikan (tanya Model)
        }
        return null;
    }

    /** Pindahin kursor ke kolom angka di dalam spinner. */
    private void focusLevel() {
        JComponent editor = levelSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().requestFocusInWindow();
        }
    }

    private void showError(String text, JComponent source) {
        messageLabel.setText(text);
        messageLabel.setForeground(AppTheme.ERROR);
        // border merah jadi tanda selain warna teks
        source.setBorder(BorderFactory.createLineBorder(AppTheme.ERROR, AppTheme.ERROR_BORDER_WIDTH));
    }

    private void showSuccess(String text) {
        messageLabel.setText(text);
        messageLabel.setForeground(AppTheme.SUCCESS);
    }

    private void clearMessage() {
        messageLabel.setText(" ");
        nameField.setBorder(nameBorder);               // pulihkan border asli
        levelSpinner.setBorder(levelBorder);
    }
}
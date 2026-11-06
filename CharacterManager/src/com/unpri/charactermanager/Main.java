package com.unpri.charactermanager;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.view.MainWindow;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // 1. Siapkan DATA terlebih dahulu (layer Model)
        CharacterManager manager = new CharacterManager();
        manager.addCharacter(new Character("Arjuna", CharacterType.WARRIOR, 5));
        manager.addCharacter(new Character("Merlin", CharacterType.MAGE, 3));
        manager.addCharacter(new Character("Athena", CharacterType.ARCHER, 4));
        manager.addCharacter(new Character("Gatotkaca", CharacterType.WARRIOR, 8)); //level tinggi
        manager.addCharacter(new Character("Morgana", CharacterType.MAGE, 6));
        manager.addCharacter(new Character("Artemis", CharacterType.WARRIOR, 7)); 
        manager.addCharacter(new Character("Bima", CharacterType.MAGE, 1));      // level rendah
        manager.addCharacter(new Character("Circe", CharacterType.MAGE, 2));

        CharacterType[] types = CharacterType.values();
        for (int i = 1; i <= 32; i++) {
        manager.addCharacter(new Character("Hero" + i, types[i % types.length], (i % 8) + 1));
        }

        // 2. Jalankan GUI dengan AMAN di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow(manager.getAllCharacters());
            window.setVisible(true);    // WAJIB
        });
    }
}
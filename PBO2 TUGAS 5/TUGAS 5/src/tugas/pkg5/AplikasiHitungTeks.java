/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tugas.pkg5;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AplikasiHitungTeks extends JFrame {
    private JTextArea textArea;
    private JLabel lblJumlahKata, lblJumlahKarakter, lblJumlahKalimat, lblJumlahParagraf, lblPetunjuk;
    private JTextField txtCari;
    private JButton btnHitung, btnSimpan, btnCari;

    public AplikasiHitungTeks() {
        setTitle("Aplikasi Hitung Teks");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panelHasil = new JPanel(new GridLayout(5, 1, 5, 5));
        JPanel panelPencarian = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Label petunjuk
        lblPetunjuk = new JLabel("Masukkan teks di bawah ini dan tekan 'Hitung' untuk melihat hasilnya:");
        mainPanel.add(lblPetunjuk, BorderLayout.NORTH);
        
        // JTextArea untuk input teks
        textArea = new JTextArea(15, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button untuk menghitung
        btnHitung = new JButton("Hitung");
        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungTeks();
            }
        });

        // Button untuk menyimpan
        btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanKeFile();
            }
        });

        // Label untuk hasil
        lblJumlahKata = new JLabel("Jumlah Kata: 0");
        lblJumlahKarakter = new JLabel("Jumlah Karakter: 0");
        lblJumlahKalimat = new JLabel("Jumlah Kalimat: 0");
        lblJumlahParagraf = new JLabel("Jumlah Paragraf: 0");

        panelHasil.add(lblJumlahKata);
        panelHasil.add(lblJumlahKarakter);
        panelHasil.add(lblJumlahKalimat);
        panelHasil.add(lblJumlahParagraf);

        // TextField dan button untuk pencarian
        txtCari = new JTextField(15);
        btnCari = new JButton("Cari");
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cariKata();
            }
        });

        panelPencarian.add(new JLabel("Cari Kata: "));
        panelPencarian.add(txtCari);
        panelPencarian.add(btnCari);

        // Menambahkan tombol ke panelButton
        panelButton.add(btnHitung);
        panelButton.add(btnSimpan);

        // Menambahkan komponen ke frame
        add(mainPanel, BorderLayout.NORTH);
        add(panelHasil, BorderLayout.CENTER);
        add(panelPencarian, BorderLayout.SOUTH);
        add(panelButton, BorderLayout.PAGE_END);

        // DocumentListener untuk menghitung secara real-time
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                hitungTeks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hitungTeks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                hitungTeks();
            }
        });
    }

    private void hitungTeks() {
        String teks = textArea.getText().trim();
        int jumlahKata = teks.isEmpty() ? 0 : teks.split("\\s+").length;
        int jumlahKarakter = teks.length();
        int jumlahKalimat = teks.isEmpty() ? 0 : teks.split("[.!?]").length;
        int jumlahParagraf = teks.isEmpty() ? 0 : teks.split("\\n").length;

        lblJumlahKata.setText("Jumlah Kata: " + jumlahKata);
        lblJumlahKarakter.setText("Jumlah Karakter: " + jumlahKarakter);
        lblJumlahKalimat.setText("Jumlah Kalimat: " + jumlahKalimat);
        lblJumlahParagraf.setText("Jumlah Paragraf: " + jumlahParagraf);
    }

    private void cariKata() {
        String kataDicari = txtCari.getText().trim();
        String teks = textArea.getText();
        if (teks.contains(kataDicari)) {
            JOptionPane.showMessageDialog(this, "Kata '" + kataDicari + "' ditemukan!");
        } else {
            JOptionPane.showMessageDialog(this, "Kata '" + kataDicari + "' tidak ditemukan.");
        }
    }

    private void simpanKeFile() {
        JFileChooser fileChooser = new JFileChooser();
        int pilihan = fileChooser.showSaveDialog(this);
        if (pilihan == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Teks yang dimasukkan:\n");
                writer.write(textArea.getText() + "\n\n");
                writer.write(lblJumlahKata.getText() + "\n");
                writer.write(lblJumlahKarakter.getText() + "\n");
                writer.write(lblJumlahKalimat.getText() + "\n");
                writer.write(lblJumlahParagraf.getText() + "\n");
                JOptionPane.showMessageDialog(this, "Hasil perhitungan disimpan ke " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan ke file: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplikasiHitungTeks app = new AplikasiHitungTeks();
            app.setVisible(true);
        });
    }
}

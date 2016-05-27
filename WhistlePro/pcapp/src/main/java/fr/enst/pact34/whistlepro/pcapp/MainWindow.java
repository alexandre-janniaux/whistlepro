package fr.enst.pact34.whistlepro.pcapp;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;

class MainWindow extends JFrame {


    public static void main(String[] args) {
        System.out.println("Yoo");
        MainWindow window = new MainWindow();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //conteneur principal
        JPanel mainPannel = new JPanel();
        mainPannel.setLayout(new BorderLayout());

        JButton btnOpen = new JButton("open");
        mainPannel.add(btnOpen, BorderLayout.NORTH);

        JPanel dataViewer = new DataViewer();
        mainPannel.add(dataViewer, BorderLayout.CENTER);

        mainPannel.setBorder(new EmptyBorder(5, 5, 5, 5));

        //ajout du contenu a la fenetre
        window.setContentPane(mainPannel);
        window.pack();

        System.out.println("Yoo");
        window.setVisible(true);
        System.out.println("Yoo");
    }

}

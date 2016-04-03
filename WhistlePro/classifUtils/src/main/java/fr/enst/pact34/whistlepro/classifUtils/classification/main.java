package fr.enst.pact34.whistlepro.classifUtils.classification;

import fr.enst.pact34.whistlepro.classifUtils.classification.Panels.mfccPanel;

import java.awt.*;

import javax.swing.*;

public class main {


    public static void main(String[] args) {

        JFrame mainWindow = new mainWindow();
        mainWindow.setVisible(true);
    }

    private static class mainWindow extends  JFrame
    {
        public mainWindow() {
            super("Classification learning tools");

            this.setSize(320, 150);
            this.setResizable(true);

            JPanel pannel = new JPanel();
            pannel.setLayout(new GridLayout());

            JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);

            onglets.addTab("MFCC db manager", new mfccPanel());
            onglets.addTab("Classifier Learner tool", buildLearnPanel());
            onglets.addTab("Classifieur User tool", buildClassifPanel());

            onglets.setOpaque(true);
            pannel.add(onglets);
            this.setContentPane(pannel);
        }

        private JPanel buildLearnPanel()
        {
            JPanel classifLearn = new JPanel();
            JLabel titreClassifLearn = new JLabel("Apprentissage classifieur");
            classifLearn.add(titreClassifLearn);
            return classifLearn;
        }


        private JPanel buildClassifPanel()
        {
            JPanel classifLearn = new JPanel();
            JLabel titreClassifLearn = new JLabel("Classifieur");
            classifLearn.add(titreClassifLearn);
            return classifLearn;
        }
    }



}

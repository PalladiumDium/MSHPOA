package controller;

import model.DataStationMeteo;
import model.Mesure;
import parser.TestReader;
import view.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


public class MainFrameController {

    private MainFrame mainFrame;
    private JButton file;
    private JTextPane display;
    private ArrayList<DataStationMeteo> listStations;
    private ArrayList<Mesure> mesures;

    public MainFrameController() {
        mainFrame = new MainFrame();

        file = mainFrame.getFile();
        display = mainFrame.getDisplay();
        initView();
        initListener();

    }

    private void initListener() {
        file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returValue = jfc.showOpenDialog(null);

               if (returValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    TestReader tr = new TestReader(selectedFile.getAbsolutePath());
                    listStations = DataStationMeteo.initFromListJson(tr.getData());
                    updateView();
                }

            }
        });
    }

    private void updateView() {

        String strOut = "Il y à " + listStations.size() + " stations météo";

        mesures = new ArrayList<>();
        for (DataStationMeteo station :
                listStations) {
            mesures.addAll(station.getMesures());

        }
        for (int i = 0; i < mesures.size(); i++) {


        }
        
        strOut += getMax(mesures);
        strOut += getMin(mesures);
        strOut += getMoy(mesures);
        strOut += getErrors(mesures);

        display.setText(strOut);
    }

    private String getErrors(ArrayList<Mesure> mesures) {
        int counterror = 0;
        for (Mesure mesu :
                mesures) {
            switch (mesu.getType().toLowerCase()) {
                case "pression":
                    if (mesu.getUnite().equalsIgnoreCase("bar")) {
                        if (mesu.getValue() < 0.870 || mesu.getValue() > 1.090) {
                            counterror++;
                        }
                    } else if (mesu.getUnite().equalsIgnoreCase("hpa")) {
                        if (mesu.getValue() < 870.0 || mesu.getValue() > 1090.0) {
                            counterror++;
                        }
                    }
                    break;
                case "humidite":
                    if (mesu.getValue() < 15.0 || mesu.getValue() > 100.0) {
                        counterror++;
                    }
                    break;
                case "temperature":
                    if (mesu.getValue() < -90.0 || mesu.getValue() > 55.0) {
                        counterror++;
                    }
                    break;
            }
        }

        System.out.println("Le nombre de capteur considerés comme en erreur est : " + counterror);
        return "\n \n\n\n Le nombre de capteur considerés comme en erreur est : " + counterror;
    }

    private String getMoy(ArrayList<Mesure> mesures) {
        double countT = 0.0, countP = 0.0, countH = 0.0;
        double  totT = 0, totP = 0, totH = 0;

        for (Mesure mesu :
                mesures) {
         switch (mesu.getType().toLowerCase()) {
             case "pression" :
                 countP++;
                 totP += mesu.getValue();
                 break;
             case "humidite":
                 countH++;
                 totH += mesu.getValue();
                 break;
             case "temperature":
                 countT++;
                 totT += mesu.getValue();
                 break;
         }
        }

        System.out.println("La valeur moyenne des capteurs pression est : " + totP/countP + "Bar" + "\n La valeur moyenne des capteurs temperature est : "+ totT/countT + "C" + "\n La valeur moyenne des capteurs d'humidité est : " + totH/countH + " % ");
        return "\n La valeur moyenne des capteurs pression est : " + totP/countP + "Bar" + "\n La valeur moyenne des capteurs temperature est : "+ totT/countT + "C" + "\n La valeur moyenne des capteurs d'humidité est : " + totH/countH + " % ";
    }

    private String getMin(ArrayList<Mesure> mesures) {
        double minT = 0.0, minP = 0.0, minH = 0.0, tmp;
        for (Mesure mesu :
                mesures) {
           // System.out.println(mesu.getType() + mesu.getValue() + " TEEEEEEEEEEEEEEEEEEEEE");
            switch (mesu.getType().toLowerCase()) {
                case "pression" :

                    if (minP > mesu.getValue()){
                        minP = mesu.getValue();
                    } else if (minP == 0.0) {
                        minP = mesu.getValue();
                    }
                    break;
                case "humidite":

                    if (minH > mesu.getValue()){
                        minH = mesu.getValue();
                    } else if (minH == 0.0) {
                        minH = mesu.getValue();
                    }
                    break;
                case "temperature":

                    if (minT > mesu.getValue()){
                        minT = mesu.getValue();
                    } else if (minT == 0.0) {
                        minT = mesu.getValue();
                    }
                    break;
            }
        }
        System.out.println("La valeur minimum des capteurs pression est : " + minP + "Bar" + "\n La valeur minimum des capteurs temperature est : "+ minT + "C" + "\n La valeur minimum des capteurs d'humidité est : " + minH + " % ");

        return "\n La valeur minimum des capteurs pression est : " + minP + "Bar" + "\n La valeur minimum des capteurs temperature est : "+ minT + "C" + "\n La valeur minimum des capteurs d'humidité est : " + minH + " % ";
    }

    private String getMax(ArrayList<Mesure> mesures) {
        double maxT = 0.0, maxP = 0.0, maxH = 0.0;
        for (Mesure mesu :
                mesures) {
            switch (mesu.getType()) {
                case "pression" :
                    //System.out.println(" PRession " + mesu.getValue());
                    if (maxP < mesu.getValue()){
                        maxP = mesu.getValue();
                    } else if (maxP == 0.0) {
                        maxP = mesu.getValue();
                    }
                    break;
                case "humidite":
                    if (maxH < mesu.getValue()){
                        maxH = mesu.getValue();
                    } else if (maxH == 0.0) {
                        maxH = mesu.getValue();
                    }

                    break;
                case "temperature":
                    if (maxT < mesu.getValue()){
                        maxT = mesu.getValue();
                    } else if (maxT == 0.0) {
                        maxT = mesu.getValue();
                    }
                    break;
            }
        }
        System.out.println("La valeur maximum des capteurs pression est : " + maxP + "Bar" + "\n La valeur maximum des capteurs temperature est : "+ maxT + "C" + "\n La valeur maximum des capteurs d'humidité est : " + maxH + " % ");
        return "\n La valeur maximum des capteurs pression est : " + maxP + "Bar" + "\n La valeur maximum des capteurs temperature est : "+ maxT + "C" + "\n La valeur maximum des capteurs d'humidité est : " + maxH + " % ";
    }

    private void initView() {
        mainFrame.setName("Mdsdsdch meteo report");
        display.setText("Veuillez charger un fichier");
    }

    public void show(){
        mainFrame.setVisible(true);

    }
}

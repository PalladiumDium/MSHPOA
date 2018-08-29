package app;

import controller.MainFrameController;
import parser.TestReader;
import view.MainFrame;

public class AppMain {

    public static void main(String[] args) {
        //Lancement de l'app avec instantiation du controller et ouverture de la fenetre
        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.show();
    }
}

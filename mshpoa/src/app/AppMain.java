package app;

import controller.MainFrameController;
import parser.TestReader;
import view.MainFrame;

public class AppMain {

    public static void main(String[] args) {

        MainFrameController mainFrameController = new MainFrameController();
        mainFrameController.show();
    }
}

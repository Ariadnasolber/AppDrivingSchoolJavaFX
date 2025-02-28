package view.gui;

import view.console.*;
import exceptions.cumplirEdadEx;
import exceptions.formatoDniEx;
import exceptions.formatoEmailEx;
import exceptions.formatoErrorEx;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import model.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//imports interfaz grafica
import view.gui.JFrameMain;

public class Main {

    public static void main(String[] args) {

        //conectar interfaz grafica
        JFrameMain panta = new JFrameMain();
        panta.setVisible(true);
        panta.setLocationRelativeTo(null);



      

    }
}

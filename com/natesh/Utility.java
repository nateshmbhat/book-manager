package com.natesh;

import javax.swing.*;
import java.awt.*;

public class Utility {
    public static void showInfoMessage(String msg , Component parent){
        JOptionPane.showMessageDialog(parent , msg , "Success" , JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessage(String msg , Component parent){
        JOptionPane.showMessageDialog(parent , msg , "Error" , JOptionPane.ERROR_MESSAGE);
    }

    public static void showWarningMessage(String msg , Component parent){
        JOptionPane.showMessageDialog(parent , msg , "Warning" , JOptionPane.WARNING_MESSAGE);
    }
}

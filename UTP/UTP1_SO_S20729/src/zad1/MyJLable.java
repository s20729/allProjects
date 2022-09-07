package zad1;

import javax.swing.*;

public class MyJLable extends JLabel {
    String path;

    MyJLable(String path){
        this.path = path;
        setIcon(new ImageIcon(path));
    }
}

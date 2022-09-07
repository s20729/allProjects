package web1;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class Wiki extends JFrame {
    String miasto;

    public Wiki(String miasto){
        this.miasto = miasto;

        JFXPanel jfxPanel = new JFXPanel();
        this.add(jfxPanel);

        Platform.runLater(() ->{
            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();
            engine.load("https://en.wikipedia.org/wiki/" + miasto);
            jfxPanel.setScene(new Scene(webView));
        });

        setPreferredSize(new Dimension(1000,1000));
        setDefaultCloseOperation(Wiki.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
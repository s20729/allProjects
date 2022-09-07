package web1;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Okno extends JFrame {

    public Service service;

    public Okno(String firstMes){
        setLayout(new GridBagLayout());
        JLabel jLabel = new JLabel(firstMes);
        JTextField kraj = new JTextField("Napisz nazwe Kraju zamiast tego tekstu");
        JTextField miasto = new JTextField("Napisz nazwe miasta zamiast tego tekstu");
        JTextField waluta = new JTextField("Napisz nazwe waluty zamiast tego tekstu");
        JButton pogoda = new JButton("Pogoda");
        JButton kurs = new JButton("Kurs");
        JButton wiki = new JButton("Wiki");

        pogoda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service = new Service(kraj.getText());
                jLabel.setText(service.getWeather(miasto.getText()));
            }
        });

        kurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel.setText(jLabel.getText() + "        " + service.getRateFor(waluta.getText()) + " "
                        + service.money + " do " + waluta.getText() + "            "
                        +service.getNBPRate() + " " + service.doWaluty + " do" + " PLN");

            }
        });

        wiki.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(
                        ()-> new Wiki(miasto.getText())
                );
            }
        });


        Panel panel = new Panel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(pogoda);
        panel.add(kurs);
        panel.add(wiki);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx=0.9;
        gbc.weighty=0.7;
        gbc.gridwidth=4;
        gbc.fill =  GridBagConstraints.BOTH;
        jLabel.setAlignmentY(JTextField.TOP);
        getContentPane().add(jLabel, gbc);

        gbc.gridwidth=1;

        gbc.weightx=0.25;
        gbc.weighty=0.2;
        gbc.gridy =1;
        getContentPane().add(kraj, gbc);

        gbc.weightx=0.24;
        gbc.weighty=0.2;
        gbc.gridy =1;
        gbc.gridx=1;
        getContentPane().add(miasto, gbc);

        gbc.weightx=0.25;
        gbc.weighty=0.2;
        gbc.gridy =1;
        gbc.gridx=2;
        getContentPane().add(waluta, gbc);

        gbc.weightx=0.25;
        gbc.weighty=0.2;
        gbc.gridy =1;
        gbc.gridx=3;
        getContentPane().add(panel, gbc);


        setSize(new Dimension(1080, 720));
        setDefaultCloseOperation(Okno.EXIT_ON_CLOSE);
        setVisible(true);

    }

}
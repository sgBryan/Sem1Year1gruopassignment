/* CarbonFootprintCalculator class represents a GUI-based carbon footprint calculator.
   It extends JFrame and provides a user interface to input data related to transportation,
   energy consumption, and food consumption to calculate and display the carbon footprint.*/

//package OOP_Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CarbonFootprintCalculator extends JFrame {
    private JTextField txtTransport, txtElectricity, txtFood;
    private JLabel lblResult;
    private double lastCalculatedFootprint = 0;

    // Constructor initializes the frame, layout, and components of the carbon footprint calculator
    public CarbonFootprintCalculator() {
        setTitle("Carbon Footprint Calculator"); // Set the title of the frame
        setSize(700, 300); // Set the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        setLayout(new GridLayout(8, 2)); // Use a 8x2 grid layout

        // Add labels and text fields for input data (transportation, electricity, and food consumption)
        add(new JLabel("Transportation mode (kilometers):"));
        txtTransport = new JTextField();
        add(txtTransport);

        add(new JLabel("Energy consumption (kilowatt-hours):"));
        txtElectricity = new JTextField();
        add(txtElectricity);

        add(new JLabel("Food consumption (kilograms):"));
        txtFood = new JTextField();
        add(txtFood);

        // Add a button to calculate carbon footprint
        JButton btnCalculate = new JButton("Calculate carbon footprint");
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFootprint();
            }
        });
        add(btnCalculate);

        // Add a label to display the result (carbon footprint)
        lblResult = new JLabel("Results: ");
        add(lblResult);

        // Add a button to show suggestions based on the calculated carbon footprint
        JButton btnShowAdvice = new JButton("Suggestions");
        btnShowAdvice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAdviceBasedOnFootprint(lastCalculatedFootprint);
            }
        });
        add(btnShowAdvice);

        // Add a button to open Google Scholar link about SDG 13
        JButton btnOpenGoogleScholar = new JButton("Read more about SDG 13");
        btnOpenGoogleScholar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGoogleScholar();
            }
        });
        add(btnOpenGoogleScholar);

        // Add a button to exit the application
        JButton btnExit = new JButton("exit");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        add(btnExit);

        setVisible(true); // Set the frame as visible
    }

    // Method to calculate the carbon footprint based on user input
    private void calculateFootprint() {
        
        try {
            double transport = Double.parseDouble(txtTransport.getText());
            double electricity = Double.parseDouble(txtElectricity.getText());
            double food = Double.parseDouble(txtFood.getText());

            // Calculate the carbon footprint using specified coefficients
            lastCalculatedFootprint = transport * 0.21 + electricity * 0.5 + food * 0.13;
            // Display the calculated carbon footprint
            lblResult.setText("Your carbon footprint is: " + lastCalculatedFootprint + " kilograms of CO2");
        } catch (NumberFormatException e) {
            // Display an error message for incorrect data format
            JOptionPane.showMessageDialog(this, "Please insert a correct form of data.");
        }
    }

    // Method to show advice/suggestions based on the calculated carbon footprint
    private void showAdviceBasedOnFootprint(double footprint) {
    int columns = 5; 

    JTextArea textArea = new JTextArea();
    textArea.setColumns(columns);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false); // set the textarea as not-editable

    if (footprint <= 4000) {
        // Display advice for below-average carbon footprint
        textArea.setText("Your carbon footprint is considered below average, keep up the effort to further decrease your carbon footprint. Consider participating in more recycling activities and share your experience with your friends and family.");
    } else if (footprint <= 10000) {
        // Display advice for average carbon footprint
        textArea.setText("Your carbon footprint is considered average in the world. Consider joining carpool activity, use energy-efficient appliances and reduce the intake of meat products.");
    } else {
        // Display advice for above-average carbon footprint
        textArea.setText("Your carbon footprint is considered above average. Take into consideration to switch up your daily carbon usage. Switch off electric appliances and turn off water taps when not in used, consider the idea of taking public transportation and limit the intake of meat products.");
    }

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 100)); 

    JOptionPane.showMessageDialog(this, scrollPane, "Advice", JOptionPane.INFORMATION_MESSAGE);
}




    // Method to open Google Scholar link about SDG 13 in the default web browser
    private void openGoogleScholar() {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                // Open the Google Scholar link about SDG 13
                desktop.browse(new URI("https://sdgs.un.org/goals/goal13"));
            } catch (IOException | URISyntaxException e) {
                // Display an error message if unable to load the link
                JOptionPane.showMessageDialog(this, "Unable to load.");
            }
        } 
    }
}
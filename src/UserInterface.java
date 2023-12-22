/* UserInterface class representing the user interface after successful login. It extends JFrame
   and provides options for opening the Carbon Footprint Calculator, viewing data, and logging out. */

//package OOP_Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class UserInterface extends JFrame {
    private JButton openCalculatorButton, viewDataButton, viewDataButtonWithKeyword, logoutButton;
    private LoginFrame loginFrame;  // Reference to the LoginFrame

    // Constructor for UserInterface, receives a reference to the LoginFrame
    public UserInterface(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        setTitle("User Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        // Create buttons for opening the Carbon Footprint Calculator, viewing data, and logging out
        openCalculatorButton = new JButton("Open Carbon Footprint Calculator");
        viewDataButton = new JButton("View Data");
        viewDataButtonWithKeyword = new JButton("View Data with Keyword");
        logoutButton = new JButton("Log Out");

        // Add action listeners to the buttons to perform specific actions when clicked
        openCalculatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCarbonFootprintCalculator();
            }
        });

        viewDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDataButtonClicked();
            }
        });

        viewDataButtonWithKeyword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewDataButtonWithKeywordClicked();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutButtonClicked();
            }
        });

        // Add buttons to the panel
        panel.add(openCalculatorButton);
        panel.add(viewDataButton);
        panel.add(viewDataButtonWithKeyword);
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }

    // Method to open the Carbon Footprint Calculator and dispose of the current frame
    private void openCarbonFootprintCalculator() {
        this.dispose();
        CarbonFootprintCalculator carbonFootprintCalculator = new CarbonFootprintCalculator();
        carbonFootprintCalculator.setVisible(true);
    }

    // Method to handle view data button click, displays data in a list
    private void viewDataButtonClicked() {
        List<AdminInterface.UrlInfo> urlList = AdminInterface.getSharedUrlList();

        if (urlList != null && !urlList.isEmpty()) {
            displayData(urlList);
        } else {
            JOptionPane.showMessageDialog(this, "No data available.");
        }
    }

    // Method to handle view data button with keyword click, displays filtered data in a list
    private void viewDataButtonWithKeywordClicked() {
        List<AdminInterface.UrlInfo> urlList = AdminInterface.getSharedUrlList();

        if (urlList != null && !urlList.isEmpty()) {
            // Get user input for search
            String searchKeyword = JOptionPane.showInputDialog(this, "Enter search keyword:");

            if (searchKeyword != null) {
                // Filter data based on search keyword
                List<AdminInterface.UrlInfo> filteredList = filterData(urlList, searchKeyword);

                if (!filteredList.isEmpty()) {
                    displayData(filteredList);
                } else {
                    JOptionPane.showMessageDialog(this, "No matching data found.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No data available.");
        }
    }

    // Method to display data in a list
    private void displayData(List<AdminInterface.UrlInfo> data) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (AdminInterface.UrlInfo urlInfo : data) {
            listModel.addElement(urlInfo.getName());
        }

        JList<String> dataList = new JList<>(listModel);
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        dataList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    int index = dataList.locationToIndex(evt.getPoint());
                    String selectedUrl = data.get(index).getUrl();
                    openWebpage(selectedUrl);
                }
            }
        });

        JOptionPane.showMessageDialog(this, new JScrollPane(dataList), "View Data", JOptionPane.PLAIN_MESSAGE);
    }

    // Method to filter data based on search keyword
    private List<AdminInterface.UrlInfo> filterData(List<AdminInterface.UrlInfo> data, String searchKeyword) {
        List<AdminInterface.UrlInfo> filteredList = new ArrayList<>();

        for (AdminInterface.UrlInfo urlInfo : data) {
            if (urlInfo.getName().toLowerCase().contains(searchKeyword.toLowerCase())) {
                filteredList.add(urlInfo);
            }
        }

        return filteredList;
    }

    // Method to open a webpage using the default web browser
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            JOptionPane.showMessageDialog(this, "Cannot open the webpage: " + url);
        }
    }

    // Method to handle logout button click, disposes of the current frame and opens the login window
    private void logoutButtonClicked() {
        this.dispose();// Close the current window
        loginFrame.resetLoginFrame(); // Open the login window
    }
}
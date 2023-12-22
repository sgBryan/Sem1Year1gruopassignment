/* AdminInterface class representing the interface for URL management. It extends JFrame and provides
   functionality for uploading, editing, deleting URLs, and checking URLs. It also includes a logout option. */

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

public class AdminInterface extends JFrame {
    private List<UrlInfo> urlList;
    public static List<UrlInfo> sharedUrlList;  // Shared list accessible by other classes
    private LoginFrame loginFrame;   // Reference to the LoginFrame

    // Constructor for AdminInterface, initializes lists and loginFrame, and calls initComponents
    public AdminInterface(LoginFrame loginFrame) {
        urlList = new ArrayList<>();
        sharedUrlList = urlList;  // Initialize sharedUrlList with urlList
        this.loginFrame = loginFrame;  // Set reference to the LoginFrame
        initComponents();  // Initialize components
    }

    // Method to initialize components and set up the user interface
    private void initComponents() {
        setTitle("URL Management");  // Set the title of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set close operation
        setSize(400, 300);  // Set the size of the frame
        setLocationRelativeTo(null);  // Center the frame on the screen

         JPanel panel = new JPanel();  // Create a panel for layout
        panel.setLayout(new GridLayout(5, 1, 10, 10));  // Use a 5x1 grid layout with spacing
        
        // Create buttons for uploading, editing and deleting URLs, checking URLs, and logging out
        
        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadButtonClicked();
            }
        });

        JButton adminViewButton = new JButton("Edit and Delete");
        adminViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminViewButtonClicked();
            }
        });

        JButton userViewButton = new JButton("Check");
        userViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userViewButtonClicked();
            }
        });

        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutButtonClicked();
            }
        });

        // Add buttons to the panel
        panel.add(uploadButton);
        panel.add(adminViewButton);
        panel.add(userViewButton);
        panel.add(logoutButton);
        
        add(panel);  // Add the panel to the frame
        setVisible(true);  // Set the frame as visible
    }

    // Method to handle upload button click, prompts the user for URL and name
    private void uploadButtonClicked() {
        String url = JOptionPane.showInputDialog(this, "Please enter the URL:");
        String name = JOptionPane.showInputDialog(this, "Please enter a name for this URL:");

        UrlInfo urlInfo = new UrlInfo(url, name);
        urlList.add(urlInfo);
        sharedUrlList = List.copyOf(urlList);  // Update the sharedUrlList
    }

    // Method to handle admin view button click, displays URLs for editing and deleting
    private void adminViewButtonClicked() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (UrlInfo urlInfo : urlList) {
            listModel.addElement(urlInfo.getName());
        }

        JList<String> urlJList = new JList<>(listModel);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButtonClicked(urlJList);
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editButtonClicked(urlJList);
            }
        });

        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.add(new JScrollPane(urlJList), BorderLayout.CENTER);
        adminPanel.add(deleteButton, BorderLayout.SOUTH);
        adminPanel.add(editButton, BorderLayout.EAST);

        JOptionPane.showMessageDialog(this, adminPanel, "Edit and delete", JOptionPane.PLAIN_MESSAGE);
    }

    // Method to handle user view button click, displays URLs for checking
    public void userViewButtonClicked() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (UrlInfo urlInfo : urlList) {
            listModel.addElement(urlInfo.getName());
        }

        JList<String> urlJList = new JList<>(listModel);
        urlJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        urlJList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    int index = urlJList.locationToIndex(evt.getPoint());
                    String selectedUrl = urlList.get(index).getUrl();
                    openWebpage(selectedUrl);
                }
            }
        });

        JOptionPane.showMessageDialog(this, new JScrollPane(urlJList), "Check", JOptionPane.PLAIN_MESSAGE);
    }

    // Method to handle delete button click, prompts for confirmation and deletes the selected URL
    private void deleteButtonClicked(JList<String> urlJList) {
        int selectedIndex = urlJList.getSelectedIndex();
        if (selectedIndex != -1) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                urlList.remove(selectedIndex);
                sharedUrlList = List.copyOf(urlList); // Update the sharedUrlList
            }
        }
    }

    // Method to handle edit button click, allows editing the selected URL
    private void editButtonClicked(JList<String> urlJList) {
        int selectedIndex = urlJList.getSelectedIndex();
        if (selectedIndex != -1) {
            UrlInfo selectedUrlInfo = urlList.get(selectedIndex);
            String newUrl = JOptionPane.showInputDialog(this, "Please enter the URL:", selectedUrlInfo.getUrl());
            String newName = JOptionPane.showInputDialog(this, "Please enter a name for this URL:", selectedUrlInfo.getName());
            selectedUrlInfo.setUrl(newUrl);
            selectedUrlInfo.setName(newName);
            sharedUrlList = List.copyOf(urlList); // Update the sharedUrlList 
        }
    }

    // Method to open a webpage using the default web browser
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            JOptionPane.showMessageDialog(this, "Cannot open the webpage: " + url);
        }
    }

    // Method to get the displayed data (URLs)
    public List<UrlInfo> getDisplayedData() {
        return urlList;
    }

    // Method to handle logout button click, disposes of the current window and opens the login window
    private void logoutButtonClicked() {
        this.dispose(); // Close the current window
        loginFrame.resetLoginFrame(); // Open the login window
    }

    // Static method to get the sharedUrlList accessible by other classes
    public static List<UrlInfo> getSharedUrlList() {
        return sharedUrlList;
    }

    // Inner class representing URL information
    public static class UrlInfo {
        private String url;
        private String name;

        // Constructor for UrlInfo, initializes URL and name
        public UrlInfo(String url, String name) {
            this.url = url;
            this.name = name;
        }

        // Getter method for URL
        public String getUrl() {
            return url;
        }

        // Setter method for URL
        public void setUrl(String url) {
            this.url = url;
        }

        // Getter method for name
        public String getName() {
            return name;
        }

        // Setter method for name
        public void setName(String name) {
            this.name = name;
        }
    }
}
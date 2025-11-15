package airlinedesign.jframe;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser; // Import JCalendar
import java.text.SimpleDateFormat;

/**
 * ✈ Philippine Airlines Reservation System with Login + Place Selector + Price Calculator
 */
public class AirlineDesignNeoPlus extends JFrame {

    private JTextField tfName, tfFlight, tfPrice;
    private JDateChooser dateChooser; // Changed from JTextField to JDateChooser
    private JComboBox<String> seatClass, cbOrigin, cbDestination;
    private JButton btnBook, btnClear, btnExit;
    private JTextArea ticketPreview;

    public AirlineDesignNeoPlus() {
        setTitle("✈ Philippine Airlines - Reservation System");
        setSize(1000, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Gradient Background
        JPanel background = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 50, 130),
                        0, getHeight(), new Color(0, 110, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new BorderLayout(15, 15));
        background.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(background);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("✈ Philippine Airlines Reservation System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        background.add(header, BorderLayout.NORTH);

        // Main Panels
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 25, 10));
        mainPanel.setOpaque(false);
        background.add(mainPanel, BorderLayout.CENTER);

        // Left Form (Passenger Info)
        JPanel formCard = createGlassCard();
        formCard.setLayout(new GridLayout(9, 2, 12, 15));

        formCard.add(createLabel("👤 Passenger Name:"));
        tfName = createTextField();
        addNoNumberInput(tfName);
        formCard.add(tfName);

        formCard.add(createLabel("🛩 Flight Number:"));
        tfFlight = createTextField();
        addOnlyNumberInput(tfFlight);
        formCard.add(tfFlight);

        // 🗺 ORIGIN SELECTION
        formCard.add(createLabel("🛫 Origin (From):"));
        cbOrigin = new JComboBox<>(new String[]{
                "Manila (MNL)",
                "Cebu (CEB)",
                "Davao (DVO)",
                "Clark (CRK)",
                "Iloilo (ILO)",
                "Zamboanga (ZAM)"
        });
        cbOrigin.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        formCard.add(cbOrigin);

        // 🗺 DESTINATION SELECTION
        formCard.add(createLabel("🛬 Destination (To):"));
        cbDestination = new JComboBox<>(new String[]{
                "Cebu (CEB)",
                "Davao (DVO)",
                "Manila (MNL)",
                "Iloilo (ILO)",
                "Tacloban (TAC)",
                "Puerto Princesa (PPS)"
        });
        cbDestination.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        formCard.add(cbDestination);

        // 💰 PRICE FIELD
        formCard.add(createLabel("💰 Price (PHP):"));
        tfPrice = createTextField();
        tfPrice.setEditable(false);
        tfPrice.setBackground(new Color(240, 240, 240));
        formCard.add(tfPrice);

        // 📅 Date Input - REPLACED WITH JCALENDAR
        formCard.add(createLabel("📅 Date (YYYY-MM-DD):"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        dateChooser.setPreferredSize(new Dimension(200, 30));
        formCard.add(dateChooser);

        // 💺 Seat Class
        formCard.add(createLabel("💺 Seat Class:"));
        seatClass = new JComboBox<>(new String[]{"Economy", "Premium Economy", "Business", "First Class"});
        seatClass.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        formCard.add(seatClass);

        // Add listeners to update price when origin, destination, or seat class changes
        // MUST BE AFTER seatClass is created!
        cbOrigin.addActionListener(e -> calculatePrice());
        cbDestination.addActionListener(e -> calculatePrice());
        seatClass.addActionListener(e -> calculatePrice());

        mainPanel.add(formCard);

        // Right - Ticket Preview
        JPanel ticketCard = createGlassCard();
        ticketCard.setLayout(new BorderLayout(10, 10));

        JLabel ticketTitle = new JLabel("🎫 Electronic Ticket Preview", JLabel.CENTER);
        ticketTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        ticketTitle.setForeground(new Color(0, 64, 128));
        ticketCard.add(ticketTitle, BorderLayout.NORTH);

        ticketPreview = new JTextArea();
        ticketPreview.setEditable(false);
        ticketPreview.setFont(new Font("Consolas", Font.PLAIN, 14));
        ticketPreview.setBackground(new Color(255, 255, 255, 230));
        ticketPreview.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        ticketPreview.setText("No ticket booked yet.");
        ticketCard.add(new JScrollPane(ticketPreview), BorderLayout.CENTER);

        mainPanel.add(ticketCard);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        buttonPanel.setOpaque(false);

        btnBook = create3DButton("Book Ticket", new Color(0, 180, 90));
        btnClear = create3DButton("Clear", new Color(255, 160, 0));
        btnExit = create3DButton("Exit", new Color(210, 0, 0));

        buttonPanel.add(btnBook);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);
        background.add(buttonPanel, BorderLayout.SOUTH);

        // Events
        btnBook.addActionListener(e -> bookTicket());
        btnClear.addActionListener(e -> clearFields());
        btnExit.addActionListener(e -> System.exit(0));
        
        // Calculate initial price
        calculatePrice();
    }

    // ======================== LOGIN FRAME ================================
    public static class LoginFrame extends JFrame {
        private JTextField tfUser;
        private JPasswordField pfPass;
        private JButton btnLogin, btnCancel;

        public LoginFrame() {
            setTitle("🔐 Airline System Login");
            setSize(400, 300);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));

            JPanel panel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(0, 0, new Color(0, 90, 180),
                            0, getHeight(), new Color(0, 140, 255));
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panel.setLayout(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            add(panel);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel lblTitle = new JLabel("✈ Login to Philippine Airlines");
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblTitle.setForeground(Color.WHITE);
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(lblTitle, gbc);

            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel lblUser = new JLabel("👤 Username:");
            lblUser.setForeground(Color.WHITE);
            panel.add(lblUser, gbc);

            gbc.gridx = 1;
            tfUser = new JTextField(15);
            panel.add(tfUser, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            JLabel lblPass = new JLabel("🔑 Password:");
            lblPass.setForeground(Color.WHITE);
            panel.add(lblPass, gbc);

            gbc.gridx = 1;
            pfPass = new JPasswordField(15);
            panel.add(pfPass, gbc);

            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;

            btnLogin = new JButton("Login");
            btnLogin.setBackground(new Color(0, 200, 100));
            btnLogin.setForeground(Color.WHITE);
            btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnLogin.addActionListener(e -> authenticate());
            panel.add(btnLogin, gbc);

            gbc.gridy++;
            btnCancel = new JButton("Exit");
            btnCancel.setBackground(new Color(200, 0, 0));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnCancel.addActionListener(e -> System.exit(0));
            panel.add(btnCancel, gbc);
        }

        private void authenticate() {
            String user = tfUser.getText().trim();
            String pass = new String(pfPass.getPassword());

            if (user.equals("jeebrayt") && pass.equals("bugocuyos")) {
                JOptionPane.showMessageDialog(this, "✅ Login Successful!");
                dispose();
                new AirlineDesignNeoPlus().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =================== Helper Methods ======================
    private JPanel createGlassCard() {
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color glass = new Color(255, 255, 255, 180);
                g2.setColor(glass);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(new Color(0, 0, 0, 40));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        return card;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(new Color(0, 51, 102));
        return lbl;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        return field;
    }

    private JButton create3DButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(150, 42));
        btn.setFocusPainted(false);
        return btn;
    }

    private void addNoNumberInput(JTextField f) {
        f.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) e.consume();
            }
        });
    }

    private void addOnlyNumberInput(JTextField f) {
        f.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') e.consume();
            }
        });
    }

    // Calculate price based on route and seat class
    private void calculatePrice() {
        String origin = (String) cbOrigin.getSelectedItem();
        String dest = (String) cbDestination.getSelectedItem();
        String seat = (String) seatClass.getSelectedItem();
        
        // Base prices for routes (in PHP)
        int basePrice = getBasePrice(origin, dest);
        
        // Seat class multipliers
        double multiplier = 1.0;
        switch (seat) {
            case "Economy": multiplier = 1.0; break;
            case "Premium Economy": multiplier = 1.5; break;
            case "Business": multiplier = 2.5; break;
            case "First Class": multiplier = 4.0; break;
        }
        
        int finalPrice = (int) (basePrice * multiplier);
        tfPrice.setText("₱ " + String.format("%,d", finalPrice));
    }
    
    // Get base price for different routes
    private int getBasePrice(String origin, String dest) {
        // Extract airport codes
        String originCode = origin.substring(origin.lastIndexOf("(") + 1, origin.lastIndexOf(")"));
        String destCode = dest.substring(dest.lastIndexOf("(") + 1, dest.lastIndexOf(")"));
        
        // Return same price if same origin and destination
        if (originCode.equals(destCode)) {
            return 0;
        }
        
        // Base prices between major routes
        String route = originCode + "-" + destCode;
        switch (route) {
            // From Manila
            case "MNL-CEB": case "CEB-MNL": return 2500;
            case "MNL-DVO": case "DVO-MNL": return 3500;
            case "MNL-CRK": case "CRK-MNL": return 1500;
            case "MNL-ILO": case "ILO-MNL": return 2200;
            case "MNL-ZAM": case "ZAM-MNL": return 3800;
            case "MNL-TAC": case "TAC-MNL": return 2800;
            case "MNL-PPS": case "PPS-MNL": return 3200;
            
            // From Cebu
            case "CEB-DVO": case "DVO-CEB": return 2000;
            case "CEB-CRK": case "CRK-CEB": return 2800;
            case "CEB-ILO": case "ILO-CEB": return 1800;
            case "CEB-ZAM": case "ZAM-CEB": return 2200;
            case "CEB-TAC": case "TAC-CEB": return 1500;
            case "CEB-PPS": case "PPS-CEB": return 2500;
            
            // From Davao
            case "DVO-CRK": case "CRK-DVO": return 4000;
            case "DVO-ILO": case "ILO-DVO": return 3000;
            case "DVO-ZAM": case "ZAM-DVO": return 1800;
            case "DVO-TAC": case "TAC-DVO": return 2800;
            case "DVO-PPS": case "PPS-DVO": return 2400;
            
            // Other combinations
            case "CRK-ILO": case "ILO-CRK": return 2500;
            case "CRK-ZAM": case "ZAM-CRK": return 4200;
            case "CRK-TAC": case "TAC-CRK": return 3000;
            case "CRK-PPS": case "PPS-CRK": return 3500;
            
            case "ILO-ZAM": case "ZAM-ILO": return 2800;
            case "ILO-TAC": case "TAC-ILO": return 2000;
            case "ILO-PPS": case "PPS-ILO": return 2600;
            
            case "ZAM-TAC": case "TAC-ZAM": return 3200;
            case "ZAM-PPS": case "PPS-ZAM": return 2000;
            
            case "TAC-PPS": case "PPS-TAC": return 2700;
            
            default: return 3000; // Default price for unlisted routes
        }
    }

    // Booking Logic - UPDATED TO USE JCALENDAR AND PRICE
    private void bookTicket() {
        String name = tfName.getText().trim();
        String flight = tfFlight.getText().trim();
        String origin = (String) cbOrigin.getSelectedItem();
        String dest = (String) cbDestination.getSelectedItem();
        String seat = (String) seatClass.getSelectedItem();
        String price = tfPrice.getText().trim();
        
        // Get date from JDateChooser
        String date = "";
        if (dateChooser.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.format(dateChooser.getDate());
        }

        if (name.isEmpty() || flight.isEmpty() || date.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String info =
                "✈ PHILIPPINE AIRLINES - BOARDING PASS\n" +
                "─────────────────────────────\n" +
                "Passenger : " + name +
                "\nFlight No  : " + flight +
                "\nFrom       : " + origin +
                "\nTo         : " + dest +
                "\nDate       : " + date +
                "\nSeat Class : " + seat +
                "\nPrice      : " + price +
                "\n─────────────────────────────\n" +
                "✅ Booking Confirmed – Bon Voyage!";

        ticketPreview.setText(info);
        JOptionPane.showMessageDialog(this, "🎉 Ticket Successfully Booked!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        tfName.setText("");
        tfFlight.setText("");
        tfPrice.setText("");
        dateChooser.setDate(null); // Clear the date picker
        cbOrigin.setSelectedIndex(0);
        cbDestination.setSelectedIndex(0);
        seatClass.setSelectedIndex(0);
        ticketPreview.setText("No ticket booked yet.");
        calculatePrice(); // Recalculate default price
    }

    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

/* 
 * IMPORTANT: Add JCalendar library to your NetBeans project
 * 
 * Steps:
 * 1. Download jcalendar-1.4.jar from: https://toedter.com/jcalendar/
 * 2. In NetBeans: Right-click your project → Properties
 * 3. Click "Libraries" → Click "Add JAR/Folder"
 * 4. Select the downloaded jcalendar-1.4.jar file
 * 5. Click OK
 * 
 * Alternative - Using Maven:
 * Add to pom.xml:
 * <dependency>
 *     <groupId>com.toedter</groupId>
 *     <artifactId>jcalendar</artifactId>
 *     <version>1.4</version>
 * </dependency>
 */


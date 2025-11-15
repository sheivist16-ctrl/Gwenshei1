package cebupacific;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * CebuPacificUI.java
 * Cebu Pacific-style flight booking page (Swing version)
 * by ChatGPT ✈
 */
public class CebuPacificUI extends JFrame {

    public CebuPacificUI() {
        setTitle("Cebu Pacific - Let's Fly Every Juan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== HERO PANEL =====
        HeroPanel hero = new HeroPanel("iloilo.jpg"); // <-- place image in /src folder
        hero.setLayout(null);

        // Top Navigation Bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        navBar.setOpaque(false);

        JLabel logo = new JLabel("✈ Cebu Pacific");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);
        navBar.add(logo);

        String[] menu = {"Book", "Manage", "Travel Info", "Explore", "About"};
        for (String m : menu) {
            JLabel item = new JLabel(m);
            item.setFont(new Font("Segoe UI", Font.BOLD, 16));
            item.setForeground(Color.WHITE);
            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // Add click listener for "Book"
            if (m.equals("Book")) {
                item.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        dispose(); // Close current window
                        new BookingPage().setVisible(true); // Open booking page
                    }
                });
            }
            
            navBar.add(item);
        }

        navBar.setBounds(300, 10, 700, 50);
        hero.add(navBar);

        // Center "ILOILO" text
        JLabel title = new JLabel("ILOILO", SwingConstants.CENTER);
        title.setFont(new Font("Arial Black", Font.BOLD, 100));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 200, 1280, 150);
        hero.add(title);

        // Flight Search Panel
        JPanel flightCard = createFlightSearchCard();
        flightCard.setBounds(180, 500, 900, 120);
        hero.add(flightCard);

        add(hero, BorderLayout.CENTER);
    }

    // ===== Flight Search Card =====
    private JPanel createFlightSearchCard() {
        JPanel card = new RoundedPanel(30, new Color(255, 255, 255, 240));
        card.setLayout(new GridBagLayout());
        card.setBorder(new CompoundBorder(
                new EmptyBorder(15, 15, 15, 15),
                new DropShadowBorder()
        ));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 12, 8, 12);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 1;

        JLabel lblFlight = new JLabel("✈ Flight");
        lblFlight.setFont(new Font("Segoe UI", Font.BOLD, 18));
        c.gridx = 0;
        c.weightx = 0.1;
        card.add(lblFlight, c);

        // From
        JPanel fromPanel = createInputField("From", "Davao DVO");
        c.gridx = 1;
        c.weightx = 0.25;
        card.add(fromPanel, c);

        // To
        JPanel toPanel = createInputField("To", "Select Destination");
        c.gridx = 2;
        c.weightx = 0.25;
        card.add(toPanel, c);

        // Depart
        JPanel departPanel = createInputField("Depart", "13 Nov 2025");
        c.gridx = 3;
        c.weightx = 0.2;
        card.add(departPanel, c);

        // Return
        JPanel returnPanel = createInputField("Return", "Returning on");
        c.gridx = 4;
        c.weightx = 0.2;
        card.add(returnPanel, c);

        // Button
        JButton btnSearch = new JButton("Search flights");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSearch.setBackground(new Color(0, 122, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(new EmptyBorder(10, 25, 10, 25));
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.gridx = 5;
        c.weightx = 0.15;
        card.add(btnSearch, c);

        btnSearch.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Searching flights...")
        );

        return card;
    }

    // ===== Input Fields =====
    private JPanel createInputField(String label, String placeholder) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel lbl = new JLabel("<html><small style='color:#777;'>" + label + "</small><br><b>" + placeholder + "</b></html>");
        lbl.setBorder(new EmptyBorder(2, 6, 2, 6));
        p.add(lbl, BorderLayout.CENTER);

        p.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        return p;
    }

    // ===== Hero Background =====
    class HeroPanel extends JPanel {
        private Image bgImage;

        public HeroPanel(String path) {
            try {
                bgImage = new ImageIcon(getClass().getResource(path)).getImage();
            } catch (Exception e) {
                System.out.println("⚠ Could not load image: " + path);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bgImage != null) {
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            // Gradient overlay
            Graphics2D g2 = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 0, 120),
                                                 0, getHeight(), new Color(0, 0, 0, 30));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // ===== Rounded Panel =====
    class RoundedPanel extends JPanel {
        private int radius;
        private Color bgColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===== Drop Shadow =====
    class DropShadowBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int shadowSize = 10;
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(x + 4, y + 4, width - 8, height - 8, 30, 30);
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CebuPacificUI ui = new CebuPacificUI();
            ui.setVisible(true);
        });
    }
}

// ===== BOOKING PAGE =====
class BookingPage extends JFrame {

    public BookingPage() {
        setTitle("Cebu Pacific - Book Your Flight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 248, 250));

        // Top Navigation Bar
        JPanel navBar = createNavBar();
        mainPanel.add(navBar, BorderLayout.NORTH);

        // Content area
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(245, 248, 250));
        contentPanel.setBorder(new EmptyBorder(60, 100, 60, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 50, 0);

        // Booking options cards
        JPanel flightsCard = createBookingCard(
            "✈", Color.decode("#FFD200"),
            "Flights",
            null
        );
        contentPanel.add(flightsCard, gbc);

        JPanel seatSaleCard = createBookingCard(
            "🎫", Color.decode("#FFD200"),
            "Seat Sale",
            null
        );
        contentPanel.add(seatSaleCard, gbc);

        JPanel superPassCard = createBookingCard(
            "₱99", Color.decode("#FFD200"),
            "CEB Super Pass",
            null
        );
        contentPanel.add(superPassCard, gbc);

        // Business section
        gbc.insets = new Insets(30, 0, 20, 0);
        JLabel businessLabel = new JLabel("FOR BUSINESS");
        businessLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        businessLabel.setForeground(new Color(100, 100, 100));
        contentPanel.add(businessLabel, gbc);

        // Business options
        gbc.insets = new Insets(0, 0, 20, 0);
        JPanel businessPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        businessPanel.setOpaque(false);

        businessPanel.add(createBusinessOption(
            "Partner Agents ↗",
            "Log in with your agent ID"
        ));
        businessPanel.add(createBusinessOption(
            "Cargo",
            "Know more about our fast and flexible air cargo service"
        ));
        businessPanel.add(createBusinessOption(
            "Sales & Group Bookings",
            "Be a partner and maximize your business' travel budget"
        ));

        contentPanel.add(businessPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));

        JLabel logo = new JLabel("✈ Cebu Pacific");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(new Color(0, 122, 255));
        navBar.add(logo);

        String[] menu = {"Book", "Manage", "Travel Info", "Explore", "About"};
        for (String m : menu) {
            JLabel item = new JLabel(m);
            item.setFont(new Font("Segoe UI", Font.BOLD, 16));
            item.setForeground(m.equals("Book") ? new Color(0, 122, 255) : new Color(80, 80, 80));
            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            navBar.add(item);
        }

        return navBar;
    }

    private JPanel createBookingCard(String icon, Color iconBg, String title, String subtitle) {
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 230, 230), 1, true),
            new EmptyBorder(25, 30, 25, 30)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Icon
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BorderLayout());
        iconPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconLabel.setOpaque(true);
        iconLabel.setBackground(iconBg);
        iconLabel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Make icon circular (approximate with rounded corners)
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconPanel.add(iconLabel);

        // Text
        JPanel textPanel = new JPanel(new GridLayout(subtitle != null ? 2 : 1, 1, 0, 5));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 122, 255));
        textPanel.add(titleLabel);

        if (subtitle != null) {
            JLabel subtitleLabel = new JLabel(subtitle);
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            subtitleLabel.setForeground(new Color(120, 120, 120));
            textPanel.add(subtitleLabel);
        }

        card.add(iconPanel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(250, 250, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
            }
        });

        return card;
    }

    private JPanel createBusinessOption(String title, String description) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 122, 255));
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(120, 120, 120));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(descLabel, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingPage page = new BookingPage();
            page.setVisible(true);
        });
    }
}

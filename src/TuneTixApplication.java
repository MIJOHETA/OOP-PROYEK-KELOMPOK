import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.util.*;

public class TuneTixApplication extends Application {

    private Stage primaryStage;
    private Scene loginScene, mainMenuScene, buyTicketScene, viewTicketsScene, viewStatusScene, verifyPaymentScene;
    private User currentUser;

    // Data
    private List<Event> events = new ArrayList<>();
    private List<Ticket> userTickets = new ArrayList<>();
    private Map<String, Booking> pendingBookings = new HashMap<>();

    // UI Controls shared in some scenes
    private Label paymentStatusLabel = new Label();
    private Label bookingStatusLabel = new Label();

    private TextField loginNameField = new TextField();

    // Formatters
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeData();

        createLoginScene();
        createMainMenuScene();
        createBuyTicketScene();
        createViewTicketsScene();
        createViewStatusScene();
        createVerifyPaymentScene();

        primaryStage.setTitle("TuneTix Ticket Booking");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void initializeData() {
        Venue v1 = new Venue("Stadion Utama", "Jakarta");
        Venue v2 = new Venue("Gedung Serbaguna", "Bandung");

        Map<TicketCategory, Integer> tickets1 = new HashMap<>();
        tickets1.put(TicketCategory.REGULAR, 10);
        tickets1.put(TicketCategory.VIP, 5);
        tickets1.put(TicketCategory.VVIP, 2);

        Map<TicketCategory, Double> prices1 = new HashMap<>();
        prices1.put(TicketCategory.REGULAR, 500000.0);
        prices1.put(TicketCategory.VIP, 1500000.0);
        prices1.put(TicketCategory.VVIP, 3000000.0);

        Map<TicketCategory, Integer> tickets2 = new HashMap<>();
        tickets2.put(TicketCategory.REGULAR, 8);
        tickets2.put(TicketCategory.VIP, 3);
        tickets2.put(TicketCategory.VVIP, 1);

        Map<TicketCategory, Double> prices2 = new HashMap<>();
        prices2.put(TicketCategory.REGULAR, 450000.0);
        prices2.put(TicketCategory.VIP, 1200000.0);
        prices2.put(TicketCategory.VVIP, 2500000.0);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            events.add(new Event("Konser A", v1, sdf.parse("20-06-2025"), tickets1, prices1));
            events.add(new Event("Konser B", v2, sdf.parse("25-06-2025"), tickets2, prices2));
        } catch (Exception e) {
            e.printStackTrace();
            // Dummy fallback
        }
    }

    private void createLoginScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(60, 40, 60, 40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("SELAMAT DATANG DI TUNE-TIX");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setStyle("-fx-text-fill: #111827;");

        Label subtitle = new Label("Masukkan nama Anda untuk memulai sesi");
        subtitle.setFont(Font.font("Segoe UI", 16));
        subtitle.setStyle("-fx-text-fill: #6b7280;");

        loginNameField.setPromptText("Nama lengkap");
        loginNameField.setFont(Font.font("Segoe UI", 16));
        loginNameField.setMaxWidth(300);

        Button loginButton = new Button("Mulai");
        loginButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        loginButton.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-background-radius: 8;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #1f2937; -fx-text-fill: white; -fx-background-radius: 8;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-background-radius: 8;"));
        loginButton.setOnAction(e -> {
            String name = loginNameField.getText().trim();
            if (name.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Nama tidak boleh kosong");
            } else {
                currentUser = new User(name);
                switchScene(mainMenuScene);
            }
        });

        root.getChildren().addAll(title, subtitle, loginNameField, loginButton);

        loginScene = new Scene(root, 480, 320);
    }

    private void createMainMenuScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #ffffff;");
        root.setAlignment(Pos.TOP_CENTER);

        Label heading = new Label("Menu Utama");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        heading.setStyle("-fx-text-fill: #111827;");

        Label welcomeLabel = new Label();
        welcomeLabel.setFont(Font.font("Segoe UI", 18));
        welcomeLabel.setStyle("-fx-text-fill: #6b7280;");

        // Update welcome label on scene showing
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null && currentUser != null) {
                welcomeLabel.setText("Halo, " + currentUser.getName());
            }
        });

        Button buyTicketBtn = new Button("Beli Tiket");
        buyTicketBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        buyTicketBtn.setPrefWidth(260);
        stylePrimaryButton(buyTicketBtn);

        Button viewTicketsBtn = new Button("Lihat Tiket Saya");
        viewTicketsBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        viewTicketsBtn.setPrefWidth(260);
        stylePrimaryButton(viewTicketsBtn);

        Button viewStatusBtn = new Button("Lihat Status Ketersediaan Tiket");
        viewStatusBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        viewStatusBtn.setPrefWidth(260);
        stylePrimaryButton(viewStatusBtn);

        Button verifyPaymentBtn = new Button("Verifikasi Pembayaran");
        verifyPaymentBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        verifyPaymentBtn.setPrefWidth(260);
        stylePrimaryButton(verifyPaymentBtn);

        Button logoutBtn = new Button("Keluar");
        logoutBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        logoutBtn.setPrefWidth(260);
        logoutBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 8;");
        logoutBtn.setOnMouseEntered(e -> logoutBtn.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-background-radius: 8;"));
        logoutBtn.setOnMouseExited(e -> logoutBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 8;"));

        buyTicketBtn.setOnAction(e -> switchScene(buyTicketScene));
        viewTicketsBtn.setOnAction(e -> {
            refreshUserTicketsView();
            switchScene(viewTicketsScene);
        });
        viewStatusBtn.setOnAction(e -> {
            refreshEventStatusView();
            switchScene(viewStatusScene);
        });
        verifyPaymentBtn.setOnAction(e -> {
            refreshPendingBookings();
            switchScene(verifyPaymentScene);
        });
        logoutBtn.setOnAction(e -> {
            currentUser = null;
            userTickets.clear();
            pendingBookings.clear();
            loginNameField.clear();
            switchScene(loginScene);
        });

        root.getChildren().addAll(heading, welcomeLabel, buyTicketBtn, viewTicketsBtn, viewStatusBtn, verifyPaymentBtn, logoutBtn);

        mainMenuScene = new Scene(root, 480, 460);
    }

    private void stylePrimaryButton(Button btn) {
        btn.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-background-radius: 8;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #1f2937; -fx-text-fill: white; -fx-background-radius: 8;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-background-radius: 8;"));
    }

    // Buy ticket scene components
    private ComboBox<Event> eventComboBox;
    private ComboBox<TicketCategory> categoryComboBox;
    private Label priceLabel;
    private Label availabilityLabel;
    private Button proceedPaymentButton;

    private Payment currentPayment;
    private Booking currentBooking;

    private void createBuyTicketScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(25, 30, 30, 30));
        root.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Beli Tiket");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #111827;");

        // Event selection
        Label eventLabel = new Label("Pilih Konser:");
        eventLabel.setFont(Font.font("Segoe UI", 16));
        eventLabel.setStyle("-fx-text-fill: #6b7280;");

        eventComboBox = new ComboBox<>();
        eventComboBox.setItems(FXCollections.observableArrayList(events));
        eventComboBox.setPrefWidth(320);
        eventComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Event event) {
                if (event == null) return "";
                return event.getTitle() + " - " + dateFormat.format(event.getDate());
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        });

        // Category selection
        Label categoryLabel = new Label("Pilih Kategori Tiket:");
        categoryLabel.setFont(Font.font("Segoe UI", 16));
        categoryLabel.setStyle("-fx-text-fill: #6b7280;");

        categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(FXCollections.observableArrayList(TicketCategory.values()));
        categoryComboBox.setPrefWidth(320);

        // Price and availability
        priceLabel = new Label("Harga: -");
        priceLabel.setFont(Font.font("Segoe UI", 16));
        priceLabel.setStyle("-fx-text-fill: #6b7280;");

        availabilityLabel = new Label("Ketersediaan: -");
        availabilityLabel.setFont(Font.font("Segoe UI", 16));
        availabilityLabel.setStyle("-fx-text-fill: #6b7280;");

        proceedPaymentButton = new Button("Lanjut ke Pembayaran");
        proceedPaymentButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        proceedPaymentButton.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-background-radius:8;");
        proceedPaymentButton.setDisable(true);

        proceedPaymentButton.setOnAction(e -> handlePayment());

        eventComboBox.valueProperty().addListener((obs, oldV, newV) -> updatePriceAndAvailability());
        categoryComboBox.valueProperty().addListener((obs, oldV, newV) -> updatePriceAndAvailability());

        Button backBtn = new Button("Kembali ke Menu");
        backBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;");
        backBtn.setOnAction(e -> switchScene(mainMenuScene));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius:8;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;"));

        root.getChildren().addAll(title,
                eventLabel, eventComboBox,
                categoryLabel, categoryComboBox,
                priceLabel, availabilityLabel,
                proceedPaymentButton,
                backBtn);

        buyTicketScene = new Scene(root, 480, 440);
    }

    private void updatePriceAndAvailability() {
        Event selectedEvent = eventComboBox.getValue();
        TicketCategory selectedCategory = categoryComboBox.getValue();

        if (selectedEvent == null || selectedCategory == null) {
            priceLabel.setText("Harga: -");
            availabilityLabel.setText("Ketersediaan: -");
            proceedPaymentButton.setDisable(true);
            return;
        }
        double price = selectedEvent.getTicketPrice(selectedCategory);
        priceLabel.setText(String.format("Harga: Rp%,.2f", price));

        int avail = selectedEvent.getTicketAvailability().getOrDefault(selectedCategory, 0);
        availabilityLabel.setText("Ketersediaan: " + avail + " tiket");

        proceedPaymentButton.setDisable(avail <= 0);
    }

    private void handlePayment() {
        Event selectedEvent = eventComboBox.getValue();
        TicketCategory selectedCategory = categoryComboBox.getValue();

        if (selectedEvent == null || selectedCategory == null) {
            showAlert(Alert.AlertType.ERROR, "Pilih event dan kategori tiket terlebih dahulu.");
            return;
        }

        Booking booking = new Booking(selectedEvent, selectedCategory, currentUser);
        if (!booking.checkAvailability()) {
            showAlert(Alert.AlertType.ERROR, "Tiket tidak tersedia untuk kategori ini.");
            return;
        }

        currentBooking = booking;
        currentPayment = new Payment(booking, booking.getBookingAmount());

        // Payment simulation dialog
        showPaymentDialog();
    }

    private void showPaymentDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Instruksi Pembayaran");

        Label instructions = new Label();
        instructions.setFont(Font.font("Segoe UI", 14));
        instructions.setWrapText(true);

        // Select bank combo box
        ComboBox<Bank> bankComboBox = new ComboBox<>();
        bankComboBox.setItems(FXCollections.observableArrayList(Bank.values()));
        bankComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Bank bank) {
                if (bank == null) return "";
                return bank.getDisplayName();
            }

            @Override
            public Bank fromString(String s) {
                return null;
            }
        });
        bankComboBox.setPromptText("Pilih Bank");

        Label vaLabel = new Label();
        vaLabel.setFont(Font.font("Segoe UI", 14));

        bankComboBox.valueProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                currentPayment.selectedBank = newV;
                currentPayment.virtualAccountNumber = currentPayment.generateVirtualAccountNumber();
                vaLabel.setText("Nomor Virtual Account: " + currentPayment.virtualAccountNumber);
                instructions.setText(
                        String.format("Silakan lakukan pembayaran ke:\nBank: %s\nNomor Virtual Account: %s\nTotal Pembayaran: Rp%,.2f\n\nSetelah membayar, silakan lakukan verifikasi pembayaran di menu verifikasi.",
                                newV.getDisplayName(), currentPayment.virtualAccountNumber, currentPayment.amount));
            } else {
                vaLabel.setText("");
                instructions.setText("");
            }
        });

        VBox content = new VBox(10);
        content.setPadding(new Insets(15));
        content.getChildren().addAll(bankComboBox, vaLabel, instructions);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) return ButtonType.OK;
            else return null;
        });

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentPayment.status = PaymentStatus.PENDING;
            pendingBookings.put(currentBooking.getKodeBooking(), currentBooking);
            showAlert(Alert.AlertType.INFORMATION, "Booking berhasil dibuat!\nKode Booking Anda: " + currentBooking.getKodeBooking() + "\nSilakan lakukan pembayaran sesuai instruksi.");
            switchScene(mainMenuScene);
        }
    }

    // View My Tickets scene variables
    private VBox ticketsContainer;

    private void createViewTicketsScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(25, 30, 30, 30));
        root.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Tiket Anda");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #111827;");

        ticketsContainer = new VBox(12);

        ScrollPane scrollPane = new ScrollPane(ticketsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent;");

        Button backBtn = new Button("Kembali ke Menu");
        backBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;");
        backBtn.setOnAction(e -> switchScene(mainMenuScene));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius:8;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;"));

        root.getChildren().addAll(title, scrollPane, backBtn);

        viewTicketsScene = new Scene(root, 480, 420);
    }

    private void refreshUserTicketsView() {
        ticketsContainer.getChildren().clear();
        if (userTickets.isEmpty()) {
            Label noTicketLabel = new Label("Anda belum memiliki tiket apapun.");
            noTicketLabel.setFont(Font.font("Segoe UI", 16));
            noTicketLabel.setStyle("-fx-text-fill: #6b7280;");
            ticketsContainer.getChildren().add(noTicketLabel);
        } else {
            for (Ticket t : userTickets) {
                VBox card = createTicketCard(t);
                ticketsContainer.getChildren().add(card);
            }
        }
    }

    private VBox createTicketCard(Ticket ticket) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #d1d5db, 3, 0, 0, 1);");

        Label nameLabel = new Label("Nama: " + ticket.user.getName());
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        nameLabel.setStyle("-fx-text-fill: #111827;");

        Label eventLabel = new Label("Acara: " + ticket.event.getTitle());
        eventLabel.setFont(Font.font("Segoe UI", 14));
        eventLabel.setStyle("-fx-text-fill: #6b7280;");

        Label categoryLabel = new Label("Kategori Tiket: " + ticket.category);
        categoryLabel.setFont(Font.font("Segoe UI", 14));
        categoryLabel.setStyle("-fx-text-fill: #6b7280;");

        String venueStr = ticket.event.getVenue().getName() + ", " + ticket.event.getVenue().getLocation();
        Label venueLabel = new Label("Venue: " + venueStr);
        venueLabel.setFont(Font.font("Segoe UI", 14));
        venueLabel.setStyle("-fx-text-fill: #6b7280;");

        String dateStr = dateFormat.format(ticket.event.getDate());
        Label dateLabel = new Label("Tanggal: " + dateStr);
        dateLabel.setFont(Font.font("Segoe UI", 14));
        dateLabel.setStyle("-fx-text-fill: #6b7280;");

        card.getChildren().addAll(nameLabel, eventLabel, categoryLabel, venueLabel, dateLabel);
        return card;
    }

    // View Event Status scene variables
    private VBox statusContainer;

    private void createViewStatusScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(25, 30, 30, 30));
        root.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Status Ketersediaan Tiket");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #111827;");

        statusContainer = new VBox(12);

        ScrollPane scrollPane = new ScrollPane(statusContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent;");

        Button backBtn = new Button("Kembali ke Menu");
        backBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;");
        backBtn.setOnAction(e -> switchScene(mainMenuScene));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius:8;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;"));

        root.getChildren().addAll(title, scrollPane, backBtn);

        viewStatusScene = new Scene(root, 480, 420);
    }

    private void refreshEventStatusView() {
        statusContainer.getChildren().clear();
        for (Event e : events) {
            VBox card = new VBox(6);
            card.setPadding(new Insets(12));
            card.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #d1d5db, 3, 0, 0, 1);");

            Label eventTitle = new Label("Acara: " + e.getTitle() + " | Status: " + e.getStatus());
            eventTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
            eventTitle.setStyle("-fx-text-fill: #111827;");

            VBox availabilityBox = new VBox(2);
            for (Map.Entry<TicketCategory, Integer> entry : e.getTicketAvailability().entrySet()) {
                Label label = new Label("Kategori " + entry.getKey() + ": " + entry.getValue() + " tiket tersedia");
                label.setFont(Font.font("Segoe UI", 14));
                label.setStyle("-fx-text-fill: #6b7280;");
                availabilityBox.getChildren().add(label);
            }

            card.getChildren().addAll(eventTitle, availabilityBox);
            statusContainer.getChildren().add(card);
        }
    }

    // Verify payment variables
    private ComboBox<String> bookingCodeComboBox;
    private Label verificationMessageLabel;

    private void createVerifyPaymentScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(25, 30, 30, 30));
        root.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Verifikasi Pembayaran");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: #111827;");

        bookingCodeComboBox = new ComboBox<>();
        bookingCodeComboBox.setPrefWidth(300);
        bookingCodeComboBox.setPromptText("Pilih Kode Booking");

        verificationMessageLabel = new Label("");
        verificationMessageLabel.setFont(Font.font("Segoe UI", 16));
        verificationMessageLabel.setStyle("-fx-text-fill: #6b7280;");

        Button verifyBtn = new Button("Verifikasi");
        verifyBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        verifyBtn.setStyle("-fx-background-color: #111827; -fx-text-fill: white; -fx-background-radius: 8;");
        verifyBtn.setOnAction(e -> verifyPayment());

        Button backBtn = new Button("Kembali ke Menu");
        backBtn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;");
        backBtn.setOnAction(e -> switchScene(mainMenuScene));
        backBtn.setOnMouseEntered(e -> backBtn.setStyle("-fx-background-color: #4b5563; -fx-text-fill: white; -fx-background-radius:8;"));
        backBtn.setOnMouseExited(e -> backBtn.setStyle("-fx-background-color: #6b7280; -fx-text-fill: white; -fx-background-radius:8;"));

        root.getChildren().addAll(title, bookingCodeComboBox, verifyBtn, verificationMessageLabel, backBtn);

        verifyPaymentScene = new Scene(root, 480, 300);
    }

    private void refreshPendingBookings() {
        bookingCodeComboBox.getItems().clear();
        bookingCodeComboBox.getItems().addAll(pendingBookings.keySet());
        verificationMessageLabel.setText("");
    }

    private void verifyPayment() {
        String kodeBooking = bookingCodeComboBox.getValue();
        if (kodeBooking == null || kodeBooking.isEmpty()) {
            verificationMessageLabel.setText("Pilih kode booking untuk verifikasi.");
            return;
        }
        Booking bookingToVerify = pendingBookings.get(kodeBooking);
        if (bookingToVerify == null) {
            verificationMessageLabel.setText("Kode booking tidak ditemukan atau sudah diverifikasi.");
            return;
        }

        // Simulate payment verification delay
        verificationMessageLabel.setText("Mengecek sistem pembayaran...");
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                // ignore
            }

            // For simulation, we always say payment detected successfully
            javafx.application.Platform.runLater(() -> {
                verificationMessageLabel.setText("Pembayaran terdeteksi!");

                Ticket newTicket = bookingToVerify.confirmAndGenerateTicket();
                userTickets.add(newTicket);
                pendingBookings.remove(kodeBooking);

                showAlert(Alert.AlertType.INFORMATION, "Pembayaran berhasil diverifikasi!\nTiket sudah dikonfirmasi.");
                switchScene(mainMenuScene);
            });
        }).start();
    }

    private void switchScene(Scene newScene) {
        primaryStage.setScene(newScene);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
// Supporting classes from your model such as User, Venue, Event, TicketCategory, Booking, Payment, Ticket, Bank, PaymentStatus
// Expected to be placed in same package or imported accordingly


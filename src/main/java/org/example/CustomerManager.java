package org.example;

    import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

    public class CustomerManager extends Application {

        private ObservableList<Customer> Customers =
                FXCollections.observableArrayList();

        @Override
        public void start(Stage stage) {

            // Customer name
            Label nameLabel = new Label("Customer Name");
            TextField nameField = new TextField();
            nameField.setPromptText("Enter customer name");
            nameLabel.setLabelFor(nameField);

            // Province
            Label provinceLabel = new Label("Province");

            ComboBox<String> provinceBox = new ComboBox<>();

            provinceBox.getItems().addAll(
                    "Central",
                    "Copperbelt",
                    "Eastern",
                    "Luapula",
                    "Lusaka",
                    "Muchinga",
                    "Northern",
                    "North-Western",
                    "Southern",
                    "Western"
            );

            provinceBox.setPromptText("Choose a province");

            // Status message
            Label status = new Label();

            // Save button
            Button saveButton = new Button("Save Customer");

            // Delete button
            Button deleteButton = new Button("Delete Customer");

            // Table
            TableView<Customer> table = new TableView<>();

            table.setItems(Customers);

            // Name column
            TableColumn<Customer, String> nameCol =
                    new TableColumn<>("Customer Name");

            nameCol.setCellValueFactory(
                    new PropertyValueFactory<>("name"));

            // Province column
            TableColumn<Customer, String> provinceCol =
                    new TableColumn<>("Province");

            provinceCol.setCellValueFactory(
                    new PropertyValueFactory<>("province"));

            table.getColumns().add(nameCol);
            table.getColumns().add(provinceCol);

            // Save action
            saveButton.setOnAction(event -> {

                String name = nameField.getText().trim();

                if (name.isEmpty()) {
                    status.setText("Enter the customer name.");
                    nameField.requestFocus();
                    return;
                }

                String province = provinceBox.getValue();

                if (province == null) {
                    status.setText("Choose a province.");
                    provinceBox.requestFocus();
                    return;
                }

                Customers.add(new Customer(name, province));

                status.setText("Customer saved.");

                nameField.clear();
                provinceBox.setValue(null);
            });

            // Delete action
            deleteButton.setOnAction(event -> {

                Customer selected =
                        table.getSelectionModel().getSelectedItem();

                if (selected == null) {
                    status.setText("Select a customer first.");
                    return;
                }

                Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION);

                alert.setTitle("Delete Customer");
                alert.setHeaderText("Delete this customer?");
                alert.setContentText(
                        selected.getName() + " - "
                                + selected.getProvince());

                ButtonType result =
                        alert.showAndWait().orElse(ButtonType.CANCEL);

                if (result == ButtonType.OK) {

                    Customers.remove(selected);

                    status.setText("Customer deleted.");
                }
            });

            // Layout
            VBox root = new VBox(10);

            root.setPadding(new Insets(15));

            root.getChildren().addAll(
                    nameLabel,
                    nameField,
                    provinceLabel,
                    provinceBox,
                    saveButton,
                    deleteButton,
                    table,
                    status
            );

            Scene scene = new Scene(root, 600, 500);

            stage.setTitle("Customer Manager");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }


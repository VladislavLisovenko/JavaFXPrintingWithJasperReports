package ru.vlsoft.view_models;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ru.vlsoft.GeneralUtils;
import ru.vlsoft.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListViewModel {

    private List<? extends TableColumn<Product, ?>> getColumns() {

        List<TableColumn<Product, ?>> columns = new ArrayList<>();
        {
            TableColumn<Product, String> column = new TableColumn<>("Name");
            column.setCellValueFactory(new PropertyValueFactory<>("name"));
            column.setEditable(false);
            column.setMinWidth(200.);
            columns.add(column);
        }
        {
            TableColumn<Product, Double> column = new TableColumn<>("Price");
            column.setCellValueFactory(new PropertyValueFactory<>("price"));
            column.setEditable(false);
            column.setMinWidth(100.);
            columns.add(column);
        }

        return columns;

    }

    public TableView<Product> getTableView() {

        TableView<Product> tableView = new TableView<>();
        tableView.getColumns().addAll(getColumns());
        tableView.setItems(FXCollections.observableList(GeneralUtils.products));
        return tableView;

    }

    public Node getSelectionDialogContent() {

        TableView<Product> tableView = getTableView();
        TextField textField = new TextField();
        textField.setOnKeyReleased(handler -> {
                    String text = textField.getText();
                    if (!text.isEmpty()) {
                        tableView.setItems(
                                FXCollections.observableList(
                                        GeneralUtils.products.stream()
                                                .filter(product -> product.getName().contains(text))
                                                .collect(Collectors.toList())));
                    }
                }
        );

        Platform.runLater(textField::requestFocus);

        VBox layout = new VBox();
        layout.getChildren().add(tableView);
        layout.getChildren().add(textField);
        layout.setOnKeyReleased(handler -> {
            if (handler.getCode().isNavigationKey()) {
                Platform.runLater(tableView::requestFocus);
            }
        });

        return layout;

    }

}

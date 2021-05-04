package ru.vlsoft.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import ru.vlsoft.GeneralUtils;
import ru.vlsoft.models.Country;
import ru.vlsoft.models.Product;
import ru.vlsoft.models.TableDetails;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainFormController {

    @FXML
    private TableView<TableDetails> tableView;

    @FXML
    private Button buttonPrint;

    @FXML
    void initialize() {

        tableView.getColumns().clear();
        tableView.setItems(FXCollections.observableList(GeneralUtils.details));

        {
            TableColumn<TableDetails, Product> column = new TableColumn<>("Product");
            column.setCellValueFactory(new PropertyValueFactory<>("product"));
            column.setMinWidth(200.);
            tableView.getColumns().add(column);
        }

        {
            TableColumn<TableDetails, Country> column = new TableColumn<>("Country");
            column.setCellValueFactory(new PropertyValueFactory<>("country"));
            column.setMinWidth(200.);
            tableView.getColumns().add(column);
        }

        {
            TableColumn<TableDetails, Integer> column = new TableColumn<>("Price");
            column.setCellValueFactory(new PropertyValueFactory<>("price"));
            column.setMinWidth(100.);
            tableView.getColumns().add(column);
        }

        {
            TableColumn<TableDetails, Integer> column = new TableColumn<>("Amount");
            column.setCellValueFactory(new PropertyValueFactory<>("amount"));
            column.setMinWidth(100.);
            tableView.getColumns().add(column);
        }

        {
            TableColumn<TableDetails, Double> column = new TableColumn<>("Total");
            column.setCellValueFactory(new PropertyValueFactory<>("total"));
            column.setMinWidth(100.);
            tableView.getColumns().add(column);
        }

        buttonPrint.setOnAction(handler -> {
            try {
                print();
            } catch (JRException e) {
                e.printStackTrace();
            }
        });

    }

    private void print() throws JRException {

        URL resource = getClass().getResource("/Blank_A4.jrxml");
        if(resource == null) {
            return;
        }

        //report object creating
        JasperReport report = JasperCompileManager.compileReport(resource.getPath());
        //setting file name for compiled report
        JRSaver.saveObject(report, "report.jasper");

        //Map with parameters (some output values in the header side of the report)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Printing test");

        //datasource for report, can be replaced with data from database for example
        List<Map<String, Object>> data = GeneralUtils.details
                .stream().map(td -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("product", td.getProduct().getName());
                    map.put("country", td.getCountry().getName());
                    map.put("price", td.getPrice().doubleValue());
                    map.put("amount", td.getAmount().doubleValue());
                    map.put("total", td.getTotal().doubleValue());
                    return map;
                }).collect(Collectors.toList());

        //Jasper-wrapper for datasource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        //filling report
        JasperPrint jasperPrint = JasperFillManager.fillReport("report.jasper", parameters, dataSource);

        //export report to html
        //you can use another Exporter to export into different format
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput("report.html"));
        exporter.exportReport();

    }

}

package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.CategoryDao;
import com.example.pt_tugasakhir_agenda.model.Category;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController {
    @FXML
    private TableView<Category> tbCategory;
    @FXML
    private TableColumn<Integer, Category>  idCol;
    @FXML
    private TableColumn<String, Category> nameCol;
    @FXML
    private TextField txtCategoryName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    private ObservableList<Category> cList;
    private CategoryDao cDao;

    public void initialize() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        cDao = new CategoryDao();
        getData();
    }
    public void getData() {
        cList = cDao.getData();
        tbCategory.setItems(cList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("idcategory"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("categoryname"));
    }
    public void getSelectedCategory() {
        txtCategoryName.setText(tbCategory.getSelectionModel().getSelectedItem().getCategoryname());
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }
    public void addCategory() {
        int res = cDao.addData(new Category(0, txtCategoryName.getText()));
        Alert alert;
        if (res > 0) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Category Added Successfully", ButtonType.OK);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Error on add category", ButtonType.OK);
        }
        alert.showAndWait();
        getData();
        reset();
    }
    public void updateCategory() {
        int res = cDao.updateData(new Category(tbCategory.getSelectionModel().getSelectedItem().getIdcategory(), txtCategoryName.getText()));
        Alert alert;
        if (res > 0) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Category updated successfully", ButtonType.OK);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION, "Error on update category", ButtonType.OK);
        }
        alert.showAndWait();
        getData();
        reset();
    }
    public void deleteCategory() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure want to delete this category?", ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            cDao.deleteData(tbCategory.getSelectionModel().getSelectedItem());
        }
        getData();
        reset();
    }
    public void reset() {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tbCategory.getSelectionModel().clearSelection();
        txtCategoryName.clear();
    }
}

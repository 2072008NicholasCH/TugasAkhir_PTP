package com.example.pt_tugasakhir_agenda.controller;

import com.example.pt_tugasakhir_agenda.dao.CategoryDao;
import com.example.pt_tugasakhir_agenda.model.Category;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
}

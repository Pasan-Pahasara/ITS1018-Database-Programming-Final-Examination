package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Student;
import util.CrudUtil;
import views.tm.StudentTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
public class StudentManageFormController {
    public AnchorPane root;
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtStudentEmail;
    public JFXTextField txtStudentContact;
    public JFXTextField txtStudentAddress;
    public JFXTextField txtStudentNic;
    public TableView <StudentTM>tblStudents;
    public JFXButton btnAddNew;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    /**
     * Initializes the controller class.
     */
    public void initialize(){
        tblStudents.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblStudents.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tblStudents.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblStudents.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudents.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudents.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("nic"));

        loadAllStudents();
        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtStudentId.setText(newValue.getStudentId());
                txtStudentName.setText(newValue.getStudentName());
                txtStudentEmail.setText(newValue.getEmail());
                txtStudentContact.setText(newValue.getContact());
                txtStudentAddress.setText(newValue.getAddress());
                txtStudentNic.setText(newValue.getNic());


                txtStudentId.setDisable(false);
                txtStudentName.setDisable(false);
                txtStudentEmail.setDisable(false);
                txtStudentContact.setDisable(false);
                txtStudentAddress.setDisable(false);
                txtStudentNic.setDisable(false);
            }
        });

        txtStudentNic.setOnAction(event -> btnSave.fire());
    }

    /**
     * Load All Students.
     */
    private void loadAllStudents() {
        tblStudents.getItems().clear();
        //Get all Student/
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Student");

            while (rst.next()) {
                tblStudents.getItems().add(new StudentTM(rst.getString("studentId"), rst.getString("studentName"), rst.getString("email"), rst.getString("contact"), rst.getString("address"), rst.getString("nic")));
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Add New Students.
     */
    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtStudentId.setDisable(false);
        txtStudentName.setDisable(false);
        txtStudentEmail.setDisable(false);
        txtStudentContact.setDisable(false);
        txtStudentAddress.setDisable(false);
        txtStudentNic.setDisable(false);

        txtStudentId.clear();
        txtStudentName.clear();
        txtStudentEmail.clear();
        txtStudentContact.clear();
        txtStudentAddress.clear();
        txtStudentNic.clear();

        txtStudentId.setText(generateNewStudentId());
        txtStudentName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudents.getSelectionModel().clearSelection();
    }

    /**
     * Generate New Student ID.
     */
    private String generateNewStudentId() {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                ResultSet rst = connection.createStatement().executeQuery("SELECT studentId FROM Student ORDER BY studentId DESC LIMIT 1");
                if (rst.next()) {
                    String id = rst.getString("studentId");
                    int newItemId = Integer.parseInt(id.replace("STU-", "")) + 1;
                    return String.format("STU-%03d", newItemId);
                } else {
                    return "STU-001";
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return "STU-001";
    }

    /**
     * Save Students.
     */
    public void btnSave_OnAction(ActionEvent actionEvent) {
        Student s = new Student(txtStudentId.getText(), txtStudentName.getText(), txtStudentEmail.getText(), txtStudentContact.getText(), txtStudentAddress.getText(), txtStudentNic.getText());
        try {
            if (CrudUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?,?)", s.getStudentId(), s.getStudentName(), s.getEmail(), s.getContact(), s.getAddress(), s.getNic())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                loadAllStudents();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    /**
     * Delete Students.
     */
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Student WHERE studentId=?", txtStudentId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllStudents();
                txtStudentId.clear();
                txtStudentName.clear();
                txtStudentEmail.clear();
                txtStudentContact.clear();
                txtStudentAddress.clear();
                txtStudentNic.clear();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
    }
}
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Student;

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
    public TableView <Student>tblStudents;
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
    }

    private void loadAllStudents() {
    }

    public void btnAddNew_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }
}
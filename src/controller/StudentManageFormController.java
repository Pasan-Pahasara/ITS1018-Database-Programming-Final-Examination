package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import views.tm.StudentTM;

import java.sql.*;

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
    public TableView<StudentTM> tblStudents;
    public JFXButton btnAddNew;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXTextField searchID;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
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

        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String email = txtStudentEmail.getText();
        String contact = txtStudentContact.getText();
        String address = txtStudentAddress.getText();
        String nic = txtStudentNic.getText();

        if (btnSave.getText().equalsIgnoreCase("save")) {
            //Save Customer
            try {
                if (existStudent(id)) {
                    new Alert(Alert.AlertType.WARNING, "Save Student Warning..").show();
                }
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Student (studentId,studentName, email,contact,address, nic) VALUES (?,?,?,?,?,?)");
                pstm.setString(1, id);
                pstm.setString(2, name);
                pstm.setString(3, email);
                pstm.setString(4, contact);
                pstm.setString(5, address);
                pstm.setString(6, nic);
                pstm.executeUpdate();
                tblStudents.getItems().add(new StudentTM(id, name, email, contact, address, nic));
                new Alert(Alert.AlertType.CONFIRMATION, "Save Student..!").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Fail Save Student..").show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            //Update customer
            try {
                if (!existStudent(id)) {
                    new Alert(Alert.AlertType.WARNING, "Update Student Warning..").show();
                }
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Student SET studentName=?, email=?,contact=?, address=?,nic=? WHERE studentId=?");
                pstm.setString(1, name);
                pstm.setString(2, email);
                pstm.setString(3, contact);
                pstm.setString(4, address);
                pstm.setString(5, nic);
                pstm.setString(6, id);
                pstm.executeUpdate();
                new Alert(Alert.AlertType.CONFIRMATION, "Update Student..!").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Fail Update Student..").show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTM selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
            selectedStudent.setStudentName(name);
            selectedStudent.setEmail(email);
            selectedStudent.setContact(contact);
            selectedStudent.setAddress(address);
            selectedStudent.setNic(nic);
            tblStudents.refresh();
        }

        btnAddNew.fire();
    }

    /**
     * Exist Students.
     */
    private boolean existStudent(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT studentId FROM Student WHERE studentId=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
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

    /**
     * Search Students.
     */
    public void search() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Student WHERE studentId=?", searchID.getText());
        if (result.next()) {

            txtStudentId.setText(result.getString(1));
            txtStudentName.setText(result.getString(2));
            txtStudentEmail.setText(result.getString(3));
            txtStudentContact.setText(result.getString(4));
            txtStudentAddress.setText(result.getString(5));
            txtStudentNic.setText(result.getString(6));
            searchID.clear();
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
        tblStudents.refresh();
    }
}
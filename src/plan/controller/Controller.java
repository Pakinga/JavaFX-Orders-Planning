package plan.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import plan.model.Plan;
import plan.model.PlanDAO;
import plan.model.User;
import plan.model.UserDAO;
import plan.utils.Validation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private Label error;
    @FXML
    private TextField regUser;
    @FXML
    private PasswordField regPassw;
    @FXML
    private PasswordField regConfPassw;
    @FXML
    private TextField regEmail;
    @FXML
    private ComboBox regTeam;
    @FXML
    private CheckBox admin;
    @FXML
    private Label regError;
    @FXML
    private TextField orderNo;
    @FXML
    private CheckBox cbK;
    @FXML
    private CheckBox cbM;
    @FXML
    private CheckBox cbJ;
    @FXML
    private CheckBox cbP;
    @FXML
    private CheckBox cbA;
    @FXML
    private CheckBox cbS;
    @FXML
    private RadioButton rbScs;
    @FXML
    private RadioButton rbMko;
    @FXML
    private RadioButton rbSko;
    @FXML
    private RadioButton rbZko;
    @FXML
    private ComboBox comboStatus;
    @FXML
    private Label warning;
    @FXML
    private TextField plannedTime;
    @FXML
    private TextField actualTime;
    @FXML
    private Label logname;
    @FXML
    private TableView table;
    @FXML
    private Label role;


    ResultSet rsAllEntries;
    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    public void login(ActionEvent event) {
        if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.login(username.getText(), password.getText());
            if (msg.contains("Successful")) {
                User user = userDAO.getUser(username.getText());
                dashboard(event, user);
                error.setText("You made log in");
                //
            } else {
                error.setText(msg);
            }

        } else {
            error.setText("Wrong user name or password!");
        }
        error.setVisible(true);
    }

    public void loadLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 450, 350));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(ActionEvent event) {
        try {
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerLogin(ActionEvent event) {
        boolean isRegistered = true;

        // clear errors on btn pressed
        regError.setText("");
        if (!Validation.isValidUsername(regUser.getText())) {
            regError.setText("Username is incorrect (letters and numbers only, at least 5 char)");
            isRegistered = false;
        } else if (!Validation.isValidPassword(regPassw.getText())) {
            regError.setText("Password is incorrect (letters and numbers only, at least 5 char)");
            isRegistered = false;
        } else if (!regConfPassw.getText().equals(regPassw.getText())) {
            regError.setText("Password doesn't match");
            isRegistered = false;
        } else if (!Validation.isValidEmail(regEmail.getText())) {
            regError.setText("Email is not correct, pattern- name@one.org");
            isRegistered = false;
        } else if (regTeam.getValue() == null) { //(!regTeam.getSelectionModel().isEmpty())
            regError.setText("Team name is not sellected");
            isRegistered = false;
        }

        if (isRegistered) {
            User user = new User(regUser.getText(), regPassw.getText(), regEmail.getText(), (String) regTeam.getValue(), admin.isSelected());
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.register(user);
            if (msg.contains("successfully")) {
                try {
                    loadLogin();
                    // hides current stage (window)
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                regError.setText(msg);
            }
        }
    }

    public void dashboard(ActionEvent event, User user) {
        try {
            // we are in controller folder, but our view is not here, so we need to go one step up - ../
            Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Dashboard");

            // scrollBar is added into dashboard
            ScrollPane sp = new ScrollPane();
            sp.setContent(root);
            stage.setScene(new Scene(sp, 1300, 850));

            //style.css is uploaded, because disabled buttons(update & delete) should be displayed with opacity
            if (!user.isAdmin()) {
                sp.getStylesheets().add(String.valueOf(getClass().getResource("../view/buttonStyle.css")));
            }
            sp.getStylesheets().add(String.valueOf(getClass().getResource("../view/style.css")));
            Label lblTeam = (Label) root.lookup("#team");
            Label lblLoginName = (Label) root.lookup("#logname");
            Label lblLoginRole = (Label) root.lookup("#role");
            if (lblTeam != null) lblTeam.setText(user.getTeamName());
            if (lblLoginName != null) lblLoginName.setText(user.getUsername());
            if (lblLoginRole != null) lblLoginRole.setText(user.isAdmin() ? "Leader" : "Member");

            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        String orderNumber = orderNo.getText();
        int orderNum = 0;
        String planTime = plannedTime.getText();
        int plTime = 0;
        String realTime = actualTime.getText();
        int actlTime = 0;

        String worker = "";

        if (cbJ.isSelected()) {
            worker += cbJ.getText() + ",";
        }
        if (cbK.isSelected()) {
            worker += cbK.getText() + ",";
        }
        if (cbM.isSelected()) {
            worker += cbM.getText() + ",";
        }
        if (cbP.isSelected()) {
            worker += cbP.getText() + ",";
        }
        if (cbA.isSelected()) {
            worker += cbA.getText() + ",";
        }
        if (cbS.isSelected()) {
            worker += cbS.getText() + ",";
        }
        worker = worker.substring(0, worker.length() - 1);

        String productType = "";
        if (rbScs.isSelected()) {
            productType += rbScs.getText();
        } else if (rbSko.isSelected()) {
            productType += rbSko.getText();
        } else if (rbMko.isSelected()) {
            productType += rbMko.getText();
        } else if (rbZko.isSelected()) {
            productType += rbZko.getText();
        }

        String orderStatus = "";
        if (!comboStatus.getSelectionModel().isEmpty()) {
            orderStatus += comboStatus.getValue();
        } else {
            warning.setText("Please check team members");
        }

        if (orderNumber.equals("")){
            warning.setText("Order number required");
        } else if (!Validation.isValidOrderNumber(orderNumber)) {
            warning.setText("Order number should be in 9XXXXXX format");
        } else  if (planTime.equals("")){
            warning.setText("Planned time required");
        } else if (!Validation.isValidTime(planTime)) {
            warning.setText("Planned time is incorrect. Enter number 1-99");
        } else if (!actualTime.getText().isEmpty() && !Validation.isValidTime(realTime)) {
            warning.setText("Actual time is incorrect. Enter number 1-99");
        } else {
            orderNum = Integer.parseInt(orderNo.getText());
            plTime = Integer.parseInt(plannedTime.getText());
            if (actualTime.getText().isEmpty()) {
                actlTime = 0;
            } else {
                actlTime = Integer.parseInt(actualTime.getText());
            }

            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUser(logname.getText());
            PlanDAO planDAO = new PlanDAO();
            int userId = user.getId();
            Plan plan = new Plan(orderNum, productType, worker, plTime, actlTime, orderStatus, userId);
            String msg = planDAO.add(plan);
            warning.setText(msg);
            updateTableFromDB(orderNumber);
        }

    }

    public void update() {
        if(role.getText().equals("Leader")) {
            String orderNumber = orderNo.getText();
            int orderNum = 0;
            String planTime = plannedTime.getText();
            int plTime = 0;
            String realTime = actualTime.getText();
            int actlTime = 0;

            String worker = "";

            if (cbJ.isSelected()) {
                worker += cbJ.getText() + ",";
            }
            if (cbK.isSelected()) {
                worker += cbK.getText() + ",";
            }
            if (cbM.isSelected()) {
                worker += cbM.getText() + ",";
            }
            if (cbP.isSelected()) {
                worker += cbP.getText() + ",";
            }
            if (cbA.isSelected()) {
                worker += cbA.getText() + ",";
            }
            if (cbS.isSelected()) {
                worker += cbS.getText() + ",";
            }
            worker = worker.substring(0, worker.length() - 1);

            String productType = "";
            if (rbScs.isSelected()) {
                productType += rbScs.getText();
            } else if (rbSko.isSelected()) {
                productType += rbSko.getText();
            } else if (rbMko.isSelected()) {
                productType += rbMko.getText();
            } else if (rbZko.isSelected()) {
                productType += rbZko.getText();
            }

            String orderStatus = "";
            if (!comboStatus.getSelectionModel().isEmpty()) {
                orderStatus += comboStatus.getValue();
            } else {
                warning.setText("Please check team members");
            }

            if (orderNumber.equals("")) {
                warning.setText("Order number required");
            } else if (!Validation.isValidOrderNumber(orderNumber)) {
                warning.setText("Order number should be in 9XXXXXX format");
            } else if (planTime.equals("")) {
                warning.setText("Planned time required");
            } else if (!Validation.isValidTime(planTime)) {
                warning.setText("Planned time is incorrect. Enter number 1-99");
            } else if (!actualTime.getText().isEmpty() && !Validation.isValidTime(realTime)) {
                warning.setText("Actual time is incorrect. Enter number 1-99");
            } else {
                orderNum = Integer.parseInt(orderNo.getText());
                plTime = Integer.parseInt(plannedTime.getText());
                if (actualTime.getText().isEmpty()) {
                    actlTime = 0;
                } else {
                    actlTime = Integer.parseInt(actualTime.getText());
                }

                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUser(logname.getText());
                PlanDAO planDAO = new PlanDAO();
                int userId = user.getId();
                Plan plan = new Plan(orderNum, productType, worker, plTime, actlTime, orderStatus, userId);
                planDAO.editByOrderNum(plan);
                updateTableFromDB(orderNumber);
            }
        } else {
            clearFields();
            warning.setText("Update feature is only for Team Leader");
        }
    }

    public void searchOrder() {
        updateTableFromDB(orderNo.getText());
    }

    public void delete() {
        if (role.getText().equals("Leader")) {
            PlanDAO planDAO = new PlanDAO();
            planDAO.deleteOrderNum(Integer.parseInt((String) orderNo.getText()));
            updateTableFromDB("");
        } else {
            clearFields();
            warning.setText("Delete feature is only for team leader");
        }
    }

    public void updateTableFromDB(String orderNum) {
        PlanDAO planDAO = new PlanDAO();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(logname.getText());
        try {
            rsAllEntries = planDAO.searchByOrderNumber(orderNum, user);
        } catch (NullPointerException e) {
            warning.setText("No rows to display");
        }
        clearFields();
        fetColumnList();
        fetRowList();

    }



    // po duomenų įvedimo išvalo nurodytus laukus, kad būtų patogiau suvesti naujus duomenis
    private void clearFields() {
        orderNo.clear();
        orderNo.setPromptText("9XXXXXX");
        cbK.setSelected(false);
        cbM.setSelected(false);
        cbJ.setSelected(false);
        cbP.setSelected(false);
        cbA.setSelected(false);
        cbS.setSelected(false);
        plannedTime.clear();
        actualTime.clear();
        comboStatus.valueProperty().set(null);
    }

    public void fetColumnList() {

        try {

            table.getColumns().clear();
            if (rsAllEntries != null) {
                //SQL FOR SELECTING ALL OF CUSTOMER

                for (int i = 0; i < rsAllEntries.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn();
                    switch (rsAllEntries.getMetaData().getColumnName(i + 1)) {
                        case "order_number":
                            col.setText("Order Number");
                            break;
                        case "product_type":
                            col.setText("Produt Type");
                            break;
                        case "worker":
                            col.setText("Worker");
                            break;
                        case "planned_time":
                            col.setText("Planned Time");
                            break;
                        case "actual_time":
                            col.setText("Actual Time");
                            break;
                        case "order_status":
                            col.setText("Order Status");
                            break;
                        default:
                            col.setText(rsAllEntries.getMetaData().getColumnName(i + 1)); //if column name in SQL Database is not found, then TableView column receive SQL Database current column name (not readable)
                            break;
                    }
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    table.getColumns().removeAll(col);
                    table.getColumns().addAll(col);
                    System.out.println("Column [" + i + "] ");
                }

            } else {
                warning.setText("No columns to display");
            }
        } catch (SQLException e) {
            System.out.println("Failure getting column data from SQL ");
        }
    }

    //fetches rows and data from the list
    public void fetRowList() {
        try {
            data.clear();
            if (rsAllEntries != null) {
                while (rsAllEntries.next()) {
                    //Iterate Row
                    ObservableList row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rsAllEntries.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rsAllEntries.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);
                }
                //connects table with list
                table.setItems(data);
            } else {
                warning.setText("No rows to display");
            }
        } catch (SQLException ex) {
            System.out.println("Failure getting row data from SQL ");
        }
    }

    public void logOutDashboard(ActionEvent event) {

        loadLogin();
        // hides current stage (window)
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }



}

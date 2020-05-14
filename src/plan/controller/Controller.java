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
import javafx.stage.Stage;
import javafx.util.Callback;
import plan.model.Plan;
import plan.model.PlanDAO;
import plan.model.User;
import plan.model.UserDAO;
import plan.utils.Validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TextField regSurname;
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

    // Warning strings are used in create(), update(), updateActualTime(), showOrder():
    String emptyStatus = "Please check order status";
    String emptyOrderNum = "Order number required";
    String workerNotSelected = "Please select worker(s)";
    String productTypeNotSelected = "Please select product type";
    String invalidOrderNum = "Order number should be in 9XXXXXX format";
    String emptyPlannedTime = "Planned time required";
    String invalidPlannedTime = "Planned time is incorrect. Enter number 1-99";
    String invalidActualTime = "Actual time is incorrect. Enter number 0-99";

    ResultSet rsAllEntries;
    ObservableList<ObservableList> data = FXCollections.observableArrayList();
    ArrayList<RadioButton> rbProductType = new ArrayList();
    ArrayList<CheckBox> cbWorker = new ArrayList();


    public void login(ActionEvent event) {
        if (Validation.isValidUsername(username.getText()) && Validation.isValidPassword(password.getText())) {
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.login(username.getText(), password.getText());
            if (msg.contains("Successful")) {
                User user = userDAO.getUser(username.getText());
                dashboard(event, user);
                error.setText("You made log in");
            } else {
                error.setText(msg);
            }
        } else {
            error.setText("No user found under these credentials");
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
            stage.setScene(new Scene(root, 450, 500));
            root.getStylesheets().add(String.valueOf(getClass().getResource("../view/RegisterStyle.css")));
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
            regError.setText("Username is incorrect (letters and numbers only, at least 5 char.)");
            isRegistered = false;
        } else if (!Validation.isValidSurname(regSurname.getText())) {
            regError.setText("Name Surname is incorrect (pattern - N.Surname, at least 4 char.)");
            isRegistered = false;
        } else if (!Validation.isValidPassword(regPassw.getText())) {
            regError.setText("Password is incorrect (letters and numbers only, at least 5 char.)");
            isRegistered = false;
        } else if (!regConfPassw.getText().equals(regPassw.getText())) {
            regError.setText("Password doesn't match");
            isRegistered = false;
        } else if (!Validation.isValidEmail(regEmail.getText())) {
            regError.setText("Email is not correct, pattern- name@job.com");
            isRegistered = false;
        } else if (regTeam.getValue() == null) { //(!regTeam.getSelectionModel().isEmpty())
            regError.setText("Team name is not sellected");
            isRegistered = false;
        }

        if (isRegistered) {
            User user = new User(regUser.getText(), regSurname.getText(), regPassw.getText(), regEmail.getText(), (String) regTeam.getValue(), admin.isSelected());
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.register(regUser.getText(), user);
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

            // disabled buttons(update & delete, etc.) should be displayed with opacity
            if (!user.isAdmin()) {
                sp.getStylesheets().add(String.valueOf(getClass().getResource("../view/buttonStyleMember.css")));
            } else sp.getStylesheets().add(String.valueOf(getClass().getResource("../view/buttonStyleLeader.css")));
            sp.getStylesheets().add(String.valueOf(getClass().getResource("../view/style.css")));
            Label lblTeam = (Label) root.lookup("#team");
            Label lblLoginName = (Label) root.lookup("#logname");
            Label lblLoginRole = (Label) root.lookup("#role");
            if (lblTeam != null) lblTeam.setText(user.getTeamName());
            if (lblLoginName != null) lblLoginName.setText(user.getUsername());
            //Team leader has admin rights
            if (lblLoginRole != null) lblLoginRole.setText(user.isAdmin() ? "Leader" : "Member");
            CheckBox cbK = (CheckBox) root.lookup("#cbK");
            CheckBox cbA = (CheckBox) root.lookup("#cbA");
            CheckBox cbJ = (CheckBox) root.lookup("#cbJ");
            CheckBox cbM = (CheckBox) root.lookup("#cbM");
            CheckBox cbP = (CheckBox) root.lookup("#cbP");
            CheckBox cbS = (CheckBox) root.lookup("#cbS");
            ArrayList<CheckBox> cb = new ArrayList<>();
            cb.add(cbK);
            cb.add(cbA);
            cb.add(cbJ);
            cb.add(cbM);
            cb.add(cbP);
            cb.add(cbS);
            for (int i = 0; i < cb.size(); i++) {
                if (cb.get(i).getText().equals(user.getNameSurname())) {
                    cb.get(i).setSelected(true);
                }
            }
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        int orderNum = 0;
        int plTime = 0;
        int actlTime = 0;
        String worker = workerSelected();
        String productType = productTypeSelected();
        String orderStatus = "";
        Boolean validData = validEntries();
        if (validData) {
            orderNum = Integer.parseInt(orderNo.getText());
            orderStatus += comboStatus.getValue();
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
            updateTableFromDB(orderNo.getText());
        }
    }
    // Function validates whether all entries of dashboard are correct
    public Boolean validEntries() {
        String orderNumber = orderNo.getText();
        String planTime = plannedTime.getText();
        String realTime = actualTime.getText();
        String worker = workerSelected();
        String productType = productTypeSelected();
        Boolean validData = false;
        if (orderNumber.equals("")) {
            warning.setText(emptyOrderNum);
        } else if (!Validation.isValidOrderNumber(orderNumber)) {
            warning.setText(invalidOrderNum);
        } else if (worker.equals("")) {
            warning.setText(workerNotSelected);
        } else if (productType.equals("")) {
            warning.setText(productTypeNotSelected);
        } else if (planTime.equals("")) {
            warning.setText(emptyPlannedTime);
        } else if (!Validation.isValidPlannedTime(planTime)) {
            warning.setText(invalidPlannedTime);
        } else if (!actualTime.getText().isEmpty() && !Validation.isValidActualTime(realTime)) {
            warning.setText(invalidActualTime);
        } else if (comboStatus.getSelectionModel().isEmpty()) {
            warning.setText(emptyStatus);
        } else validData = true;
        return validData;
    }
    public void update() {
        if (role.getText().equals("Leader")) {
            warning.setText("");
            int orderNum = 0;
            int plTime = 0;
            int actlTime = 0;
            String worker = workerSelected();
            String productType = productTypeSelected();
            String orderStatus = "";
            Boolean validData = validEntries();
            if (validData) {
                orderNum = Integer.parseInt(orderNo.getText());
                orderStatus += comboStatus.getValue();
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
                updateTableFromDB(orderNo.getText());
                warning.setText("Order " + orderNum + " updated successfully");
            }
        } else {
            clearFields();
            warning.setText("Update feature is only for Team Leader");
        }
    }
    /* Function finds which worker(s) (CheckBox) is (are) selected
     * return worker - string containing all selected workers
     */
    public String workerSelected() {
        String worker = "";
        cbListAddElement();
        for (int i = 0; i < cbWorker.size(); i++) {
            if (cbWorker.get(i).isSelected()) {
                worker += cbWorker.get(i).getText() + ", ";
            }
        }
        if (!worker.equals("")) {
            worker = worker.substring(0, worker.length() - 2);
        }
        return worker;
    }

    /* Function finds what product type is  selected
     * return productType - name of selected product
     */
    public String productTypeSelected() {
        String productType = "";
        rbListAddElement();
        for (int i = 0; i < rbProductType.size(); i++) {
            if (rbProductType.get(i).isSelected()) {
                productType += (rbProductType.get(i).getText());
                System.out.println("Selected product type :" + productType);
            }
        }
        return productType;
    }
    
    public void searchOrder() {
        warning.setText("");
        updateTableFromDB(orderNo.getText());
    }

    public void delete() {
        warning.setText("");
        if (role.getText().equals("Leader")) {
            PlanDAO planDAO = new PlanDAO();
            planDAO.deleteOrder(Integer.parseInt((String) orderNo.getText()));
            warning.setText("Order " + orderNo.getText() + " was deleted successfully");
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
    /* Function updates actual time, if team member is logged in,
     * because they do not have permission to update order.
     * only team leader has this permission
     */
    public void updateActualTime() {
        if (role.getText().equals("Member")) {
            warning.setText("");
            String orderNumber = orderNo.getText();
            int orderNum = 0;
            String realTime = actualTime.getText();
            int actlTime = 0;
            if (orderNumber.equals("")) {
                warning.setText(emptyOrderNum);
            } else if (!Validation.isValidOrderNumber(orderNumber)) {
                warning.setText(invalidOrderNum);
            } else if (actualTime.getText().isEmpty() || !Validation.isValidActualTime(realTime)) {
                warning.setText(invalidActualTime);
            } else {
                orderNum = Integer.parseInt(orderNo.getText());
                actlTime = Integer.parseInt(actualTime.getText());

                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUser(logname.getText());
                PlanDAO planDAO = new PlanDAO();
                int userId = user.getId();
                Plan plan = new Plan(orderNum, actlTime);
                planDAO.editActualTime(plan, userId);
                updateTableFromDB(orderNumber);
                warning.setText("Actual time of order " + orderNum + " updated successfully ");
            }
        } else {
            warning.setText("Please use Update button");
        }
    }
    /* Function fills dashboard fields with data received from data base according order number
     * its role is to simplify users work.
     * in one button press user gets all data of specified order,
     * therefore demand to rewrite not edited fields is eliminated
     */
    public void showOrder() {
        warning.setText("");
        String orderNumber = orderNo.getText();
        if (orderNumber.equals("")) {
            warning.setText(emptyOrderNum);
        } else if (!Validation.isValidOrderNumber(orderNumber)) {
            warning.setText(invalidOrderNum);
        } else {
            PlanDAO planDAO = new PlanDAO();
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUser(logname.getText());
            try {
                rsAllEntries = planDAO.showByOrderNumber(orderNumber, user);
            } catch (NullPointerException e) {
                warning.setText("No data to display");
            }
            String productType = "";
            String worker = "";
            String workersList[] = new String[6];
            String plTime = "";
            String actTime = "";
            String orderStatus = "";
            try {
                if (rsAllEntries.next()) {
                    ObservableList order = FXCollections.observableArrayList();
                    for (int i = 1; i <= rsAllEntries.getMetaData().getColumnCount(); i++) {
                        order.add(rsAllEntries.getString(i));
                    }
                    productType = (String) order.get(1);
                    plTime = (String) order.get(3);
                    actTime = (String) order.get(4);
                    orderStatus = (String) order.get(5);
                    worker = (String) order.get(2);
                    int countCommas = 0;
                    if (worker.contains(",")) {
                        Pattern pattern = Pattern.compile("[^,]*,");
                        Matcher matcher = pattern.matcher(worker);
                        while (matcher.find()) {
                            countCommas++;
                        }
                        System.out.println("Number of commas: " + countCommas);
                        String[] parts = worker.split(", ", countCommas + 1);
                        for (int i = 0; i < countCommas + 1; i++) {
                            workersList[i] = parts[i];
                            System.out.println(i + ". parts " + parts[i]);
                            System.out.println("workersList " + workersList[i]);
                        }
                    } else workersList[0] = worker;
                    System.out.println("Order data added " + order);
                } else {
                    warning.setText("No data to display");
                }

            } catch (SQLException ex) {
                System.out.println("Failure getting order data from SQL ");
                ex.printStackTrace();
            }
            orderNo.setText(orderNumber);
            rbListAddElement();
            for (int i = 0; i < rbProductType.size(); i++) {
                selectRadioButton(rbProductType.get(i), productType);
            }
            cbListAddElement();
            for (int i = 0; i < cbWorker.size(); i++) {
                selectCheckBox(cbWorker.get(i), workersList);
            }
            plannedTime.setText(plTime);
            actualTime.setText(actTime);
            comboStatus.valueProperty().set(orderStatus);
        }
    }

    // Function adds all check boxes into ArrayList
    public void cbListAddElement() {
        cbWorker.clear();
        cbWorker.add(cbJ);
        cbWorker.add(cbK);
        cbWorker.add(cbM);
        cbWorker.add(cbP);
        cbWorker.add(cbA);
        cbWorker.add(cbS);
    }

    // Function adds all radio buttons into ArrayList
    public void rbListAddElement() {
        rbProductType.clear();
        rbProductType.add(rbScs);
        rbProductType.add(rbSko);
        rbProductType.add(rbMko);
        rbProductType.add(rbZko);
    }

    // Function checks whether Radio button is selected
    private void selectRadioButton(RadioButton radioButton, String rbText) {
        if (radioButton.getText().equals(rbText)) {
            radioButton.setSelected(true);
        } else radioButton.setSelected(false);
    }

    // Function checks whether CheckBox is selected
    private void selectCheckBox(CheckBox checkBox, String cbText[]) {
        checkBox.setSelected(false);
        for (int i = 0; i < cbText.length; i++) {
            if (checkBox.getText().equals(cbText[i])) {
                checkBox.setSelected(true);
            }
        }
    }

    // fields are cleared after data is entered
    private void clearFields() {
        orderNo.clear();
        orderNo.setPromptText("9XXXXXX");
        if (rbProductType.isEmpty()) {
            rbListAddElement();
        }
        for (int i = 0; i < rbProductType.size(); i++) {
            rbProductType.get(i).setSelected(false);
        }
        if (cbWorker.isEmpty()) {
            cbListAddElement();
        }
        for (int i = 0; i < cbWorker.size(); i++) {
            cbWorker.get(i).setSelected(false);
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(logname.getText());
        for (int i = 0; i < cbWorker.size(); i++) {
            if(cbWorker.get(i).getText().equals(user.getNameSurname())) {
                cbWorker.get(i).setSelected(true);
            } else {
                cbWorker.get(i).setSelected(false);
            }
        }
        plannedTime.clear();
        actualTime.clear();
        comboStatus.valueProperty().set(null);
    }

    // Function creates columns for TableView
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
                }
            } else {
                warning.setText("No columns to display");
            }
        } catch (SQLException e) {
            System.out.println("Failure getting column data from SQL ");
        }
    }

    // Function creates rows for TableView, by fetching rows and data from the list
    public void fetRowList() {
        try {
            data.clear();
            if (rsAllEntries != null) {
                ObservableList row = null;
                //Iterate Row
                while (rsAllEntries.next()) {
                    row = FXCollections.observableArrayList();
                    //Iterate Column
                    for (int i = 1; i <= rsAllEntries.getMetaData().getColumnCount(); i++) {
                        row.add(rsAllEntries.getString(i));
                    }
                    data.add(row);
                }
                //connects table with list
                table.setItems(data);
                // cell instances are generated for Order Status column and then colored
                customiseStatusCells();

            } else {
                warning.setText("No rows to display");
            }
        } catch (SQLException ex) {
            System.out.println("Failure getting row data from SQL ");
        }
    }

    // cell instances are generated for Order Status column
    public void customiseStatusCells() {
        TableColumn column = table.getVisibleLeafColumn(5);
        column.setCellFactory(new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                //cell implementation
                return new styleCell();
            }
        });
    }

    // Cells of Order Status column are colored according cell value
    static class styleCell extends TableCell<String, String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText("");
            } else {
                if (item.equals("Scheduled")) {
                    setText(item);
                    setStyle("-fx-background-color: #eb6589; -fx-border-color:  #f08ea8; -fx-border-width: 0 0 1px 0;");
                } else if (item.equals("Completed")) {
                    setText(item);
                    setStyle("-fx-background-color: #adf5ab; -fx-border-color:  #92d590; -fx-border-width: 0 0 1px 0;");
                } else if (item.equals("Processing")) {
                    setText(item);
                    setStyle("-fx-background-color: #f7f194; -fx-border-color:  #e9e063; -fx-border-width: 0 0 1px 0;");
                }
            }
        }
    }
    /* Function closes current window (register or dashboard) and opens log in window
     * return worker - string containing all selected workers
     */
    public void backToLoginWindow(ActionEvent event) {
        loadLogin();
        // hides current stage (window)
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

}

package plan.model;

import java.sql.*;
import plan.utils.Constant;
import java.util.ArrayList;

public class PlanDAO {

    public String add(Plan plan){
        String query = "insert into " + Constant.TABLE_NAME1 + " (order_number, product_type, worker, planned_time, actual_time, order_status, user_id)" +
                "values (?,?,?,?,?,?,?)";
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, plan.getOrderNum());
            preparedStatement.setString(2, plan.getProductType());
            preparedStatement.setString(3, plan.getWorker());
            preparedStatement.setInt(4, plan.getPlannedTime());
            preparedStatement.setInt(5, plan.getActualTime());
            preparedStatement.setString(6, plan.getOrderStatus());
            preparedStatement.setInt(7, plan.getUserId());
            preparedStatement.executeUpdate();
            return "Successfully created new entry";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure creating new entry";
        }
    }
    public ResultSet searchByOrderNumber(String orderNum, User user) {
        String query2 = "";
        ArrayList<Plan> plan = new ArrayList<>();
        if (user.isAdmin()) {
            if (orderNum.equals("")) {
                query2 = "SELECT order_number, product_type, worker, planned_time, actual_time, order_status, user_id FROM " + Constant.TABLE_NAME1;
            } else
                query2 = "SELECT order_number, product_type, worker, planned_time, actual_time, order_status, user_id FROM " + Constant.TABLE_NAME1 + " WHERE order_number LIKE \"%" + orderNum + "%\" ";
        } else {
            if (orderNum.equals("")) {
                query2 = "SELECT order_number, product_type, worker, planned_time, actual_time, order_status, user_id FROM " + Constant.TABLE_NAME1 + " WHERE user_id = " + user.getId();
            } else
                query2 = "SELECT order_number, product_type, worker, planned_time, actual_time, order_status, user_id FROM " + Constant.TABLE_NAME1 + " WHERE user_id = " + user.getId() + " AND order_number LIKE \"%" + orderNum + "%\" ";
        }
        System.out.println("is admin? " + user.isAdmin());
        System.out.println(query2);

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            preparedStatement = connection.prepareStatement(query2);
            resultSet = preparedStatement.executeQuery(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}

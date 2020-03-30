package plan.model;

import java.sql.*;

import plan.utils.Constant;

import java.util.ArrayList;

public class PlanDAO {

    public String add(Plan plan) {
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
        String query2 = "SELECT order_number, product_type, worker, planned_time, actual_time, order_status, user_id FROM " + Constant.TABLE_NAME1;
        if (user.isAdmin()) {
            if (!orderNum.equals("")) {
                query2 += " WHERE order_number LIKE \"%" + orderNum + "%\" ";
            }
        } else {
            if (orderNum.equals("")) {
                query2 += " WHERE user_id = " + user.getId();
            } else
                query2 += " WHERE user_id = " + user.getId() + " AND order_number LIKE \"%" + orderNum + "%\" ";
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

    public ResultSet showByOrderNumber(String orderNum, User user) {
        String query2 = "SELECT order_number, product_type, worker, planned_time, actual_time, order_status, user_id FROM " + Constant.TABLE_NAME1;
        if (user.isAdmin()) {
            query2 += " WHERE order_number =" + orderNum;
        } else {
            query2 += " WHERE user_id = " + user.getId() + " AND order_number = " + orderNum;
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

    public void editByOrderNum(Plan plan) {
        String query = "UPDATE " + Constant.TABLE_NAME1 + " SET product_type=?, worker=?, planned_time=?, actual_time=?, order_status=?" +
                " WHERE order_number=?";
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, plan.getProductType());
            preparedStatement.setString(2, plan.getWorker());
            preparedStatement.setInt(3, plan.getPlannedTime());
            preparedStatement.setInt(4, plan.getActualTime());
            preparedStatement.setString(5, plan.getOrderStatus());
            preparedStatement.setInt(6, plan.getOrderNum());
            preparedStatement.executeUpdate();
            System.out.println("Data was successfully edited");
        } catch (SQLException e) {
            System.out.println("Error. Cannot edit data");
            e.printStackTrace();
        }
    }

    public void editActualTime(Plan plan, int userId) {
        String query = "UPDATE " + Constant.TABLE_NAME1 + " SET actual_time=?" +
                " WHERE user_id = " + userId + " AND order_number = ?";
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, plan.getActualTime());
            preparedStatement.setInt(2, plan.getOrderNum());
            preparedStatement.executeUpdate();
            System.out.println("Edit Actual time query: " + query);
            System.out.println("Actual time was successfully edited");
        } catch (SQLException e) {
            System.out.println("Error. Cannot edit actual time. User is not valid");
            e.printStackTrace();
        }
    }

    public void deleteOrderNum(int num) {
        String query = "DELETE FROM " + Constant.TABLE_NAME1 + " WHERE order_number=?";
        try {
            Connection connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, num);
            preparedStatement.executeUpdate();
            System.out.println("Data was successfully deleted");
        } catch (SQLException e) {
            System.out.println("Error. Cannot delete data");
            e.printStackTrace();
        }
    }


}

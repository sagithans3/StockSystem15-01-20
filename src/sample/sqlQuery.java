package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class sqlQuery {
    public void insertInProduct(String id, String products_name, String product_quantity, String product_price) throws SQLException {
        String sql = "INSERT INTO students(id, products_name, product_quantity, product_price) VALUES(?,?,?,?)";
        Connection conn = ConnectDB.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, products_name);
        pstmt.setString(3, product_quantity);
        pstmt.setString(4, product_price);
        pstmt.executeUpdate();
    }

    public void deleteInProduct(String products_name) throws SQLException {
        String sql = "DELETE FROM products WHERE products_name = ?";
        Connection conn = ConnectDB.connect();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, products_name);
        statement.executeUpdate();
    }
    public void editInProducts(String products_name, String product_quantity, String product_price, String id) throws SQLException {
        String sql = "UPDATE products SET products_name = ? ," +
                "product_quantity = ? ," +
                "product_quantity = ?" + "WHERE id = ?";
        Connection conn = ConnectDB.connect();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, products_name);
        statement.setString(2, product_quantity);
        statement.setString(3, product_price);
        statement.setString(4, id);
        statement.executeUpdate();
    }

    public void insertInBranch(String id, String branch_name) throws SQLException {
        String sql = "INSERT INTO branch(id, branch_location) VALUES(?,?)";
        Connection conn = ConnectDB.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, branch_name);
        pstmt.executeUpdate();
    }

    public void deleteInBranch(String branch_name) throws SQLException {
        String sql = "DELETE FROM products WHERE branch_location = ?";
        Connection conn = ConnectDB.connect();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, branch_name);
        statement.executeUpdate();
    }
    public void editInBranch(String branch_name, String id) throws SQLException {
        String sql = "UPDATE products SET branch_location = ? ," +
                 "WHERE id = ?";
        Connection conn = ConnectDB.connect();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, branch_name);
        statement.setString(4, id);
        statement.executeUpdate();
    }
}

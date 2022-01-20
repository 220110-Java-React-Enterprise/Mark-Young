package com.sql2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssociateRepo implements DataSourceCRUD<Associate> {

    private final Connection connection;

    public AssociateRepo() {
        connection = ConnectionMgr.getConnection();
    }


    @Override
    public Associate create(Associate model) {
        // JDBC logic here

        try {
            String sql = "INSERT INTO associates (associate_id, first_name, last_name, age) VALUES (?,?,?,?)";


            //connection.prepareStatement(sql);
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, model.getId());
            pstmt.setString(2, model.getFirstName());
            pstmt.setString(3, model.getLastName());
            pstmt.setInt(4, model.getAge());

            pstmt.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;

    }

    @Override
    public Associate read(Integer id) {
        try {
            String sql2 = "SELECT * FROM associates WHERE associate_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql2);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            Associate model = new Associate();
            while(rs.next()) {
                model.setId(rs.getInt("associate_id"));
                model.setFirstName(rs.getString("first_name"));
                model.setLastName((rs.getString("last_name")));
                model.setAge(rs.getInt("age"));
            }

            return model;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Associate update(Associate model) {
        try {
            String sql = "UPDATE associates SET first_name = ?, last_name = ?, age = ? WHERE associate_id = ?";


            //connection.prepareStatement(sql);
            PreparedStatement pstmt = connection.prepareStatement(sql);


            pstmt.setString(1, model.getFirstName());
            pstmt.setString(2, model.getLastName());
            pstmt.setInt(3, model.getAge());
            pstmt.setInt(4, model.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    @Override
    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM associates WHERE associate_id = ?";
            //connection.prepareStatement(sql);
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

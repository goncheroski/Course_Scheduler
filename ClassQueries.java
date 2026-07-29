/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static ResultSet resultSet;
    private static PreparedStatement dropClass;
    
    public static void addClass(ClassEntry classEntry) {
        connection = DBConnection.getConnection();

        try {
            addClass = connection.prepareStatement(
                "insert into class (semester, courseCode, seats) values (?, ?, ?)"
            );

            addClass.setString(1, classEntry.getSemester());
            addClass.setString(2, classEntry.getCourseCode());
            addClass.setInt(3, classEntry.getSeats());

            addClass.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>();

        try {
            getAllCourseCodes = connection.prepareStatement(
                "select courseCode from class where semester = ?"
            );

            getAllCourseCodes.setString(1, semester);

            resultSet = getAllCourseCodes.executeQuery();

            while (resultSet.next()) {
                courseCodes.add(resultSet.getString("courseCode"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return courseCodes;
    }

    public static int getClassSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;

        try {
            getClassSeats = connection.prepareStatement(
                "select seats from class where semester = ? and courseCode = ?"
            );

            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);

            resultSet = getClassSeats.executeQuery();

            if (resultSet.next()) {
                seats = resultSet.getInt("seats");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return seats;
    }
    public static void dropClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();

        try {
            dropClass = connection.prepareStatement(
                "delete from class where semester = ? and courseCode = ?"
            );

            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);

            dropClass.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
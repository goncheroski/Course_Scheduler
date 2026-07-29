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
public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into course (courseCode, description) values (?, ?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes() {
        connection = DBConnection.getConnection();
        ArrayList<String> allCourseCodes = new ArrayList<>();

        try {
            getAllCourseCodes = connection.prepareStatement(
                "select courseCode from course"
            );

            resultSet = getAllCourseCodes.executeQuery();

            while (resultSet.next()) {
                allCourseCodes.add(resultSet.getString("courseCode"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return allCourseCodes;
    }
    
    
}
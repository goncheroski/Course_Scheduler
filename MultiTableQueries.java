/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author gonch
 */
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getAllClassDescriptions;
    private static ResultSet resultSet;
    private static PreparedStatement getScheduledStudentsByClass;
    private static PreparedStatement getWaitlistedStudentsByClass;
    
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classList = new ArrayList<>();

        try {
            getAllClassDescriptions = connection.prepareStatement(
                "select class.courseCode, course.description, class.seats " +
                "from class, course " +
                "where class.courseCode = course.courseCode and class.semester = ?"
            );

            getAllClassDescriptions.setString(1, semester);
            resultSet = getAllClassDescriptions.executeQuery();

            while (resultSet.next()) {
                classList.add(new ClassDescription(
                    resultSet.getString("courseCode"),
                    resultSet.getString("description"),
                    resultSet.getInt("seats")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return classList;
    }
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();

        try {
            getScheduledStudentsByClass = connection.prepareStatement(
                "select student.studentID, student.firstName, student.lastName " +
                "from student, schedule " +
                "where student.studentID = schedule.studentID " +
                "and schedule.semester = ? " +
                "and schedule.courseCode = ? " +
                "and schedule.status = 'S' " +
                "order by student.lastName, student.firstName"
            );

            getScheduledStudentsByClass.setString(1, semester);
            getScheduledStudentsByClass.setString(2, courseCode);

            resultSet = getScheduledStudentsByClass.executeQuery();

            while (resultSet.next()) {
                students.add(new StudentEntry(
                    resultSet.getString("studentID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName")
                ));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return students;
    }

    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();

        try {
            getWaitlistedStudentsByClass = connection.prepareStatement(
                "select student.studentID, student.firstName, student.lastName " +
                "from student, schedule " +
                "where student.studentID = schedule.studentID " +
                "and schedule.semester = ? " +
                "and schedule.courseCode = ? " +
                "and schedule.status = 'W' " +
                "order by schedule.timestamp"
            );

            getWaitlistedStudentsByClass.setString(1, semester);
            getWaitlistedStudentsByClass.setString(2, courseCode);

            resultSet = getWaitlistedStudentsByClass.executeQuery();

            while (resultSet.next()) {
                students.add(new StudentEntry(
                    resultSet.getString("studentID"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName")
                ));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return students;
    }
}

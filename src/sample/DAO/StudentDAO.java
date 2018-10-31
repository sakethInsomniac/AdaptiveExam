package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Utility.dbconnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO
{
/*************************************/
 //INSERT a student details
 //*************************************
 public static void insertStu (String studentID, String studentName, String institutionName) throws SQLException, ClassNotFoundException {
 //Declare a DELETE statement
 String insertStmt =
 "BEGIN\n" +
 "INSERT INTO studentDetails\n" +
 "(studentID, studentName, institutionName)\n" +
 "VALUES\n" +
 "('"+studentID+"', '"+studentName+"','"+institutionName+"');\n" +
 "END;";

 //Execute Insert operation
 try {
 dbconnection.dbExecuteUpdate(insertStmt);
 } catch (SQLException e) {
 System.out.print("Error occurred while DELETE Operation: " + e);
 throw e;
 }
 }
}

package sample.DAO;

import javafx.beans.property.*;


public class studentDetails
{
    private StringProperty studentID;
    private StringProperty studentName;
    private StringProperty  institutionName;

    public studentDetails(String studentID, String studentName, String institutionName)
    {
        this.studentID = new SimpleStringProperty();
        this.studentName = new SimpleStringProperty();
        this.institutionName = new SimpleStringProperty();

    }
    public studentDetails()
    {


    }
    public StringProperty getStudentID() {
        return studentID;
    }
    public void getStudentID(String studentID) {
        this.studentID.set(studentID);
    }
    public StringProperty getName() {
        return studentName;
    }
    public void setName(String studentName) {
        this.studentName.set(studentName);
    }
    public StringProperty getInstitutionName() {
        return institutionName;
    }
    public void setInstitutionName(String institutionName) {
        this.institutionName.set(institutionName);
    }

}


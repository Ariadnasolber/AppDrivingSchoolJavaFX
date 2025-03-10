/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 
import java.time.LocalDate;
 
/**
*
* @author ariadnasolerbernad
*/
// Clase Student refactorizada
 
public class Student extends Person {
 
    private int approvalStatus;
    private float registrationFee;
    private Teacher teacher;
 
    // Constructor
    public Student(String name, String surname, String id, String email, LocalDate date, int approvalStatus, float registrationFee, Teacher teacher) {
        super(name, surname, id, email, date);
        this.approvalStatus = approvalStatus;
        this.registrationFee = registrationFee;
        this.teacher = teacher;
    }
 
    // Getters y Setters
    public int getApprovalStatus() {
        return approvalStatus;
    }
 
    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
 
    public float getRegistrationFee() {
        return registrationFee;
    }
 
    public void setRegistrationFee(float registrationFee) {
        this.registrationFee = registrationFee;
    }
 
    public Teacher getTeacher() {
        return teacher;
    }
 
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
 
    // Método para evaluar al estudiante
    public void evaluateStudent(int approved) {
        this.approvalStatus = approved;
    }
 
    // Método toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" Student{");
        sb.append("age=").append(getAge()); // Se usa el método heredado
        sb.append(", approvalStatus=").append(approvalStatus);
        sb.append(", registrationFee=").append(registrationFee);
        sb.append(", teacher=").append(teacher);
        sb.append('}');
        return sb.toString();
    }
}
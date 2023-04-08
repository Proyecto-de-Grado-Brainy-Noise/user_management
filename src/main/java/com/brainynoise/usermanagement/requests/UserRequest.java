package com.brainynoise.usermanagement.requests;

public class UserRequest {
    private String email;
    private String role;
    private String name;
    private String name2;
    private String lastname;
    private String lastname2;
    private int doctype;
    private String document;
    private String birthdate;
    private int idEmployee;
    private String jobtitle;
    private String area;
    private String observations;
    private String creationDate;

    public UserRequest(String email, String role, String name, String name2, String lastname, String lastname2, int doctype, String document, String birthdate, Integer idEmployee, String jobtitle, String area, String observations, String creationDate) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.name2 = name2;
        this.lastname = lastname;
        this.lastname2 = lastname2;
        this.doctype = doctype;
        this.document = document;
        this.birthdate = birthdate;
        this.idEmployee = idEmployee;
        this.jobtitle = jobtitle;
        this.area = area;
        this.observations = observations;
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname2() {
        return lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public int getDoctype() {
        return doctype;
    }

    public void setDoctype(int doctype) {
        this.doctype = doctype;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}

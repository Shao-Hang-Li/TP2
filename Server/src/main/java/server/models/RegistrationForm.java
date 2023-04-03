package server.models;

import java.io.Serializable;

/**
 * The RegistrationForm class represents a registration form for a course.
 * It contains the following attributes:
 * prenom: a String representing the first name of the student
 * nom: a String representing the last name of the student
 * email: a String representing the email of the student
 * matricule: a String representing the matriculation number of the student
 * course: a Course object representing the course the student wants to register to
 */

public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * Constructor for the RegistrationForm class.
     * @param prenom a String representing the first name of the student
     * @param nom a String representing the last name of the student
     * @param email a String representing the email of the student
     * @param matricule a String representing the matriculation number of the student
     * @param course a Course object representing the course the student wants to register to
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * Getter for the prenom attribute.
     * @return a String representing the first name of the student
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter for the prenom attribute.
     * @param prenom a String representing the first name of the student
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter for the nom attribute.
     * @return a String representing the last name of the student
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter for the nom attribute.
     * @param nom a String representing the last name of the student
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter for the email attribute.
     * @return a String representing the email of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email attribute.
     * @param email a String representing the email of the student
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the matricule attribute.
     * @return a String representing the matriculation number of the student
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Setter for the matricule attribute.
     * @param matricule a String representing the matriculation number of the student
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Getter for the course attribute.
     * @return a Course object representing the course the student wants to register to
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter for the course attribute.
     * @param course a Course object representing the course the student wants to register to
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}

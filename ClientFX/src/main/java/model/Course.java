package model;

import java.io.Serializable;

/**

 The Course class represents a course in a university.

 It contains information about the name, code, and session of the course.
 */
public class Course implements Serializable {

    /**

     The name of the course.
     */
    private String name;
    /**

     The code of the course.
     */
    private String code;
    /**

     The session of the course.
     */
    private String session;
    /**

     Constructs a new Course object with the given name, code, and session.
     @param name the name of the course
     @param code the code of the course
     @param session the session of the course
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }
    /**

     Gets the name of the course.
     @return the name of the course
     */
    public String getName() {
        return name;
    }
    /**

     Sets the name of the course.
     @param name the name of the course
     */
    public void setName(String name) {
        this.name = name;
    }
    /**

     Gets the code of the course.
     @return the code of the course
     */
    public String getCode() {
        return code;
    }
    /**

     Sets the code of the course.
     @param code the code of the course
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**

     Gets the session of the course.
     @return the session of the course
     */
    public String getSession() {
        return session;
    }
    /**

     Sets the session of the course.
     @param session the session of the course
     */
    public void setSession(String session) {
        this.session = session;
    }
    /**

     Returns a string representation of the course.
     @return a string representation of the course
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
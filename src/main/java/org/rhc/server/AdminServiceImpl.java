package org.rhc.server;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.rhc.client.AdminService;
import org.rhc.shared.Student;
import org.hibernate.Session;
import org.rhc.server.RandomPasswordUtil;
import org.hibernate.exception.ConstraintViolationException;

public class AdminServiceImpl extends RemoteServiceServlet implements AdminService{
    @Override
    public Boolean createStudent(String email, String password, String firstName, String lastName,
                                 String contact, String country, String countryCode, String school,
                                 String lecturerFirstName, String lecturerLastName, String lecturerEmail,
                                 String language) throws IllegalArgumentException {

        email = SecurityUtil.escapeInput(email);
        firstName = SecurityUtil.escapeInput(firstName);
        lastName = SecurityUtil.escapeInput(lastName);
        contact = SecurityUtil.escapeInput(contact);
        country = SecurityUtil.escapeInput(country);
        countryCode = SecurityUtil.escapeInput(countryCode);
        school = SecurityUtil.escapeInput(school);
        lecturerFirstName = SecurityUtil.escapeInput(lecturerFirstName);
        lecturerLastName = SecurityUtil.escapeInput(lecturerLastName);
        lecturerEmail = SecurityUtil.escapeInput(lecturerEmail);
        language = SecurityUtil.escapeInput(language);

        password = SecurityUtil.hashPassword(password);

        Student student = new Student();
        student.setPassword(password);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setContact(contact);
        student.setCountry(country);
        student.setCountryCode(countryCode);
        student.setSchool(school);
        student.setLecturerFirstName(lecturerFirstName);
        student.setLecturerLastName(lecturerLastName);
        student.setLecturerEmail(lecturerEmail);
        student.setLanguage(language);

        if(student.getPassword() != null){
            student.setPassword(password);
        }
        else{
            student.setPassword(randomPassword());
        }

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            return true;
        } catch (ExceptionInInitializerError e) {
            log("Error with initializing hibernate session.", e);
            throw e;
        } catch (ConstraintViolationException e) {
            log("Failed to add student due to key constraints.", e);
            return false;
        }
    }

    public String randomPassword(){
        RandomPasswordUtil randomPass = new RandomPasswordUtil();
        return String.valueOf(randomPass);
    }
}

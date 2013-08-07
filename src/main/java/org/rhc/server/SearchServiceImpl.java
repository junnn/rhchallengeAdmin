package org.rhc.server;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.rhc.client.SearchService;
import org.rhc.shared.Student;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService{

    @Override
    public List<Student> loadDB(String search, String field) {
        List<Student> studentList;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.like(field,"%" + search + "%").ignoreCase());

            studentList = criteria.list();

            session.getTransaction().commit();
            return studentList;


        } catch (HibernateException e) {
            log("Failed to retrieve profile information from the database", e);
            throw new RuntimeException("Failed to retrieve profile information from the database");
        }
        finally{
            session.close();
        }
    }

    public List<Student> displayDB() {
        List<Student> studentList;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Student.class);
            studentList = criteria.list();

            session.getTransaction().commit();
            return studentList;

        } catch (HibernateException e) {
            log("Failed to retrieve profile information from the database", e);
            throw new RuntimeException("Failed to retrieve profile information from the database");
        }
        finally{
            session.close();
        }
    }

    public Boolean updateEmail(String email, String newEmail) throws IllegalArgumentException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.eq("email", email));
            Student student = (Student)criteria.uniqueResult();

            student.setEmail(newEmail);

            session.update(student);
            session.getTransaction().commit();

            return true;
        } catch (HibernateException e) {
            log("Profile update failed", e);
            return false;
        }
        finally {
            session.close();
        }
    }

    public Boolean updateProfileData(Student studentRow) throws IllegalArgumentException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.eq("email", studentRow.getEmail()));
            Student student = (Student)criteria.uniqueResult();

            student.setEmail(studentRow.getEmail());
            student.setFirstName(studentRow.getFirstName());
            student.setPassword(studentRow.getPassword());
            student.setLastName(studentRow.getLastName());
            student.setCountryCode(studentRow.getCountryCode());
            student.setCountry(studentRow.getCountry());
            student.setContact(studentRow.getContact());
            student.setSchool(studentRow.getSchool());
            student.setLanguage(studentRow.getLanguage());
            student.setLecturerFirstName(studentRow.getLecturerFirstName());
            student.setLecturerLastName(studentRow.getLecturerLastName());
            student.setLecturerEmail(studentRow.getLecturerEmail());
            student.setVerified(studentRow.getVerified());
            student.setStatus(studentRow.getStatus());
            student.setQuestions(studentRow.getQuestions());

            session.update(student);
            session.getTransaction().commit();

            return true;
        }
        catch (HibernateException e) {
            log("Profile update failed", e);
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean deleteStudent(List<String> email) throws IllegalArgumentException {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try{
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.in("email", email));

            for(Object s : criteria.list()) {
                session.delete(s);
            }

                session.getTransaction().commit();

            return true;
        }
        catch (HibernateException e){
            log("Failed to delete user", e);
            return false;
        }
        finally {
            session.close();
        }
    }
}

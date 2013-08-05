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
            //criteria.add(Restrictions.eq("status", Boolean.FALSE));   //thinking how to use this

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
}

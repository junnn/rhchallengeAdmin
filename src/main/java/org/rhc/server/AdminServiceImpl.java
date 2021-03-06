package org.rhc.server;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.rhc.client.AdminService;
import org.rhc.shared.ConfirmationTokens;
import org.rhc.shared.Student;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AdminServiceImpl extends RemoteServiceServlet implements AdminService{

    private static final Random RANDOM = new SecureRandom();
    public static final int PASSWORD_LENGTH = 10;


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
        student.setEmail(email);
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

        class SendConfirmationEmail implements Runnable {
            String email;

            SendConfirmationEmail(String email) {
                this.email = email;
            }

            @Override
            public void run() {

                ConfirmationTokens token = new ConfirmationTokens();
                token.setToken(EmailUtil.generateToken(32));
                token.setEmail(email);
                EmailUtil.sendEmail("Confirmation of account",
                        "<html>Click here to confirm your account: " + "http://register-ayrx.rhcloud.com/register/?confirmToken=" + token.getToken() + "</html>",
                        "Your client does not support HTML messages, your token is " + token.getToken(),
                        email);

                Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
                currentSession.beginTransaction();
                currentSession.save(token);
                currentSession.getTransaction().commit();
            }
        }

        /**
         * Opens a session using Hibernate and saves the Student object.
         * Calls the SendConfirmationEmail runnable if the commit is
         * successful.
         */

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();

            Thread t = new Thread(new SendConfirmationEmail(email));
            t.start();

            return true;
        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            return false;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        }

    }
    @Override
    public boolean setConfirmationStatus(String email) throws IllegalArgumentException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {

            session.beginTransaction();

            Criteria criteria = session.createCriteria(Student.class);
            criteria.add(Restrictions.eq("email", email));
            Student student = (Student)criteria.uniqueResult();

            Set<Integer> questions = randomQuestions();
            Integer[] arr = questions.toArray(new Integer[questions.size()]);
            int[] questionsArray = ArrayUtils.toPrimitive(arr);

            student.setQuestions(questionsArray);
            student.setVerified(true);

            session.update(student);
            session.getTransaction().commit();

            return true;
        } catch (HibernateException e) {
            log("Failed to set confirmation status to true", e);
            return false;
        }
    }

    private Set<Integer> randomQuestions() {
        Random rand = new Random();
        int max;
        int min;

        Set<Integer> listOfQuestions = new HashSet<Integer>();
        int levelOne = 69;
        int levelTwo = 52;
        int levelThree = 29;

        while(listOfQuestions.size()<levelOne) {
            max = 230;
            min = 1;
            listOfQuestions.add(rand.nextInt(max - min + 1) + min);
        }
        while(listOfQuestions.size()<levelOne + levelTwo) {
            max = 406;
            min = 231;
            listOfQuestions.add(rand.nextInt(max - min + 1) + min);
        }
        while(listOfQuestions.size()<levelOne + levelTwo + levelThree) {
            max = 500;
            min = 407;
            listOfQuestions.add(rand.nextInt(max - min + 1) + min);
        }
        return listOfQuestions;
    }

    public String generateRandomPassword(){
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        String pw = "";
        for (int i=0; i<PASSWORD_LENGTH; i++)
        {
            int index = (int)(RANDOM.nextDouble()*letters.length());
            pw += letters.substring(index, index+1);
        }
        return pw;
    }


    @Override
    public Boolean createAndVerifyStudent(String email, String password, String firstName, String lastName,
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
        student.setEmail(email);
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
        student.setVerified(true);

        Set<Integer> questions = randomQuestions();
        Integer[] arr = questions.toArray(new Integer[questions.size()]);
        int[] questionsArray = ArrayUtils.toPrimitive(arr);

        student.setQuestions(questionsArray);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            return true;

        } catch (ConstraintViolationException e) {
            log("Failed to add student due to key constraints.", e);
            session.getTransaction().rollback();
            return false;
        } catch (HibernateException e) {
            log("Error with initializing hibernate session.", e);
            session.getTransaction().rollback();
            return false;
        }
    }
}

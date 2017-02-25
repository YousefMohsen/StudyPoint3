
import entity.Student;
import entity.Studypoint;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yousinho
 */
public class Facade {
    
    EntityManager em;
    EntityManagerFactory emf;
    
    public Facade() {
        Persistence.generateSchema("pu", null);
        emf = Persistence.createEntityManagerFactory("pu");
        em = emf.createEntityManager();
        
    }
    
    public List<Student> findAllStudents() {//c1
        List<Student> result = (List<Student>) em.createNamedQuery("Student.findAll").getResultList();
        System.out.println("Find all students:");
        for (Student student : result) {
            System.out.println(student.getFirstname() + " " + student.getLastname());
        }
        return result;
    }
    
    public List<Student> findAllJans() {//c2
        List<Student> result = (List<Student>) em.createNamedQuery("Student.findByFirstname").setParameter("firstname", "jan").getResultList();
        for (Student student : result) {
            System.out.println(student.getFirstname());
            System.out.println(student.getFirstname() + " " + student.getLastname());
            
        }
        return result;
    }
    
    public List<Student> findAllOlsens() {//c3
        List<Student> result = (List<Student>) em.createNamedQuery("Student.findByLastname").setParameter("lastname", "olsen").getResultList();
        for (Student student : result) {
            System.out.println(student.getLastname());
        }
        return result;
    }
    
    public int findTotalSPoints(int id) {//c4
        Student student = (Student) em.createNamedQuery("Student.findById").setParameter("id", id).getSingleResult();
        int sum = 0;        
        for (Studypoint st : student.getStudypointList()) {
            sum += st.getScore();
        }
        System.out.println(student.getFirstname() + " score: " + sum);
        return 3;
    }
    
    public int allPointAllStudents() {//c5
        int sum = 0;
        
        for (Student student : findAllStudents()) {
            
            for (Studypoint st : student.getStudypointList()) {
                sum += st.getScore();
            }
            
        }
        
        System.out.println("allPointAllStudents: " + sum);
        
        return sum;
    }
    
    public int greatestTotal() {//c6
        int max = 0;
        
        for (Student student : findAllStudents()) {
            int temp = findTotal(student);
            if (temp > max) {
                max = temp;
            }            
            
        }
        
        System.out.println("greatestTotal: " + max);
        return max;
    }
    
    public int lowestTotal() {//c7
        int max = 100;
        
        for (Student student : findAllStudents()) {
            int temp = findTotal(student);
            if (temp < max) {
                max = temp;
            }            
            
        }
        
        System.out.println("lowestTotal: " + max);
        return max;
    }
    
    private int findTotal(Student st) {
        int sum = 0;
        for (Studypoint studPoi : st.getStudypointList()) {
            sum += studPoi.getScore();
        }
        
        return sum;
    }
    
    public void createStdent(Student newStudent) {//D1
        
        try {
            em.getTransaction().begin();
            em.persist(newStudent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }
    
    public void addStudypoint(Student student, Studypoint sp) {//D2
        student.addStudypoint(sp);
        
        try {
            em.getTransaction().begin();
            em.persist(sp);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
    }
    
    public Student findStudentByID(int id) {
        Student student = (Student) em.createNamedQuery("Student.findById").setParameter("id", id).getSingleResult();
        
        return student;
    }
    
    public static void main(String[] args) {
        Facade fa = new Facade();

        // c1
        fa.findAllStudents();
        // c2
        fa.findAllJans();
        // c3
        fa.findAllOlsens();

        // c4
        fa.findTotalSPoints(1);
        // c5
        fa.allPointAllStudents();
        // c6
        fa.greatestTotal();
        //c7
        fa.lowestTotal();

        //d1
        fa.createStdent(new Student("Leo", "Messi"));
        // d2
        Student janus = fa.findStudentByID(3);
        Studypoint bla = new Studypoint("CS go credits", 10, 3);        
        fa.addStudypoint(janus, bla);
        
    }
    
}

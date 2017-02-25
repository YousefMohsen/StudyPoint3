/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Student;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Yousinho - exam-preparation_JPA2.pdf
 */
public class Facade {
    
    EntityManagerFactory emf;
    EntityManager em;

    public Facade() {
        Persistence.generateSchema("pu", null);
        emf = Persistence.createEntityManagerFactory("pu");
        em = emf.createEntityManager();
    }
    
    public void addStudent(Student student){
    try{
    em.getTransaction().begin();
    em.persist(student);
    em.getTransaction().commit();
    }finally{
    em.close();
    }
    
    }
    
    public void deleteStudent(Student student){
        try{
    em.getTransaction().begin();
    em.remove(student);
    em.getTransaction().commit();
    }finally{
    em.close();
    }
    
        
        
    }
    
    public Student findStudent(int id){
    Query q = em.createQuery("SELECT s FROM Student s WHERE s.id = :aID");
    
           

   Student result = (Student) q.setParameter("aID", id).getSingleResult();
    return result;
    
    }
    
    
    public static void main(String[] args) {
        Facade fa = new Facade();
       //*add student 
      //  Student student =  new Student(7, new Date(), "Leo", "Messi", new Date(1987,6,24),29, true);
       //fa.addStudent(student);
       
       //*find student
       //  Student student =  fa.findStudent(1);
       
        //delete student
         // fa.deleteStudent(student);
    
      
     
        
    }
    
}

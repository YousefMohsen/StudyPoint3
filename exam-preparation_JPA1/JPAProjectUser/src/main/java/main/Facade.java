/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entity.Project;
import entity.ProjectUser;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Yousinho exam-preparation_JPA1.pdf
 */
public class Facade {

    EntityManagerFactory emf;
    EntityManager em;

    public Facade() {
        Persistence.generateSchema("pu", null);
        emf = Persistence.createEntityManagerFactory("pu");
        em = emf.createEntityManager();
    }

    public void createUser(ProjectUser pu) {

        try {
            em.getTransaction().begin();
            em.persist(pu);
            em.getTransaction().commit();
            System.out.println("User " + pu.getUserName() + "added to database");
        } finally {
            em.close();
        }

    }
    
      public List<ProjectUser> finAllUsers() {
          
            Query q = em.createQuery("SELECT u FROM ProjectUser u");
        
      List<ProjectUser> projectUsers = q.getResultList();
     
              
  return projectUsers;
      }
      
      public ProjectUser findUserByID(int id){
        Query q = em.createQuery("SELECT u FROM ProjectUser u WHERE u.id = :aId");

        q.setParameter("aId", id);
      ProjectUser result = (ProjectUser) q.getSingleResult();
      
          System.out.println("result: "+result.getUserName());
      return result;
      }
      
      public void createProject(Project project){
           try {
            em.getTransaction().begin();
            em.persist(project);
            em.getTransaction().commit();
            System.out.println("Project " + project.getName() + "added to database");
        } finally {
            em.close();
        }
      }
      
      
      public Project findProject(int id){
          
       Query q = em.createQuery("SELECT p FROM Project p WHERE p.id = :aId");

        q.setParameter("aId", id);
         Project result  = (Project) q.getSingleResult();
          System.out.println("project found "+result.getName());
         return result; 
      
      }
      
      
      public void assignUserToProject(ProjectUser prUser,Project project){
      
              try {
            em.getTransaction().begin();
          project.assignMeToUser(prUser);
            em.getTransaction().commit();
            System.out.println("Project " + project.getName() + "added to database");
        } finally {
            em.close();
        }
      }
    

    public static void main(String[] args) {
       // task 1
        Facade fa = new Facade();
        
        //task 2)
        //* create User
        //fa.createUser(new ProjectUser("Messi87","leoKing@live.dk"));
        
          //* find user
      // fa.findUserByID(2);
      
        //*finAllUsers
       // fa.finAllUsers();
      
   
      //*create project
     // fa.createProject(new Project("CA1","This is a descriptive description of this project project "));
      
     
     //*assign user to project
   //  ProjectUser user = fa.findUserByID(2);
     //Project project = fa.findProject(1);
     //fa.assignUserToProject(user,project);

     //*find project
    //fa.findProject(1);
    
    
     
    }
}

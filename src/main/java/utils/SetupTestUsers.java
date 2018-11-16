package utils;

import entity.Role;
import entity.User;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SetupTestUsers {

    //fills up database with user information
    
  public static void main(String[] args) {

    EntityManager em = Persistence.createEntityManagerFactory("persistence").createEntityManager();
    //opens the transaction
    em.getTransaction().begin();
   //creating user and role data for the database 
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    User user = new User("kurt", "test");
    User admin = new User("admin", "test"); 
    //provides the user with the correct role
    user.addRole(userRole);
    admin.addRole(adminRole);
    //persist prepares data to the database
    em.persist(userRole); 
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    //commit seals the transaction
    em.getTransaction().commit();
    
    //debugging purpose
    System.out.println("PW: " + user.GetPassword());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test"));
    System.out.println("Created TEST Users");
   
  }

}

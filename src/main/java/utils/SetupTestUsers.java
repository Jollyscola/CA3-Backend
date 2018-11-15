package utils;

import entity.Role;
import entity.User;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManager em = Persistence.createEntityManagerFactory("persistence").createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    
        
    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    User user = new User("user", "test");
    user.addRole(userRole);
    User admin = new User("admin", "test");
    admin.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.getTransaction().commit();
    System.out.println("PW: " + user.GetPassword());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));


      // User user = em.find(User.class,"user"); 
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test"));
    System.out.println("Created TEST Users");
   
  }

}

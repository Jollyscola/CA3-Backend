package entity;

import entity.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T16:51:12")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T14:22:24")
>>>>>>> 1c15f024ac3cc349f405b6e1f38c42a8e23dd8a4
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> userName;
    public static volatile ListAttribute<User, Role> roleList;
    public static volatile SingularAttribute<User, String> Password;

}
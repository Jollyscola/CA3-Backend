package entity;

import entity.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-16T09:23:00")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> userName;
    public static volatile ListAttribute<User, Role> roleList;
    public static volatile SingularAttribute<User, String> Password;

}
package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
    //entity  manager created.
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("userPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}

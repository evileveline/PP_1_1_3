package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDaoJDBCImpl udi = new UserDaoJDBCImpl();

        udi.createUsersTable();

        udi.saveUser("Ivan", "Petrov", (byte) 20);
        udi.saveUser("Alexey", "Stepanov", (byte) 25);
        udi.saveUser("Oleg", "Popov", (byte) 31);
        udi.saveUser("Artem", "Subbotin", (byte) 38);

        udi.removeUserById(1);
        udi.getAllUsers();
        udi.cleanUsersTable();
        udi.dropUsersTable();
    }
}

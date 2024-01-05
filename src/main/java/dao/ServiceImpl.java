package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ServiceImpl implements Service{

    static Connection conn = null ;

    static {
        String url = "jdbc:mysql://localhost:3306/bakerydb";
        String user = "root" ;
        String password = "tiger";

        try {
            conn = DriverManager.getConnection(url , user , password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

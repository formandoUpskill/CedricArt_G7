package persistence;


import java.sql.*;


public class MyUtils {

    public  enum DBType {DB_MYSQL, DB_SQLSERVER, DB_SQLITE};

    public static Boolean exist(Connection conn, String table) {
        String SQL = "select count(*) from "+table+";";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery(SQL);
            rs.next();
            return rs.getInt(1)>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static ResultSet exec_sql_select(Connection conn, String  sql) {

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs =  stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection get_connection(DBType db_type, String server, String port,String  db_name,
                                            String user_name, String password)
    {
        String connString = get_connection_string(db_type, server, port, db_name, user_name, password);

        try {
            Connection connection = DriverManager.getConnection(connString);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String get_connection_string(DBType db_type, String server, String port,String  db_name, String user_name,
                                                String password)
    {
        String str="";

        switch (db_type)
        {
            case DB_MYSQL:
                if (port==null || port.isEmpty())  // NÃ£o passei o porto
                    port = "3306";
                jdbc:mysql://localhost:3306/organizer?user=root&password=root
                str="jdbc:mysql://" + server +":" + port + "/" +db_name;
                if (user_name!=null && !user_name.isEmpty())
                    str += "?user=" + user_name;
                if (password!=null && !password.isEmpty())
                    str += "&password=" + password;
                break;

            case DB_SQLSERVER:
                break;

            case DB_SQLITE:
                str = "jdbc:sqlite:" + db_name;
        }
        return str;
    }

    public static int exec_sql (Connection conn, String sqlCmd) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(sqlCmd);
        }
        catch (SQLException e) {
            return -1;
        }

    }
}

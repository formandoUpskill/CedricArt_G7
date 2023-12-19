package persistence;


import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Constructor for MyDBUtils.
 * Initializes database configurations from a properties file.
 */
public class MyDBUtils {

    public static  String DB_SERVER;
    public static  String DB_PORT;
    public static  String DB_NAME;

    public static String DB_USER;
    public static String DB_PWD;

    Properties config;

    /**
     * Constructor for MyDBUtils.
     * Initializes database configurations from a properties file.
     */
    public MyDBUtils()
    {
        try {
            this.config= new Properties();
            FileReader file = new FileReader("resources/config/CedricArt.config");
            config.load(file);
            DB_SERVER = this.config.getProperty("DB_SERVER");
            DB_PORT = this.config.getProperty("DB_PORT");
            DB_NAME  = this.config.getProperty("DB_NAME");
            DB_PWD = this.config.getProperty("DB_PWD");
            DB_USER = this.config.getProperty("DB_USER");

            file.close();
        } catch (IOException e) {
            System.out.println("Config file not found "+  e.getMessage());
        }

        System.out.println("DB_SERVER " + DB_SERVER);
    }


    /**
     * Enum for specifying database types.
     */
    public enum db_type {DB_MYSQL, DB_SQLSERVER, DB_SQLITE}


    /**
     * Constructs a connection string for the specified database type.
     *
     * @param type Database type.
     * @param server Database server address.
     * @param port Database server port.
     * @param db Database name.
     * @param user Database user.
     * @param pwd Database password.
     * @return Connection string for the database.
     */
    private static String get_connection_string (db_type type, String server, String port, String db, String user, String pwd){
        switch (type){
            case DB_MYSQL: return  "jdbc:mysql://"+ server +":"+ port+"/"+db;
        }
        return null;
    }

    /**
     * Establishes a connection to the database.
     *
     * @param type Database type.
     * @param server Database server address.
     * @param port Database server port.
     * @param db Database name.
     * @param user Database user.
     * @param pwd Database password.
     * @return A Connection object to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection get_connection(db_type type, String server, String port, String db, String user, String pwd) throws SQLException

    {
        String connString = get_connection_string(type,server,port,db,user,pwd);

        Connection conn = DriverManager.getConnection(connString,user,pwd);
        return conn;
    }

    /**
     * Executes a SQL command that does not return a result set.
     *
     * @param conn Connection object.
     * @param sqlCmd SQL command to execute.
     * @return Number of records affected by the command, or -1 in case of an error.
     * @throws SQLException If a database access error occurs.
     */
    public static int exec_sql (Connection conn,String sqlCmd) throws SQLException
    {

        Statement statement = conn.createStatement();
        int n_regs = statement.executeUpdate(sqlCmd);
        return n_regs;

    }

    /**
     * Executes a SQL query command.
     *
     * @param conn Connection object.
     * @param sqlCmd SQL query command to execute.
     * @return A ResultSet object containing the data produced by the query.
     * @throws SQLException If a database access error occurs.
     */
    public static ResultSet exec_query(Connection conn,String sqlCmd) throws SQLException
    {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sqlCmd);
        return rs;
    }

    /**
     * Crie os métodos públicos que devolvem a string contendo o comando SELECT corretamente construído a partir dos valores
     * enviados a cada função. Apenas o parâmetro fields tem de ter conteúdo não nulo e não vazio:
     * o
     * get_select_command(field)
     * o
     * get_select_command(fields, tables, where_cond)
     * o
     * get_select_command(fields, tables, where_cond, order_by)
     * o
     * get_select_command(fields, tables, where_cond, group_by, having, order_by)
     */


    public static String get_select_command(String field){
        return "SELECT " + field;
    }

    /**
     * get_select_command("id_pessoa, nome AS fullName", "pessoa", "sexo='m'", "apelido, nome")
     * @param fields
     * @param tables
     * @param where_cond
     * @return
     */
    public static String get_select_command(String fields, String tables, String where_cond)
    {
        return "SELECT "+ fields+ " FROM " + tables + " WHERE " + where_cond;

    }


    public static String get_select_command(String fields, String table)
    {
        return "SELECT "+ fields+ " FROM " + table ;

    }

    public static String get_select_command(String fields, String tables, String where_cond, String order_by)
    {
        return (get_select_command(fields,tables, where_cond) + " ORDER BY " + order_by);
    }

    public static String get_select_command(String fields, String tables, String where_cond,
                                            String group_by, String having, String order_by)
    {
        return (get_select_command(fields,tables, where_cond) + " GROUP BY "
                + group_by + " HAVING " + having + " ORDER BY " + order_by);
    }


    /**
     * Checks if a record exists in the database.
     *
     * @param conn Connection object.
     * @param table Table name to check.
     * @param where WHERE condition for checking existence.
     * @return true if the record exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */

    public static boolean exist(Connection conn, String table, String where) throws SQLException
    {

        String cmdSQL= get_select_command("count(*)", table, where);
        ResultSet rs = exec_query(conn,cmdSQL);

        return rs.next() && rs.getInt(1) != 0;

    }


    /**
     * Performs a lookup operation in the database.
     *
     * @param conn Connection object.
     * @param field Field name to retrieve.
     * @param table Table name to lookup.
     * @return ResultSet containing the result of the lookup.
     * @throws SQLException If a database access error occurs.
     */
    public static ResultSet lookup(Connection conn, String field, String table) throws SQLException
    {
        String cmdSQL = get_select_command(field, table);

        ResultSet rs = exec_query(conn, cmdSQL);

        return rs;
    }


    /**
     * Performs a lookup operation in the database with a default value.
     *
     * @param conn Connection object.
     * @param field Field name to retrieve.
     * @param table Table name to lookup.
     * @param where_cond WHERE condition for the lookup.
     * @param default_value Default value to return if no record is found.
     * @return The value of the field or the default value if no record is found.
     * @throws SQLException If a database access error occurs.
     */
    public static Object lookup(Connection conn, String field, String table, String where_cond,String default_value) throws SQLException
    {
        String cmdSQL = get_select_command(field, table, where_cond);

        ResultSet rs = exec_query(conn, cmdSQL);

        if (rs.next())
            return rs.getObject(1);

        return default_value;
    }

    /**
     *
     * @param conn
     * @param field
     * @param table
     * @param where_cond
     * @param group_by
     * @param having
     * @param default_value
     * @return
     * @throws SQLException
     */

    public static Object lookup(Connection conn, String field, String table,
                                String where_cond, String group_by, String having, String default_value) throws SQLException
    {
        String cmdSQL = get_select_command(field, table, where_cond,group_by,having, field);
        ResultSet rs = exec_query(conn, cmdSQL);

        if (rs.next())
            return rs.getObject(1);

        return default_value;
    }
}

package persistence;

/**
 * Hello world!
 *
 */
import domain.Artwork;
import domain.Client;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private ArrayList<Artwork> storage;

    public Storage() {
        getArtworksDB();
    }


    public ArrayList<Artwork> getAllArtworks() {
        getArtworksDB();
        return storage;
    }

    public Artwork getArtwork(int i) throws SQLException {
        return getArtworkDB(i);
    }

    public void getArtworksDB() {
        Connection conn = connectDataBase();
        storage = new ArrayList<>();

        Boolean teste = MyUtils.exist(conn,"Artwork");
        if (!teste) {
            //System.out.println("Empty Table");
            return;
        }

        ResultSet rs = MyUtils.exec_sql_select(conn,"SELECT * FROM Artwork");
        String id;
        String title;
        String category;
        String medium;

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                id = rs.getString(1);
                title = rs.getString(2);
                category = rs.getString(3);
                medium = rs.getString(4);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.print("id:" + id);
            System.out.print(" title:" + title);
            System.out.print(" category:" + category);
            System.out.println(" medium:" + medium);
            storage.add( new Artwork(id,title,category,medium));
        }
        try {
            rs.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Artwork getArtworkDB(int artworkId)  {
        Connection conn = connectDataBase();

        ResultSet rs = MyUtils.exec_sql_select(conn,"SELECT * FROM ARTWORK WHERE ARTWORKID = "+
                                               String.valueOf(artworkId));
        String id;
        String title;
        String category;
        String medium;

        try {
            if (!rs.next())
                return null;
            id = rs.getString(1);
            title = rs.getString(2);
         //  category = rs.getString(3);
          //  medium = rs.getString(4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            rs.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Artwork(id,title);
    }

    public static Connection connectDataBase() {
        Connection conn_mysql = MyUtils.get_connection(MyUtils.DBType.DB_MYSQL, "localhost",
                null, "artsydb",
                "root", "123");

        return conn_mysql;
    }


}



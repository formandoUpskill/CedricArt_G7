package persistence;

import domain.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBStorage {

    public DBStorage()
    {
        new MyDBUtils();
    }

    /**
     *
     * @param newGene
     */
    public void createGene(Gene newGene) {

        String sql = "insert into Gene (id_gene, name, description) values ('" +
                newGene.getId()+ "','" +
                newGene.getName() + "','" +
                newGene.getDescription() + "');";

        System.out.println("insert " + sql);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            MyDBUtils.exec_sql(connection,sql);
        } catch (SQLException e) {
            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }
    }

    /**
     *
     * @param newArtist
     */
    public void createArtist(Artist newArtist) {

        String sql = "insert into Artist (id_Artist, location, hometown, name, biography, slug, birthyear, deathyear, thumbnail, " +
                "url, nationality)" +
                " values ('"+ newArtist.getId() + "','" +
                newArtist.getLocation() + "','" +
                newArtist.getHometown() + "','" +
                newArtist.getName() + "','" +
                newArtist.getBiography() + "','" +
                newArtist.getSlug() + "','" +
                newArtist.getBirthyear() + "','" +
                newArtist.getDeathyear() + "','" +
                newArtist.getThumbnail() + "','" +
                newArtist.getUrl() + "','" +
                newArtist.getNationality() +
                "');";

        System.out.println("insert " + sql);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            MyDBUtils.exec_sql(connection,sql);
        } catch (SQLException e) {
            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }
    }


    /**
     *
     * @return
     */
    public List getAllArtworks()

    {

        List listArtwork = new ArrayList<>();
        Artwork artwork;

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            ResultSet rs= MyDBUtils.lookup(connection, "*", "Artwork");
            while (rs.next())
            {
                artwork= new Artwork();
                artwork.setId(rs.getString("id_Artwork"));
                artwork.setTitle(rs.getString("title"));
                artwork.setThumbnail(rs.getString("thumbnail"));

                listArtwork.add(artwork);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listArtwork;

    }

    /**
     *
     * @param artwork
     * @param artist
     */
    private void insertArtworkArtist(Artwork artwork, Artist artist)
    {

        if (artworkExists(artwork) && artistExists(artist))
        {
            String sqlInsert = "insert into Created_By (id_Artwork, id_Artist) values ('" +
                    artwork.getId() + "','" +
                    artist.getId() +
                    "');";

            System.out.println("insert into Created_By (id_Artwork, id_Artist)  " + sqlInsert);

            try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                    MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {
                MyDBUtils.exec_sql(connection, sqlInsert);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }

        }
    }

    /**
     *
     * @param artwork
     * @param exhibition
     */
    private void insertExhibitionArtwork(Artwork artwork, Exhibition exhibition)
    {

        if (artworkExists(artwork) && exibitionExists(exhibition))
        {
            String sqlInsert = "insert into Exhibition_Artwork (id_Artwork, id_Exhibition) values ('" +
                    artwork.getId() + "','" +
                    exhibition.getId() +
                    "');";

            System.out.println("insert into Exhibition_Artwork (id_Artwork, id_Exhibition) )  " + sqlInsert);

            try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                    MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {
                MyDBUtils.exec_sql(connection, sqlInsert);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }

        }
    }

    /**
     *
     * @param artist
     * @return
     */
    private boolean artistExists(Artist artist)
    {
        boolean exists= false;

        String where= "id_Artist='" + artist.getId()+"'";

        System.out.println("artistExists artistExists artistExists" + where );

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            exists= MyDBUtils.exist(connection,"Artist", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }

    /**
     *
     * @param newArtwork
     * @return
     */
    private boolean artworkExists(Artwork newArtwork)
    {
        boolean exists= false;

        String where= "id_Artwork='" + newArtwork.getId()+"'";

        System.out.println("artworkExists artworkExists artworkExists" + where );

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            exists= MyDBUtils.exist(connection,"Artwork", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }


    /**
     *
     * @param partner
     * @return
     */
    private boolean partnerExists(Partner partner)
    {
        boolean exists= false;

        String where= "id_Partner='" + partner.getId()+"'";


        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            exists= MyDBUtils.exist(connection,"partner", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }


    /**
     *
     * @param exhibition
     * @return
     */
    private boolean exibitionExists(Exhibition exhibition)
    {
        boolean exists= false;

        String where= "id_Exhibition='" + exhibition.getId()+"'";


        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            exists= MyDBUtils.exist(connection,"exhibition", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }

    /**
     *
     * @param newArtwork
     * @param geneList
     */
    private void insertArtworkGenes(Artwork newArtwork, List<Gene> geneList)
    {

        for (Gene gene: geneList) {
            String sqlInsert = "insert into Artwork_Gene (id_Artwork, id_Gene) values ('" +
                    newArtwork.getId() + "','" +
                    gene.getId () +
                    "');";

            System.out.println("insert into Artwork_Gene (id_Artwork, id_Gene)  " + sqlInsert);

            try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                    MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {
                MyDBUtils.exec_sql(connection, sqlInsert);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }
        }
    }

    /**
     *
     * @param artwork
     * @param geneList
     * @param artist
     */
    public void createArtwork(Artwork artwork, List<Gene> geneList, Artist artist) {


        String sqlInsert = "insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) values ('" +
                artwork.getId() + "','" +
                artwork.getTitle() + "','" +
                //   artwork.getCreated_at() + "','" +
                //  artwork.getUpdated_at() + "','" +
                //      artwork.getDate() + "','" +
                artwork.getThumbnail() + "','" +
                //     artwork.getUrl() +
                "');";

        System.out.println("insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) " + sqlInsert);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {
            MyDBUtils.exec_sql(connection, sqlInsert);
        } catch (SQLException e) {
            System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
        }

        insertArtworkGenes(artwork, geneList);

        insertArtworkArtist(artwork, artist);

    }

    /**
     *
     * @param partner
     * @param artwork
     */
    private void updateArtworkPartner(Partner partner, Artwork artwork){

        String update = "Update artwork set id_partner= '"+partner.getId() + "' WHERE id_artwork = '"+ artwork.getId() +"'";

        System.out.println("update " + update);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            MyDBUtils.exec_sql(connection,update);
        } catch (SQLException e) {
            System.out.println("exec_sql:" + update + " Error: " + e.getMessage());
        }
    }

    /**
     *
     * @param partner
     * @param artwork
     */
    public void createPartner(Partner partner, Artwork artwork) {

        if (partnerExists(partner)){

            updateArtworkPartner(partner,artwork);
        }
        else {

            String sqlInsert = "insert into Partner (id_Partner,region, name, website, id_Gallerist, id_Coordinator ) values ('" +
                    partner.getId() + "','" +
                    partner.getRegion() + "','" +
                    partner.getName() + "','" +
                    partner.getWebsite() + "','" +
                    partner.getId_gallerist() + "','" +
                    partner.getId_coordinator() +
                    "');";

            System.out.println("insert " + sqlInsert);

            try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                    MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {

                MyDBUtils.exec_sql(connection, sqlInsert);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }


            updateArtworkPartner(partner, artwork);
        }
    }


    /**
     *
     * @param exhibition
     * @param partner
     * @param artwork
     */
    public void createExhibition(Exhibition exhibition, Partner partner, Artwork artwork) {

        if (!exibitionExists(exhibition)) {

            String sql = "insert into Exhibition (id_Exhibition, end_at, start_at, thumbnail, description, name, id_Partner,Id_Exhibition_Status) values ('" +
                    exhibition.getId() + "','" +
                    exhibition.getEnd_at() + "','" +
                    exhibition.getStart_at() + "','" +
                    exhibition.getThumbnail() + "','" +
                    exhibition.getDescription() + "','" +
                    exhibition.getName() + "','" +
                    partner.getId() + "'," +
                    "(select Id_Exhibition_Status from Exhibition_Status where status='" + exhibition.getStatus() +"')" +
                    ");";

            System.out.println("nsert into Exhibition " + sql);

            try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                    MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {

                MyDBUtils.exec_sql(connection, sql);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
            }

            insertExhibitionArtwork(artwork,exhibition);
        }
    }


}

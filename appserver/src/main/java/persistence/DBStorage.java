package persistence;

import domain.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for handling database storage operations.
 * This class provides methods to interact with the database for various domain objects.
 */
public class DBStorage {

    /**
     * Constructor for DBStorage.
     * Initializes utility class MyDBUtils.
     */
    public DBStorage()
    {
        new MyDBUtils();
    }


    /**
     * Retrieves a database connection.
     *
     * @return A Connection object to the database.
     * @throws SQLException If a database access error occurs.
     */
    private Connection getConnection() throws SQLException {
        return MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);
    }



    /**
     * -----------------------------------------
     * ARTIST SECTION
     * ---------------------------------------
     */

    /**
     * Extracts an Artist object from a ResultSet.
     *
     * @param rs The ResultSet from which to extract the artist data.
     * @return An Artist object.
     * @throws SQLException If a database access error occurs.
     */
    private Artist extractArtistFromResultSet(ResultSet rs) throws SQLException {

       Artist artist = new Artist();

        artist.setId(rs.getString("id_Artist"));
        artist.setName(Objects.toString(rs.getString("name"), ""));
        artist.setLocation(Objects.toString(rs.getString("location"), ""));
        artist.setHometown(Objects.toString(rs.getString("hometown"), ""));
        artist.setBiography (Objects.toString(rs.getString("biography"), ""));
        artist.setSlug(Objects.toString(rs.getString("slug"), ""));
        artist.setBirthyear(Objects.toString(rs.getString("birthyear"), ""));
        artist.setDeathyear (Objects.toString(rs.getString("deathyear"), ""));
        artist.setThumbnail(Objects.toString(rs.getString("thumbnail"), ""));
        artist.setUrl(Objects.toString(rs.getString("url"), ""));
        artist.setNationality(Objects.toString(rs.getString("nationality"), ""));


        return artist;
    }

    /**
     * Retrieves all artists associated with a specific partner.
     *
     * @param partner_id The ID of the partner.
     * @return A list of Artist objects.
     */
    public List<Artist> getAllArtistsByPartner(String partner_id)
    {

        List<Artist> artistList = new ArrayList<>();

        Artist artist;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(

                    "distinct Artist.id_Artist, " +
                            "Artist.name, "+
                            "Artist.biography, " +
                            "Artist.birthyear, "+
                            "Artist.deathyear, " +
                            "Artist.hometown, " +
                            "Artist.location, "+
                            "Artist.nationality, " +
                            "Artist.slug, "+
                            "Artist.thumbnail, "+
                            "Artist.url",
                    " Artist, " +
                            " Artwork, "+
                             "Created_By",
                    " Artist.id_Artist = Created_By.id_Artist AND " +
                             " Artwork.id_Artwork = Created_By.id_Artwork AND "+
                            " Artwork.id_Partner= '" + partner_id + "'" );


           // System.out.println("getAllArtistsByPartner " + sqlCMD);

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                artistList.add(extractArtistFromResultSet(rs));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return artistList;

    }

    /**
     * Retrieves all artists from the database.
     *
     * @return A list of all Artist objects.
     */
    public List<Artist> getAllArtists()

    {

        List<Artist> artistList = new ArrayList<>();

        Artist artist;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    " Artist");

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                artistList.add(extractArtistFromResultSet(rs));

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artistList;

    }


    /**
     * Retrieves a specific artist by their ID.
     *
     * @param idArtist The ID of the artist to retrieve.
     * @return An Artist object.
     */
    public Artist getArtist(String idArtist)  {

        Artist artist = new Artist();

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*" ,
                    "  Artist ",
                    " id_Artist= '" + idArtist + "'" );



            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

          //  System.out.println(sqlCMD);

            while (rs.next())
            {
                artist=(extractArtistFromResultSet(rs));


            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artist;
    }



    /**
     * Creates a new artist in the database.
     *
     * @param newArtist The Artist object to create in the database.
     * @return The created Artist object.
     */
    public Artist createArtist(Artist newArtist) {

        Artist artist = new Artist();

        String sql = "insert into Artist (" +
                "id_Artist, " +
                "location, " +
                "hometown, " +
                "name," +
                " biography," +
                " slug," +
                " birthyear, " +
                "deathyear, " +
                "thumbnail, " +
                "url, " +
                "nationality)" +
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

      //  System.out.println("insert " + sql);

        try (Connection connection  = getConnection())
        {

            MyDBUtils.exec_sql(connection,sql);

            artist = getArtist(newArtist.getId());

        } catch (SQLException e) {
            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }

        return artist;
    }

/**
 * -----------------------------------------
 * GENE SECTION
 * ---------------------------------------
 */

    /**
     * Creates a new gene in the database.
     *
     * @param newGene The Gene object to create.
     * @return The created Gene object.
     */
    public Gene createGene(Gene newGene) {

        Gene gene= new Gene();

        String sql = "insert into Gene (id_gene, name, description) values ('" +
                newGene.getId()+ "','" +
                newGene.getName() + "','" +
                newGene.getDescription() + "');";

     //   System.out.println("insert " + sql);

        try (Connection connection  = getConnection())
        {

            MyDBUtils.exec_sql(connection,sql);

            gene= getGene(newGene.getId());

        } catch (SQLException e) {
            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }

        return gene;
    }

    /**
     * Retrieves a specific gene by its ID.
     *
     * @param id The ID of the gene.
     * @return A Gene object.
     */
    public Gene getGene(String id){

        Gene gene = new Gene();

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    " Gene" ,
                    " id_Gene='"  + id +"'"
            );


          //  System.out.println("getAllGenes " + sqlCMD );

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {
                gene= new Gene();
                gene.setId(rs.getString("id_Gene"));
                gene.setName(rs.getString("name"));
                gene.setDescription(rs.getString("description"));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return gene;

    }

    /**
     * Retrieves all genes from the database.
     *
     * @return A list of all Gene objects.
     */
    public List<Gene> getAllGenes(){

        List<Gene> listGenes = new ArrayList<>();
        Gene gene;

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "* " ,
                    " Gene",
                    "",
                    "name ASC");


        //    System.out.println("getAllGenes " + sqlCMD );

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {
                gene= new Gene();
                gene.setId(rs.getString("Gene"));
                gene.setName(rs.getString("name"));
                gene.setDescription(rs.getString("description"));

                listGenes.add(gene);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listGenes;

    }


    /**
     * Retrieves all genes associated with a specific artwork.
     *
     * @param artwork The Artwork object.
     * @return A list of Gene objects associated with the artwork.
     */
    public List<Gene> getAllGenes(Artwork artwork){

        List<Gene> listGenes = new ArrayList<>();
        Gene gene;

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Gene.id_Gene, " +
                            "Gene.name, " +
                            "Gene.description",
                    " Gene, " +
                            "Artwork_gene ",
                    "Gene.id_Gene = Artwork_gene.id_Gene AND " +
                            " Artwork_gene.id_Artwork= '"+ artwork.getId() + "'",
                    "Gene.name ASC");


          //  System.out.println("getAllGenes " + sqlCMD );

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {
                gene= new Gene();
                gene.setId(rs.getString("Gene.id_Gene"));
                gene.setName(rs.getString("Gene.name"));
                gene.setDescription(rs.getString("Gene.description"));

                listGenes.add(gene);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listGenes;

    }




    /**
     * -----------------------------------------
     * ARTWORK SECTION
     * ---------------------------------------
     */

    /**
     * Extracts an Artwork object from a ResultSet.
     *
     * @param rs The ResultSet from which to extract the artwork data.
     * @return An Artwork object.
     * @throws SQLException If a database access error occurs.
     */
    private Artwork extractArtworkFromResultSet(ResultSet rs) throws SQLException {

        Artwork artwork = new Artwork();

        artwork.setId(rs.getString("Artwork.id_Artwork"));
        artwork.setTitle(Objects.toString(rs.getString("Artwork.title"), ""));
        artwork.setThumbnail(Objects.toString(rs.getString("thumbnail"), ""));
        artwork.setDate(Objects.toString(rs.getString("Artwork.date"), ""));

        artwork.setCreated_at(rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC));
        artwork.setUpdated_at(rs.getTimestamp("updated_at").toInstant().atOffset(ZoneOffset.UTC));

        return artwork;
    }


    /**
     * Retrieves an Artwork along with its associated Partner and Genes using the artwork's ID.
     * @param artworkId The ID of the artwork.
     * @return An Artwork object with its partner and genes.
     */

    public Artwork getArtworkWithPartnerAndGenes(String artworkId)  {

        Artwork artwork = new Artwork();
        Partner partner = new Partner();

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Artwork.id_Artwork," +
                            " Artwork.title, " +
                            "Artwork.date," +
                            " Artwork.thumbnail," +
                            "Artwork.created_at, " +
                            "Artwork.updated_at, " +
                            "Partner.id_Partner," +
                            " Partner.name, " +
                            "Partner.region, " +
                            "Partner.website",
                    "  Artwork ,  " +
                            " Partner ",
                    " Artwork.id_Partner= Partner.id_Partner AND " +
                            " id_Artwork= '" + artworkId + "'" );


            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

           // System.out.println(sqlCMD);
            while (rs.next())
            {

                artwork= extractArtworkFromResultSet(rs);

                partner.setId(rs.getString("Partner.id_Partner"));
                partner.setName(rs.getString("Partner.name"));
                partner.setRegion(rs.getString("Partner.region"));
                partner.setWebsite(rs.getString("Partner.website"));

                artwork.setPartner(partner);

                artwork.setGeneList(getAllGenes(artwork));

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artwork;
    }


    /**
     * Retrieves a specific Artwork by its ID.
     * @param artworkId The ID of the artwork.
     * @return An Artwork object.
     */
    public Artwork getArtwork(String artworkId)  {

        Artwork artwork = new Artwork();


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    " * ",
                    "  Artwork " ,
                    " id_Artwork = '"+ artworkId +"'");



            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

         //   System.out.println(sqlCMD);

            while (rs.next())
            {

                artwork= extractArtworkFromResultSet(rs);


            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artwork;
    }


    /**
     * Retrieves all Artworks from the database.
     * @return A list of all Artwork objects.
     */
    public List<Artwork> getAllArtworks()
    {

        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    " Artwork ");


           // System.out.println("getAllArtworks " + sqlCMD);

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                listArtwork.add(extractArtworkFromResultSet(rs));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listArtwork;

    }


    /**
     * Retrieves all Artworks associated with a given Partner.
     * @param partner_id The ID of the partner.
     * @return A list of Artwork objects associated with the specified partner.
     */
    public List<Artwork> getAllArtworksByPartner(String partner_id)
    {
        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;
        Partner partner;

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Artwork.id_Artwork, " +
                            "Artwork.title, " +
                            "Artwork.date, " +
                            "Artwork.thumbnail, " +
                            "Artwork.created_at, " +
                            "Artwork.updated_at, " +
                            "Partner.id_Partner, " +
                            "Partner.name, " +
                            "Partner.region, " +
                            "Partner.website ",
                    " Artwork , " +
                            " Partner",
                    "Artwork.id_Partner= Partner.id_Partner AND " +
                            " Artwork.id_Partner = '"+  partner_id + "'" ,
                    "title ASC");


            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {
                artwork= extractArtworkFromResultSet(rs);

                partner = new Partner();
                partner.setId(rs.getString("Partner.id_Partner"));
                partner.setName(rs.getString("Partner.name"));
                partner.setRegion(rs.getString("Partner.region"));
                partner.setWebsite(rs.getString("Partner.website"));

                artwork.setPartner(partner);


                listArtwork.add(artwork);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listArtwork;
    }


    /**
     * Retrieves all Artworks created by a specific Artist.
     * @param artist_id The ID of the artist.
     * @return A list of Artwork objects created by the specified artist.
     */
    public List<Artwork> getAllArtworksByArtist(String artist_id)
    {
        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Artwork.id_Artwork, " +
                            "Artwork.title, " +
                            "Artwork.date, " +
                            "Artwork.thumbnail, " +
                            "Artwork.created_at, " +
                            "Artwork.updated_at " ,
                    " Artwork , " +
                            " Created_By  ",
                    "Artwork.id_Artwork= Created_By.id_Artwork AND " +
                            " Created_By.id_Artist = '"+  artist_id + "'" ,
                    "title ASC");


         //   System.out.println("getAllArtworksByArtist " + sqlCMD);

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                listArtwork.add( extractArtworkFromResultSet(rs));


            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listArtwork;
    }



    /**
     * Retrieves all Artworks associated with a specific Exhibition.
     * @param exhibition_id The ID of the exhibition.
     * @return A list of Artwork objects associated with the specified exhibition.
     */
    public List<Artwork> getAllArtworksByExhibition(String exhibition_id)
    {
        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Artwork.id_Artwork, " +
                            "Artwork.title, " +
                            "Artwork.date, " +
                            "Artwork.thumbnail, " +
                            "Artwork.created_at, " +
                            "Artwork.updated_at " ,
                    " Artwork , " +
                            " Exhibition_Artwork ",
                    "Artwork.id_Artwork= Exhibition_Artwork.id_Artwork AND " +
                            " Exhibition_Artwork.id_Exhibition = '"+  exhibition_id + "'" ,
                    "title ASC");


            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                listArtwork.add( extractArtworkFromResultSet(rs));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listArtwork;
    }




    /**
     * Creates a new Artwork in the database and establishes its relationships with Artists and Genes.
     * @param newArtwork The Artwork object to be created.
     * @return The created Artwork object.
     */
    public Artwork createArtwork(Artwork newArtwork) {

        Artwork artwork= newArtwork;

        // se a artwork não existir então criar
        if (!artworkExists(newArtwork)) {

            String sqlInsert = "insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) values (" +
                    "'" + newArtwork.getId() + "'," +
                    "'" + newArtwork.getTitle() + "'," +
                    "'" + newArtwork.getCreated_at().toString().replace("Z", "") + "'," +
                    "'" + newArtwork.getUpdated_at().toString().replace("Z", "") + "'," +
                    "'" + newArtwork.getDate() + "'," +
                    "'" + newArtwork.getThumbnail() + "'," +
                    "'" + newArtwork.getUrl() +
                    "');";



            try (Connection connection  = getConnection())
            {

                MyDBUtils.exec_sql(connection, sqlInsert);

                artwork = getArtwork(newArtwork.getId());


            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }

        }

        // inserir na tabela que representa a ligação entre artista e artworks
        insertArtworkArtist(newArtwork, newArtwork.getArtist());

        insertArtworkGenes(newArtwork, newArtwork.getGeneList());

        return artwork;
    }





    /**
     * -----------------------------------------
     * SHOWS SECTION
     * ---------------------------------------
     */

    /**
     * Extracts an Exhibition object from a SQL ResultSet.
     * @param rs The ResultSet from which to extract the data.
     * @return An Exhibition object populated with data from the ResultSet.
     * @throws SQLException If a SQL error occurs.
     */
    private Exhibition extractExhibitionFromResultSet(ResultSet rs) throws SQLException {

        Exhibition exhibition = new Exhibition();


        exhibition.setId(rs.getString("Exhibition.id_Exhibition"));


        exhibition.setName(Objects.toString(rs.getString("Exhibition.name"), ""));
        exhibition.setDescription(Objects.toString(rs.getString("Exhibition.description"), ""));
        exhibition.setThumbnail (Objects.toString(rs.getString("Exhibition.thumbnail"), ""));
        exhibition.setId_Partner (Objects.toString(rs.getString("Exhibition.id_Partner"), ""));
        exhibition.setStatus(Objects.toString(rs.getString("Exhibition_Status.Status"), ""));

        exhibition.setStart_at(rs.getTimestamp("Exhibition.start_at").toInstant().atOffset(ZoneOffset.UTC));
        exhibition.setEnd_at(rs.getTimestamp("Exhibition.end_at").toInstant().atOffset(ZoneOffset.UTC));


        return exhibition;
    }

    /**
     * Retrieves an Exhibition by its ID.
     * @param exhibitionId The ID of the exhibition to retrieve.
     * @return The Exhibition object corresponding to the specified ID.
     */
    public Exhibition getExhibition(String exhibitionId)  {


        Exhibition exhibition = new Exhibition();

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Exhibition.id_Exhibition, " +
                            "Exhibition.name, " +
                            "Exhibition.description, " +
                            "Exhibition.thumbnail," +
                            "Exhibition.start_at, " +
                            "Exhibition.end_at, " +
                            "Exhibition_Status.Status, " +
                            "Exhibition.id_Partner",
                    " Exhibition, " +
                            " Exhibition_Status",
                    "Exhibition.Id_Exhibition_Status = Exhibition_Status.Id_Exhibition_Status AND " +
                            " id_Exhibition= '" + exhibitionId + "'" );


            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

           // System.out.println(sqlCMD);

            while (rs.next())
            {

               exhibition= extractExhibitionFromResultSet(rs);

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exhibition;
    }

    /**
     * Retrieves all Exhibitions in the database.
     * @return A list of all Exhibition objects.
     */
    public List<Exhibition> getAllExhibitions()

    {

        List<Exhibition> listExhibition = new ArrayList<>();

        Exhibition exhibition;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Exhibition.id_Exhibition, " +
                            "Exhibition.name, " +
                            "Exhibition.description, " +
                            "Exhibition.thumbnail," +
                            "Exhibition.start_at, " +
                            "Exhibition.end_at, " +
                            "Exhibition_Status.Status, " +
                            "Exhibition.id_Partner",
                    " Exhibition, " +
                            " Exhibition_Status",
                    " Exhibition.Id_Exhibition_Status = Exhibition_Status.Id_Exhibition_Status");

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                listExhibition.add(extractExhibitionFromResultSet(rs));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listExhibition;

    }


    /**
     * Retrieves all Exhibitions associated with a specific Partner.
     * @param partner_id The ID of the partner whose exhibitions to retrieve.
     * @return A list of Exhibitions associated with the specified partner.
     */
    public List<Exhibition> getAllExhibitionsByPartner(String partner_id)

    {

        List<Exhibition> listExhibition = new ArrayList<>();

        Exhibition exhibition;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "Exhibition.id_Exhibition, " +
                            "Exhibition.name, " +
                            "Exhibition.description, " +
                            "Exhibition.thumbnail," +
                            "Exhibition.start_at, " +
                            "Exhibition.end_at, " +
                            "Exhibition_Status.Status, " +
                            "Exhibition.id_Partner",
                    " Exhibition, " +
                            " Exhibition_Status",
                    " Exhibition.Id_Exhibition_Status = Exhibition_Status.Id_Exhibition_Status AND " +
                            " Exhibition.id_Partner= '" + partner_id + "'" );

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                listExhibition.add(extractExhibitionFromResultSet(rs));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listExhibition;

    }




    /**
     * -----------------------------------------
     * PARTNERS SECTION
     * ---------------------------------------
     */


    /**
     * Retrieves a Partner from the database using their ID.
     * @param partnerId The ID of the partner to retrieve.
     * @return The Partner object corresponding to the specified ID.
     */
    public Partner getPartner(String partnerId)  {


        Partner partner = new Partner();

        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    "Partner",
                    " id_Partner= '" + partnerId + "'" );


            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

         //   System.out.println(sqlCMD);

            while (rs.next())
            {

                partner.setId(rs.getString("Partner.id_Partner"));
                partner.setName(rs.getString("Partner.name"));
                partner.setRegion(rs.getString("Partner.region"));
                partner.setWebsite(rs.getString("Partner.website"));

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return partner;
    }



    /**
     * Retrieves all Partners in the database.
     * @return A list of all Partner objects.
     */
    public List<Partner> getAllPartners()

    {

        List<Partner> listPartner = new ArrayList<>();

        Partner partner;


        try(Connection connection  = getConnection())
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    " Partner");

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                partner = new Partner();
                partner.setId(rs.getString("Partner.id_Partner"));
                partner.setName(rs.getString("Partner.name"));
                partner.setRegion(rs.getString("Partner.region"));
                partner.setWebsite(rs.getString("Partner.website"));

                listPartner.add(partner);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listPartner;

    }

    /**
     * Inserts a relationship between an artwork and an artist into the database.
     * @param artwork The Artwork object.
     * @param artist The Artist object.
     */
    private void insertArtworkArtist(Artwork artwork, Artist artist)
    {

        if (artworkExists(artwork) && artistExists(artist))
        {
            String sqlInsert = "insert into Created_By (id_Artwork, id_Artist) values ('" +
                    artwork.getId() + "','" +
                    artist.getId() +
                    "');";

          //  System.out.println("insert into Created_By (id_Artwork, id_Artist)  " + sqlInsert);

            try (Connection connection  = getConnection())
            {
                MyDBUtils.exec_sql(connection, sqlInsert);

            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }

        }
    }

    /**
     * Inserts a relationship between an artwork and an exhibition into the database.
     * @param artwork The Artwork object.
     * @param exhibition The Exhibition object.
     */
    private void insertExhibitionArtwork(Artwork artwork, Exhibition exhibition)
    {

        if (artworkExists(artwork) && exibitionExists(exhibition))
        {
            String sqlInsert = "insert into Exhibition_Artwork (id_Artwork, id_Exhibition) values ('" +
                    artwork.getId() + "','" +
                    exhibition.getId() +
                    "');";

        //    System.out.println("insert into Exhibition_Artwork (id_Artwork, id_Exhibition) )  " + sqlInsert);

            try (Connection connection  = getConnection())
            {
                MyDBUtils.exec_sql(connection, sqlInsert);

            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }

        }
    }

    /**
     * Checks if an artist exists in the database.
     * @param artist The Artist object to check.
     * @return True if the artist exists, false otherwise.
     */
    private boolean artistExists(Artist artist)
    {
        boolean exists= false;

        String where= "id_Artist='" + artist.getId()+"'";


        try(Connection connection  = getConnection())
        {

            exists= MyDBUtils.exist(connection,"Artist", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }

    /**
     * Checks if an artwork exists in the database.
     * @param newArtwork The Artwork object to check.
     * @return True if the artwork exists, false otherwise.
     */
    private boolean artworkExists(Artwork newArtwork)
    {
        boolean exists= false;

        String where= "id_Artwork='" + newArtwork.getId()+"'";

        try (Connection connection  = getConnection())
        {

            exists= MyDBUtils.exist(connection,"Artwork", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }


    /**
     * Checks if a partner exists in the database.
     * @param partner The Partner object to check.
     * @return True if the partner exists, false otherwise.
     */
    private boolean partnerExists(Partner partner)
    {
        boolean exists= false;

        String where= "id_Partner='" + partner.getId()+"'";


        try (Connection connection  = getConnection()){

            exists= MyDBUtils.exist(connection,"partner", where );

        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }


    /**
     * Checks if an exhibition exists in the database.
     * @param exhibition The Exhibition object to check.
     * @return True if the exhibition exists, false otherwise.
     */
    private boolean exibitionExists(Exhibition exhibition)
    {
        boolean exists= false;

        String where= "id_Exhibition='" + exhibition.getId()+"'";


        try (Connection connection  = getConnection())
        {

            exists= MyDBUtils.exist(connection,"Exhibition", where );


        } catch (SQLException e) {
            System.out.println("exec_sql:" + where+ " Error: " + e.getMessage());
        }

        return exists;
    }

    /**
     * Inserts a list of genes related to an artwork into the database.
     * @param newArtwork The Artwork object.
     * @param geneList The list of Gene objects.
     */
    private void insertArtworkGenes(Artwork newArtwork, List<Gene> geneList)
    {

        for (Gene gene: geneList) {
            String sqlInsert = "insert into Artwork_Gene (id_Artwork, id_Gene) values ('" +
                    newArtwork.getId() + "','" +
                    gene.getId () +
                    "');";

          //  System.out.println("insert into Artwork_Gene (id_Artwork, id_Gene)  " + sqlInsert);

            try (Connection connection  = getConnection())
            {
                MyDBUtils.exec_sql(connection, sqlInsert);
            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }
        }
    }


    /**
     * Updates the partner associated with an artwork in the database.
     * @param partner The Partner object.
     * @param artwork The Artwork object.
     */
    private void updateArtworkPartner(Partner partner, Artwork artwork){

        String update = "Update Artwork set id_partner= '"+partner.getId() + "' WHERE id_artwork = '"+ artwork.getId() +"'";

       // System.out.println("update " + update);

        try(Connection connection  = getConnection())
        {

            MyDBUtils.exec_sql(connection,update);


        } catch (SQLException e) {
            System.out.println("exec_sql:" + update + " Error: " + e.getMessage());
        }
    }

    /**
     * Creates a new Partner in the database.
     * @param newPartner The Partner object to create.
     * @return The created Partner object.
     */
    public Partner createPartner (Partner newPartner){

        Artwork artwork = newPartner.getArtwork();

        if (partnerExists(newPartner)){

            updateArtworkPartner(newPartner,artwork);
        }
        else {

            String sqlInsert = "insert into Partner (id_Partner,region, name, website, id_Gallerist, id_Coordinator ) values ('" +
                    newPartner.getId() + "','" +
                    newPartner.getRegion() + "','" +
                    newPartner.getName() + "','" +
                    newPartner.getWebsite() + "','" +
                    newPartner.getId_gallerist() + "','" +
                    newPartner.getId_coordinator() +
                    "');";


            try (Connection connection  = getConnection())
            {

                MyDBUtils.exec_sql(connection, sqlInsert);


            } catch (SQLException e) {
                System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
            }

            updateArtworkPartner(newPartner, artwork);
        }

        return getPartner(newPartner.getId());
    }



    /**
     * Creates a new Exhibition in the database.
     * @param newExhibition The Exhibition object to create.
     * @return The created Exhibition object.
     */
    public Exhibition createExhibition(Exhibition newExhibition) {


        Partner partner = newExhibition.getPartner();
        List<Artwork> artworks = newExhibition.getArtworks();

        if (!exibitionExists(newExhibition)) {

            String sql = "insert into Exhibition (" +
                    "id_Exhibition, " +
                    "end_at, " +
                    "start_at, " +
                    "thumbnail, " +
                    "description, " +
                    "name, " +
                    "id_Partner," +
                    "Id_Exhibition_Status)" +
                    " values ('" +
                    newExhibition.getId() + "','" +
                    newExhibition.getEnd_at().toString().replace("Z", "") + "','" +
                    newExhibition.getStart_at().toString().replace("Z", "") + "','" +
                    newExhibition.getThumbnail() + "','" +
                    newExhibition.getDescription() + "','" +
                    newExhibition.getName() + "','" +
                    partner.getId() + "'," +
                    "(select Id_Exhibition_Status from Exhibition_Status where status='" + newExhibition.getStatus() +"')" +
                    ");";



            try (Connection connection  = getConnection())
            {

                MyDBUtils.exec_sql(connection, sql);

            //    System.out.println("createExhibition " + sql);

            } catch (SQLException e) {
                System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
            }

        }
        // para cada artwork fazer a correspondência na tabela Exhibition_Artwork
        for (Artwork artwork: artworks) {

       //     System.out.println("createExhibition" + artwork);

            insertExhibitionArtwork(artwork, newExhibition);
        }

        return newExhibition;
    }

}

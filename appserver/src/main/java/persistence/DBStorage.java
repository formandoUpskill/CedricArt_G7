package persistence;

import domain.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class DBStorage {

    public DBStorage()
    {
        new MyDBUtils();
    }





    public List<Artist> getAllArtists()

    {

        List<Artist> artistList = new ArrayList<>();

        Artist artist;


        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    " Artist");

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {

                artist = new Artist();


                artist.setId(rs.getString("id_Artist"));
                artist.setName(rs.getString("name"));
                artist.setLocation(rs.getString("location"));
                artist.setHometown(rs.getString("hometown"));
                artist.setBiography(rs.getString("biography"));
                artist.setSlug(rs.getString("slug"));
                artist.setBirthyear(rs.getString("birthyear"));
                artist.setDeathyear(rs.getString("deathyear"));
                artist.setThumbnail(rs.getString("thumbnail"));
                artist.setUrl(rs.getString("url"));
                artist.setNationality(rs.getString("url"));


                artistList.add(artist);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artistList;

    }


    /**
     *
     * @param idArtist
     * @return
     */
    public Artist getArtist(String idArtist)  {

        Artist artist = new Artist();

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*" ,
                    "  Artist ",
                    " id_Artist= '" + idArtist + "'" );



            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            System.out.println(sqlCMD);
            while (rs.next())
            {

                artist.setId(rs.getString("id_Artist"));
                artist.setName(rs.getString("name"));
                artist.setLocation(rs.getString("location"));
                artist.setHometown(rs.getString("hometown"));
                artist.setBiography(rs.getString("biography"));
                artist.setSlug(rs.getString("slug"));
                artist.setBirthyear(rs.getString("birthyear"));
                artist.setDeathyear(rs.getString("deathyear"));
                artist.setThumbnail(rs.getString("thumbnail"));
                artist.setUrl(rs.getString("url"));
                artist.setNationality(rs.getString("nationality"));


            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artist;
    }



    /**
     *
     * @param newArtist
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

        System.out.println("insert " + sql);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            MyDBUtils.exec_sql(connection,sql);

            artist = getArtist(newArtist.getId());

        } catch (SQLException e) {
            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }

        return artist;
    }



    /**
     *
     * @param newGene
     * @return
     */
    public Gene createGene(Gene newGene) {

        Gene gene= new Gene();

        String sql = "insert into Gene (id_gene, name, description) values ('" +
                newGene.getId()+ "','" +
                newGene.getName() + "','" +
                newGene.getDescription() + "');";

        System.out.println("insert " + sql);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD);){

            MyDBUtils.exec_sql(connection,sql);

            gene= getGene(newGene.getId());

        } catch (SQLException e) {
            System.out.println("exec_sql:" + sql + " Error: " + e.getMessage());
        }

        return gene;
    }

    /**
     *
     * @param id
     * @return
     */
    public Gene getGene(String id){

        Gene gene = new Gene();

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    " Gene" ,
                    " id_Gene='"  + id +"'"
            );


            System.out.println("getAllGenes " + sqlCMD );

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
     *
     * @return
     */

    public List<Gene> getAllGenes(){

        List<Gene> listGenes = new ArrayList<>();
        Gene gene;

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "* " ,
                    " Gene",
                    "",
                    "name ASC");


            System.out.println("getAllGenes " + sqlCMD );

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
     *
     * @param artwork
     * @return
     */
    public List<Gene> getAllGenes(Artwork artwork){

        List<Gene> listGenes = new ArrayList<>();
        Gene gene;

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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


            System.out.println("getAllGenes " + sqlCMD );

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
     *
     * @param artworkId
     * @return
     */

    public Artwork getArtworkWithPartnerAndGenes(String artworkId)  {

        Artwork artwork = new Artwork();
        Partner partner = new Partner();

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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

            System.out.println(sqlCMD);
            while (rs.next())
            {

                artwork.setId(rs.getString("Artwork.id_Artwork"));
                artwork.setTitle(rs.getString("Artwork.title"));
                artwork.setThumbnail(rs.getString("Artwork.thumbnail"));
                artwork.setDate(rs.getString("Artwork.date"));

             //   artwork.setCreated_at(MyDBUtils.covertSqlDateToLocalDateTime(rs.getDate("created_at")));
              //  artwork.setUpdated_at(MyDBUtils.covertSqlDateToLocalDateTime(rs.getDate("updated_at")));

                artwork.setCreated_at(rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC));
                artwork.setUpdated_at(rs.getTimestamp("updated_at").toInstant().atOffset(ZoneOffset.UTC));

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



    public Artwork getArtwork(String artworkId)  {

        Artwork artwork = new Artwork();
        Partner partner = new Partner();

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    " * ",
                    "  Artwork " ,
                    " id_Artwork = '"+ artworkId +"'");



            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            System.out.println(sqlCMD);
            while (rs.next())
            {

                artwork.setId(rs.getString("Artwork.id_Artwork"));
                artwork.setTitle(rs.getString("Artwork.title"));
                artwork.setThumbnail(rs.getString("Artwork.thumbnail"));
                artwork.setDate(rs.getString("Artwork.date"));

                artwork.setCreated_at(rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC));
                artwork.setUpdated_at(rs.getTimestamp("updated_at").toInstant().atOffset(ZoneOffset.UTC));

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artwork;
    }







    /**
     *
     * @param artwork
     * @return
     */



    /**
     *
     * @return
     */
    public List<Artwork> getAllArtworks()

    {

        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;
        Partner partner;

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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
                    " Artwork.id_Partner= Partner.id_Partner",
                    "title ASC");

            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            while (rs.next())
            {
                artwork= new Artwork();
                artwork.setId(rs.getString("Artwork.id_Artwork"));
                artwork.setTitle(rs.getString("Artwork.title"));
                artwork.setThumbnail(rs.getString("Artwork.thumbnail"));
                artwork.setDate(rs.getString("Artwork.date"));
                artwork.setCreated_at(rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC));
                artwork.setUpdated_at(rs.getTimestamp("updated_at").toInstant().atOffset(ZoneOffset.UTC));

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
     *
     * @param partner_id
     * @return
     */
    public List<Artwork> getAllArtworksByPartner(String partner_id)
    {
        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;
        Partner partner;

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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
                artwork= new Artwork();
                artwork.setId(rs.getString("Artwork.id_Artwork"));
                artwork.setTitle(rs.getString("Artwork.title"));
                artwork.setThumbnail(rs.getString("Artwork.thumbnail"));
                artwork.setDate(rs.getString("Artwork.date"));

                artwork.setCreated_at(rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC));
                artwork.setUpdated_at(rs.getTimestamp("updated_at").toInstant().atOffset(ZoneOffset.UTC));


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
     *
     * @param exhibition_id
     * @return
     */
    public List<Artwork> getAllArtworksByExhibition(String exhibition_id)
    {
        List<Artwork> listArtwork = new ArrayList<>();
        Artwork artwork;


        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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
                artwork= new Artwork();
                artwork.setId(rs.getString("Artwork.id_Artwork"));
                artwork.setTitle(rs.getString("Artwork.title"));
                artwork.setThumbnail(rs.getString("Artwork.thumbnail"));
                artwork.setDate(rs.getString("Artwork.date"));

                artwork.setCreated_at(rs.getTimestamp("created_at").toInstant().atOffset(ZoneOffset.UTC));
                artwork.setUpdated_at(rs.getTimestamp("updated_at").toInstant().atOffset(ZoneOffset.UTC));


                listArtwork.add(artwork);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listArtwork;
    }



    /**
     *
     * @param exhibitionId
     * @return
     */
    public Exhibition getExhibition(String exhibitionId)  {


        Exhibition exhibition = new Exhibition();

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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

            System.out.println(sqlCMD);
            while (rs.next())
            {

                exhibition.setId(rs.getString("Exhibition.id_Exhibition"));
                exhibition.setName(rs.getString("Exhibition.name"));
                exhibition.setDescription(rs.getString("Exhibition.description"));
                exhibition.setThumbnail(rs.getString("Exhibition.thumbnail"));


                exhibition.setStart_at(rs.getTimestamp("Exhibition.start_at").toInstant().atOffset(ZoneOffset.UTC));
                exhibition.setEnd_at(rs.getTimestamp("Exhibition.end_at").toInstant().atOffset(ZoneOffset.UTC));


                exhibition.setId_Partner(rs.getString("Exhibition.id_Partner"));
                exhibition.setStatus(rs.getString("Exhibition_Status.Status"));


            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exhibition;
    }

    /**
     *
     * @return
     */
    public List<Exhibition> getAllExhibitions()

    {

        List<Exhibition> listExhibition = new ArrayList<>();

        Exhibition exhibition;


        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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

                exhibition = new Exhibition();
                exhibition.setId(rs.getString("Exhibition.id_Exhibition"));
                exhibition.setName(rs.getString("Exhibition.name"));
                exhibition.setDescription(rs.getString("Exhibition.description"));
                exhibition.setThumbnail(rs.getString("Exhibition.thumbnail"));

                exhibition.setStart_at(rs.getTimestamp("Exhibition.start_at").toInstant().atOffset(ZoneOffset.UTC));
                exhibition.setEnd_at(rs.getTimestamp("Exhibition.end_at").toInstant().atOffset(ZoneOffset.UTC));


                exhibition.setId_Partner(rs.getString("Exhibition.id_Partner"));
                exhibition.setStatus(rs.getString("Exhibition_Status.Status"));

                listExhibition.add(exhibition);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listExhibition;

    }


    /**
     *
     * @param partner_id
     * @return
     */
    public List<Exhibition> getAllExhibitionsByPartner(String partner_id)

    {

        List<Exhibition> listExhibition = new ArrayList<>();

        Exhibition exhibition;


        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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

                exhibition = new Exhibition();
                exhibition.setId(rs.getString("Exhibition.id_Exhibition"));
                exhibition.setName(rs.getString("Exhibition.name"));
                exhibition.setDescription(rs.getString("Exhibition.description"));
                exhibition.setThumbnail(rs.getString("Exhibition.thumbnail"));


                exhibition.setStart_at(rs.getTimestamp("Exhibition.start_at").toInstant().atOffset(ZoneOffset.UTC));
                exhibition.setEnd_at(rs.getTimestamp("Exhibition.end_at").toInstant().atOffset(ZoneOffset.UTC));


                exhibition.setId_Partner(rs.getString("Exhibition.id_Partner"));
                exhibition.setStatus(rs.getString("Exhibition_Status.Status"));

                listExhibition.add(exhibition);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return listExhibition;

    }
    /**
     *
     * @param partnerId
     * @return
     */
    public Partner getPartner(String partnerId)  {


        Partner partner = new Partner();

        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
        {

            String sqlCMD= MyDBUtils.get_select_command(
                    "*",
                    "Partner",
                    " id_Partner= '" + partnerId + "'" );


            ResultSet rs= MyDBUtils.exec_query(connection,sqlCMD);

            System.out.println(sqlCMD);
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
     *
     * @return
     */
    public List<Partner> getAllPartners()

    {

        List<Partner> listPartner = new ArrayList<>();

        Partner partner;


        try( Connection connection  = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER,MyDBUtils.DB_PORT,MyDBUtils.DB_NAME,MyDBUtils.DB_USER,MyDBUtils.DB_PWD))
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

            exists= MyDBUtils.exist(connection,"Exhibition", where );

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
     * @param newArtwork
     * @param geneList
     * @param artist
     */
    public void createArtwork(Artwork newArtwork, List<Gene> geneList, Artist artist) {


        String sqlInsert = "insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) values (" +
                "'" + newArtwork.getId() + "'," +
                "'" + newArtwork.getTitle() + "'," +
                newArtwork.getCreated_at() + "," +
                newArtwork.getUpdated_at() + "," +
                "'"+   newArtwork.getDate() + "'," +
                "'"+   newArtwork.getThumbnail()+ "'," +
                "'"+   newArtwork.getUrl() +
                "');";

        System.out.println("insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) " + sqlInsert);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);)
        {
            MyDBUtils.exec_sql(connection, sqlInsert);


        } catch (SQLException e) {
            System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
        }

        insertArtworkGenes(newArtwork, geneList);

        insertArtworkArtist(newArtwork, artist);

    }




    public Artwork createArtwork(Artwork newArtwork) {

        Artwork artwork= new Artwork();


        String sqlInsert = "insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) values (" +
                "'" + newArtwork.getId() + "'," +
                "'" + newArtwork.getTitle() + "'," +
                "'" + newArtwork.getCreated_at().toString().replace("Z", "") + "'," +
                "'" + newArtwork.getUpdated_at().toString().replace("Z", "") + "'," +
                "'"+   newArtwork.getDate() + "'," +
                "'"+   newArtwork.getThumbnail()+ "'," +
                "'"+   newArtwork.getUrl() +
                "');";



        System.out.println("insert into Artwork (id_Artwork, title, created_at, updated_at, date, thumbnail, url) " + sqlInsert);

        try (Connection connection = MyDBUtils.get_connection(MyDBUtils.db_type.DB_MYSQL,
                MyDBUtils.DB_SERVER, MyDBUtils.DB_PORT, MyDBUtils.DB_NAME, MyDBUtils.DB_USER, MyDBUtils.DB_PWD);) {

            MyDBUtils.exec_sql(connection, sqlInsert);

            artwork = getArtwork(newArtwork.getId());


        } catch (SQLException e) {
            System.out.println("exec_sql:" + sqlInsert + " Error: " + e.getMessage());
        }


        return artwork;
    }

    /**
     *
     * @param partner
     * @param artwork
     */
    private void updateArtworkPartner(Partner partner, Artwork artwork){

        String update = "Update Artwork set id_partner= '"+partner.getId() + "' WHERE id_artwork = '"+ artwork.getId() +"'";

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
                    exhibition.getId() + "','" +
                    exhibition.getEnd_at() + "','" +
                    exhibition.getStart_at() + "','" +
                    exhibition.getThumbnail() + "','" +
                    exhibition.getDescription() + "','" +
                    exhibition.getName() + "','" +
                    partner.getId() + "'," +
                    "(select Id_Exhibition_Status from Exhibition_Status where status='" + exhibition.getStatus() +"')" +
                    ");";



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

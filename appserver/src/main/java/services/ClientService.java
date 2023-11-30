package services;

import domain.Client;

import java.util.List;

/**
 * Specifies the CRUD operations for a Client
 */
public interface ClientService {
    /**
     * Retrives all clients from storage.
     * @return a list (possibly empty) containing all clients
     */
    List<Client> getAllClients();

    /**
     * Retrives a client with the specified id from storage.
     * @param id the client's id
     * @return the client; <i>null</i> if not found
     */
    Client getClient(int id);

    /**
     * Creates a client and persists its information in storage.
     * @return an instance of the created client
     */
    Client createClient(String name, int age, String email, String telephoneNumber, String street, String city, String code, boolean isPremium);

    /**
     * Updates the information of the specified client and persists its information in storage.
     * @return an instance of the updated client
     */
    Client updateClient(int id, String name, int age, String email, String telephoneNumber, String street, String city, String code, boolean isPremium);

    /**
     *
     * @param id
     * @return
     */
    Client deleteClient(int id);
}

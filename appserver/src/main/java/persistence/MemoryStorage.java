package persistence;

import domain.Address;
import domain.Client;
import domain.Purchase;
import services.ClientService;
import services.PurchaseService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a DAO implementation for *Services using in-memory data.
 * Other implementations can implement the services over, e.g., a DBMS.
 */
public class MemoryStorage implements ClientService, PurchaseService {

    private List<Client> storage;
    private static int lastClientId = -1;
    private static int lastPurchaseId = -1;
    public MemoryStorage() {
        storage = new ArrayList<>();

        /* Populate storage */

        Client client = new Client(1, "Bruno Silva",
                42,
                "bruno.silva@estsetubal.ips.pt",
                "961234567",
                new Address("Rua de Cima", "Setúbal", "2900-999"),
                true
        );

        client.addPurchase(new Purchase(1, null, "PS5", 1, 400));
        client.addPurchase(new Purchase(2, LocalDate.now(), "PowerBank", 2, 30));
        client.addPurchase(new Purchase(3, LocalDate.now(), "Headphones", 1, 125.6));

        storage.add(client);

        // Keep track of generated id's
        lastClientId = 1;
        lastPurchaseId = 3;
    }

    @Override
    public List<Client> getAllClients() {
        return storage;
    }

    @Override
    public Client getClient(int id) {
        for (Client c : storage) {
            if(c.getId() == id) return c;
        }
        return null;
    }

    @Override
    public Client createClient(String name, int age, String email, String telephoneNumber, String street, String city, String code, boolean isPremium) {
        Client client = new Client(++lastClientId, name, age, email, telephoneNumber, new Address(street, city, code), isPremium);
        storage.add(client);
        return client;
    }

    @Override
    public Client updateClient(int id, String name, int age, String email, String telephoneNumber, String street, String city, String code, boolean isPremium) {
        Client existing = getClient(id); //this is a reference to an existing object to be updated
        if(existing == null) return null;

        existing.setName(name);
        existing.setAge(age);
        existing.setEmail(email);
        existing.setAddress(new Address(street, city, code));
        existing.setPremium(isPremium);

        return existing;
    }

    @Override
    public Client deleteClient(int id) {
        Client existing = getClient(id); //this is a reference to an existing object to be removed
        if(existing == null) return null;

        System.out.println(storage.size());
        storage.remove(existing);
        System.out.println(storage.size());

        return existing;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        List<Purchase> all = new ArrayList<>();
        for (Client client : storage) {
            all.addAll( client.getPurchaseHistory() );
        }
        return all;
    }

    @Override
    public List<Purchase> getAllPurchasesByClientId(int clientId) {
        Client existing = getClient(clientId);
        if(existing == null) return null;

        return existing.getPurchaseHistory();
    }

    @Override
    public Purchase getPurchase(int purchaseId) {
        List<Purchase> allPurchases = getAllPurchases();
        for (Purchase p : allPurchases) {
            if(p.getId() == purchaseId) return p;
        }
        return null;
    }

    @Override
    public Purchase createPurchase(int clientId, LocalDate date, String itemName, int quantity, double unitPrice) {
        Client existing = getClient(clientId); //this is a reference to an existing object to be updated
        if(existing == null) return null;

        Purchase p = new Purchase(++lastPurchaseId, date, itemName, quantity, unitPrice);
        existing.addPurchase(p);

        return p;
    }


    @Override
    public Purchase updatePurchase(int purchaseId, LocalDate date, String itemName, int quantity, double unitPrice) {
        Purchase existing = getPurchase(purchaseId);  //this is a reference to an existing object to be updated
        if(existing == null) return null;

        existing.setDate(date);
        existing.setItemName(itemName);
        existing.setQuantity(quantity);
        existing.setUnitPrice(unitPrice);

        return existing;
    }

    @Override
    public Purchase deletePurchase(int purchaseId) {
        Purchase existing = getPurchase(purchaseId);  //this is a reference to an existing object to be updated
        if(existing == null) return null;

        //must find the client with this purchase to remove it from its list
        for (Client client : storage) {
            if(client.getPurchaseHistory().contains(existing)) {
                client.getPurchaseHistory().remove(existing);
            }
        }

        return existing;
    }
}

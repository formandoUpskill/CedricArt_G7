package services;

import domain.Purchase;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseService {
    /**
     *
     * @return
     */
    List<Purchase> getAllPurchases();

    /**
     *
     * @param clientId
     * @return
     */
    List<Purchase> getAllPurchasesByClientId(int clientId);

    /**
     *
     * @param purchaseId
     * @return
     */
    Purchase getPurchase(int purchaseId);

    /**
     *
     * @param date
     * @param itemName
     * @param quantity
     * @param unitPrice
     * @return
     */
    Purchase createPurchase(int clientId, LocalDate date, String itemName, int quantity, double unitPrice);

    /**
     *
     * @param purchaseId
     * @param date
     * @param itemName
     * @param quantity
     * @param unitPrice
     * @return
     */
    Purchase updatePurchase(int purchaseId, LocalDate date, String itemName, int quantity, double unitPrice);

    Purchase deletePurchase(int purchaseId);
}

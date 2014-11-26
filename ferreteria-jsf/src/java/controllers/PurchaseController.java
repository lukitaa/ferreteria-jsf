/*
 * Copyright (C) 2014 Luca Giordano, Lucio Martínez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package controllers;

import dao.DetailsDaoImpl;
import dao.ProductsDaoImpl;
import dao.PurchasesDaoImpl;
import entity.Details;
import entity.Products;
import entity.Purchases;
import entity.Users;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import servlets.ShoppingCart;
import util.HibernateUtil;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class PurchaseController extends IntermediateController {

    /**
     * Generate a new purchase and bind it with the details
     *
     * @param purchaseDetails The Details list with a null purchase
     * @param userId The user that is buying
     * @return The Details list already stored binded with a purchase
     * @throws StorageException
     * @throws InvalidParameterException
     */
    public static List<Details> purchaseProducts(List<Details> purchaseDetails, Integer userId) throws StorageException, InvalidParameterException {

        // Check if there are any products to buy and store
        if (purchaseDetails.size() <= 0)
            throw new InvalidParameterException("La cantidad de productos a comprar es nula.");

        Integer productAmount, productId;

        // Get the user how made the purchase
        Users user = UsersController.getUser(userId);
        // Generate a new purchase
        Purchases purchase = new Purchases(false, user);

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Store the purchase to have an ID ;)
            new PurchasesDaoImpl(session).add(purchase);

            Products product = null;

            // Add each product into the purchase's details
            for(Details d : purchaseDetails){
                productId     = d.getProducts().getIdProduct();
                productAmount = d.getAmount();

                // Recover product data
                product = new ProductsDaoImpl(session).get(productId);

                // Check if amount to buy is available,
                // otherwise cancel the procedure
                if (productAmount <= 0 || productAmount > product.getStock())
                    throw new HibernateException("Stock not available.");//TODO: throw an invalid parameter exception

                // Bind the detail to the product
                d.setPurchases(purchase);

                // Update product stock
                product.setStock(product.getStock() - productAmount);
            }

            // Now the details list is complete and gotta be stored
            new DetailsDaoImpl(session).add(purchaseDetails);

            session.getTransaction().commit();

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar guardar la compra.");
        }

        return purchaseDetails;
    }

    /**
     * Add every product on the cart with a null purchase
     *
     * @param cart The products to buy
     * @return The Details list
     * @throws InvalidParameterException
     * @throws StorageException
     */
    public static List<Details> getDetailsFromCart(ShoppingCart cart)
            throws InvalidParameterException, StorageException {

        // Check if there are any products to buy and store
        if (cart.getProductsId().size() <= 0)
            throw new InvalidParameterException("La cantidad de productos a comprar es nula.");

        List<Details> details = new ArrayList();
        Integer productAmount, productId;

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Products product = null;

            // Add each product into the purchase's details
            for(int i = 0, length = cart.getProductsId().size(); i < length; i++) {
                productId     = cart.getProductsId().get(i);
                productAmount = cart.getProductsAmount().get(i);

                // Recover product data
                product = new ProductsDaoImpl(session).get(productId);

                /// Add an item to the cart when it is over stock
                /// have to allow user to note it and correct it
                // Check if amount to buy is available,
                // otherwise cancel the procedure
                //if (productAmount <= 0 || productAmount > product.getStock())
                    //throw new InvalidParameterException("El stock para el producto no se encuentra disponible.", product);//TODO: throw an invalid parameter exception

                // Add the detail to the list
                // NOTE: it is not associated with a purchase
                details.add(new Details(null, product, productAmount, product.getPrice()));
            }

        } catch(HibernateException e) {
            if (session != null) {
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar productos.");
        }

        return details;
    }

    /**
     * Get pending orders
     *
     * @param session A Hibernate session already opened
     * @return A list of purchases if no errors, otherwise an empty list
     * @throws StorageException
     */
    public static List<Purchases> getPendingOrders(Session session) throws StorageException {
        List<Purchases> orders = null;

        try {
            orders = new PurchasesDaoImpl(session).getPending();

        } catch(HibernateException e) {
            if (session != null) {
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar las ordenes pendientes.");
        }

        return orders;
    }


    /**
     * Get not pending orders
     *
     * @param session A Hibernate session already opened
     * @return A list of purchases if no errors, otherwise an empty list
     * @throws StorageException
     */
    public static List<Purchases> getNotPendingOrders(Session session) throws StorageException {
        List<Purchases> orders = null;

        try {
            orders = new PurchasesDaoImpl(session).getNotPending();

        } catch(HibernateException e) {
            if (session != null) {
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar las ordenes de piqueo.");
        }

        return orders;
    }



    /**
     * Update a purchase
     *
     * @param purchase purchase previously generated and stored
     * @throws StorageException if hibernate has troubles
     * @throws InvalidParameterException if you enter something nasty
     */
    public static void udpatePurchase(Purchases purchase)
            throws StorageException, InvalidParameterException {

        if (purchase == null)
            throw new InvalidParameterException("La orden ingresada es invalida.");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            new PurchasesDaoImpl(session).update(purchase);

            session.getTransaction().commit();

            session.close();

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar actualizar la orden.");
        }
    }


    public static Purchases getPurchase(Session session, int purchaseId) throws StorageException {

        try {
            Purchases p = new PurchasesDaoImpl(session).get(purchaseId);

            return p;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar la compra.");
        }
    }

    public static Purchases getPurchase(int purchaseId) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Purchases p = new PurchasesDaoImpl(session).get(purchaseId);

            session.close();

            return p;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar la compra.");
        }
    }

}

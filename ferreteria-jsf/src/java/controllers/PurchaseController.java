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
import util.HibernateUtil;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class PurchaseController extends IntermediateController {

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

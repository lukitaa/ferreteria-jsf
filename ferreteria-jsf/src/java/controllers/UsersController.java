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

import dao.UsersDaoImpl;
import entity.Purchases;
import entity.Users;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateUtil;

/**
 *
 * @author alumno
 */
public class UsersController extends IntermediateController {

    public static Users addUser(String username, String password, boolean admin) throws InvalidParameterException, StorageException {

        if (!validUsername(username))
            throw new InvalidParameterException("El nombre de usuario ingresado es invalido.");

        if (!validPassword(password))
            throw new InvalidParameterException("La contraseña ingresada es invalida.");

        // Encrypt the password before send it to the DAO
        password = BCrypt.hashpw(password, BCrypt.gensalt(12));

        Users u = new Users(username, password, admin);

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            new UsersDaoImpl(session).add(u);

            session.getTransaction().commit();
            session.close();

            return u;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar guardar el usuario.");
        }

    }


    public static void deleteUser(Users u) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            new UsersDaoImpl(session).delete(u);

            session.getTransaction().commit();
            session.close();

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar eliminar el usuario.");
        }
    }


    public static Users getUser(int userId) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Users u = new UsersDaoImpl(session).get(userId);

            session.getTransaction().commit();
            session.close();

            return u;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar el usuario.");
        }
    }


    /**
     * Get purchases of the user
     *
     * @param userId id of the user to get purchases
     * @param session hibernate session already opened
     * @return Set of purchases
     * @throws StorageException
     */
    public static Set<Purchases> getUserPurchases(int userId, Session session)
            throws StorageException {

        Set<Purchases> purchases;

        try {
            session.beginTransaction();

            Users u = new UsersDaoImpl(session).get(userId);
            purchases = u.getPurchaseses();

            // WARNING!!!
            // The crap collector will remove purchases
            // if it is not used. So you better leave this line!
            //System.out.println(purchases.size());

            session.getTransaction().commit();


            return purchases;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar el usuario.");
        }
    }


    public static void updateAllUsersAttributes(Users user, String username, String password, boolean admin) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Update user's attributes
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
            user.setAdmin(admin);

            new UsersDaoImpl(session).update(user);

            session.getTransaction().commit();
            session.close();

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar actualizar el usuario.");
        }
    }


}
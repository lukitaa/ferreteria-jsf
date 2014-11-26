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

import dao.ProductsDaoImpl;
import dao.UsersDaoImpl;
import entity.Products;
import entity.Users;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public abstract class IntermediateController extends Controller {

    public static List<Users> getUsers() throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            List<Users> l = new UsersDaoImpl(session).fetchAll();

            session.getTransaction().commit();
            session.close();

            return l;

        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error al intentar cargar los usuarios.");
        }

    }


    public static List<Products> getProducts() throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            List<Products> l = new ProductsDaoImpl(session).fetchAll();

            session.getTransaction().commit();
            session.close();

            return l;

        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error al intentar cargar los productos.");
        }

    }

}

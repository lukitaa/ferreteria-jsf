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
import entity.Products;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author usuario
 */
public class ProductsController extends IntermediateController {
    
    public static Products addProduct(String producto, int precio, int stock) throws InvalidParameterException, StorageException {
        if (!validUsername(producto))
            throw new InvalidParameterException("El nombre del producto ingresado es invalido.");
        
        Products p = new Products(producto, precio, stock);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            new ProductsDaoImpl(session).add(p);
            session.getTransaction().commit();
            session.close();
            return p;
        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            throw new StorageException("Error interno al intentar guardar el producto.");
        }
    }
    
    public static Products getProduct(int productsId) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Products u = new ProductsDaoImpl(session).get(productsId);
            session.getTransaction().commit();
            session.close();
            return u;
        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            throw new StorageException("Error interno al intentar cargar el producto.");
        }
    }
    
    public static void deleteProduct(Products p) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            new ProductsDaoImpl(session).delete(p);
            session.getTransaction().commit();
            session.close();
        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            throw new StorageException("Error interno al intentar eliminar el producto.");
        }
    }
    
    public static void updateProduct(Products product, String producto, int precio, int stock) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            product.setProduct(producto);
            product.setPrice(precio);
            product.setStock(stock);
            new ProductsDaoImpl(session).update(product);
            session.getTransaction().commit();
            session.close();
        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            throw new StorageException("Error interno al intentar actualizar el producto.");
        }
    }
    
}

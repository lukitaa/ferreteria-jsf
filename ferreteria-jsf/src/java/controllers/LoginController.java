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
import entity.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateUtil;

/**
 *
 * @author alumno
 */
public class LoginController extends Controller {
    
    /**
     * Check if the username and password are correct
     * @param username The username of the user
     * @param password The password in plaintext!
     * @return the user if found, null if not.
     * @throws InvalidParameterException If the username do not exist or it doesn't match with the password
     * @throws StorageException If there is an internal error while trying to fetch info from database
     */
    public static Users login(String username, String password) throws InvalidParameterException, StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            Users u = new UsersDaoImpl(session).getElementByName(username);

            session.getTransaction().commit();
            session.close();

            if (u == null)
                throw new InvalidParameterException("El usuario ingresado no existe.");

            // Check that an unencrypted password matches one that has previously been hashed
            if (!BCrypt.checkpw(password, u.getPassword()))
                throw new InvalidParameterException("El usuario ingresado y la contraseña no coinciden.");

            return u;
        } catch(HibernateException e) {
            throw new StorageException("Error interno al intentar leer el usuario.");
        }
    }
    
}

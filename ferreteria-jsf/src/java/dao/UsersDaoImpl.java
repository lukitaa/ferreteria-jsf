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

package dao;

import entity.Users;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author alumno
 */
public class UsersDaoImpl extends GenericDaoImpl<Users, Integer> implements UsersDao {

    public UsersDaoImpl(Session session) {
        super(session);
    }

    @Override
    public List<Users> fetchAll() {
        String hql = "FROM Users";
        return session.createQuery(hql).list();
    }

    @Override
    public Users getElementByName(String name) {
        Users i;
        String hql = "FROM Users WHERE username = :name";

        List l = session.createQuery(hql).setParameter("name", name).list();
        i = (l.size() > 0) ? (Users)l.get(0) : null;

        return i;
    }

}

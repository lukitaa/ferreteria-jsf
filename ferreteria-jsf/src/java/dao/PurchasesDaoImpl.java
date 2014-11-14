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

import entity.Purchases;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author alumno
 */
public class PurchasesDaoImpl extends GenericDaoImpl<Purchases, Integer> implements PurchasesDao {

    public PurchasesDaoImpl(Session session) {
        super(session);
    }

    @Override
    public List<Purchases> fetchAll() {
        String hql = "FROM Purchases";
        return session.createQuery(hql).list();
    }

    @Override
    public List<Purchases> getPending() {
        String hql = "FROM Purchases p WHERE p.done = 0";
        return session.createQuery(hql).list();
    }

    @Override
    public List<Purchases> getNotPending() {
        String hql = "FROM Purchases p WHERE p.done = 1";
        return session.createQuery(hql).list();
    }

}

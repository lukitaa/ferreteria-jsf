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

import entity.Details;
import java.util.List;
import org.hibernate.Session;


/**
 *
 * @author alumno
 */
public class DetailsDaoImpl extends GenericDaoImpl<Details, Integer> implements DetailsDao {

    public DetailsDaoImpl(Session session) {
        super(session);
    }

    @Override
    public List<Details> fetchAll() {
        String hql = "FROM Details";
        return session.createQuery(hql).list();
    }

    public void add(List<Details> details) {
        for (Details d : details)
            session.save(d);
    }
    
}

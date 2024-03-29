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

import entity.Products;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Lucio Martinez <luciomartinez at openmailbox dot org>
 */
public class ProductsDaoImpl extends GenericDaoImpl<Products, Integer> implements ProductsDao {

    public ProductsDaoImpl(Session session) {
        super(session);
    }

    @Override
    public List<Products> fetchAll() {
        String hql = "FROM Products";
        return session.createQuery(hql).list();
    }

}

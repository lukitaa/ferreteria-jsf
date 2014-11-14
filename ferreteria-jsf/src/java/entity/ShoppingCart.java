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

package entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumno
 */
public class ShoppingCart {

    private List<Integer> productsAmount;
    private List<Integer> productsId;

    public ShoppingCart() {
        productsAmount = new ArrayList();
        productsId = new ArrayList();
    }



    public List<Integer> getProductsAmount() {
        return productsAmount;
    }
    public void setProductsAmount(List<Integer> productsAmount) {
        this.productsAmount = productsAmount;
    }
    public List<Integer> getProductsId() {
        return productsId;
    }
    public void setProductsId(List<Integer> idProd) {
        this.productsId = idProd;
    }


    public int getTotalProducts() {
        return (productsAmount != null) ? productsAmount.size() : 0;
    }

}

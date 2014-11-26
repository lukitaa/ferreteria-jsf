/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import controllers.PurchaseController;
import controllers.StorageException;
import entity.Products;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author alumno
 */
public class Productos {
    
    private Integer idProduct;
     private String product;
     private int price;
     private int stock;
     private int unidades;
     private Set detailses = new HashSet(0);
     
     private List<Products> listaProductos;

    /**
     * Creates a new instance of Products
     */
    public Productos() {
    }
    
    public Productos(String product, int price, int stock) {
        this.product = product;
        this.price = price;
        this.stock = stock;
    }
    public Productos(String product, int price, int stock, Set detailses) {
       this.product = product;
       this.price = price;
       this.stock = stock;
       this.detailses = detailses;
    }
   
    public Integer getIdProduct() {
        return this.idProduct;
    }
    
    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
    public String getProduct() {
        return this.product;
    }
    
    public void setProduct(String product) {
        this.product = product;
    }
    public int getPrice() {
        return this.price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    public int getStock() {
        return this.stock;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Set getDetailses() {
        return this.detailses;
    }
    
    public void setDetailses(Set detailses) {
        this.detailses = detailses;
    }

    /**
     * @return the listaProductos
     */
    public List<Products> getListaProductos() throws StorageException {
        this.listaProductos = PurchaseController.getProducts();
        return listaProductos;
    }

    /**
     * @param listaProductos the listaProductos to set
     */
    public void setListaProductos(List<Products> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
    
    public void agregarCarrito() {
        System.out.println(this.unidades);
    }

    /**
     * @return the unidades
     */
    public int getUnidades() {
        return unidades;
    }

    /**
     * @param unidades the unidades to set
     */
    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
}

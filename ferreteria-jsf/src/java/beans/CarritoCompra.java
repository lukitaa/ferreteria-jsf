/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.ProductsController;
import controllers.StorageException;
import entity.Products;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usuario
 */
public class CarritoCompra {
    
    private List<Integer> productsAmount;
    private List<Integer> productsId;
    private int total;
    private int cantidad;

    private List<Products> productosComprar;
    
    public CarritoCompra() {
        productsAmount = new ArrayList();
        productsId = new ArrayList();
        productosComprar = new ArrayList();
        total = 0;
    }

    
    public void updateCart(Products product) throws StorageException {

        boolean exists = false;
        //Go trought the products' list searching if
        // the product was already in the cart to
        // increment the amount of unities to buy
        for(int i = 0, length = this.getProductsId().size(), aux; i < length; i++) {
            //If it's in, add the new amount and the older one
            if(this.getProductsId().get(i).equals(product.getIdProduct())) {
                aux = this.getProductsAmount().get(i) + product.getUnidades();
                this.getProductsAmount().set(i, aux);
                exists = true;
                break;
            }
        }
        if(!exists) {
            this.getProductsId().add(product.getIdProduct());
            this.getProductsAmount().add(product.getUnidades());
        }
        
        getProductosComprar();
    }
    
    public void removeProduct(Products prod) {

        for(int i = 0, length = this.productosComprar.size(); i < length; i++) {
        if (prod.getIdProduct() == this.productosComprar.get(i).getIdProduct()) {
            this.productosComprar.remove(i);
            
            for(int x = 0; x < productsId.size(); x++){
                if(productsId.get(x) == prod.getIdProduct()){
                    productsId.remove(x);
                    productsAmount.remove(x);
                }
            }
            break;
        }
}
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

    /**
     * @return the total
     */
    public int getTotal() {
        int aux = 0;
        for(int i = 0; i < productosComprar.size(); i++){
            aux += productosComprar.get(i).getPrice() * productosComprar.get(i).getUnidades();
        }
        setTotal(aux);
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    public void updateTotal(int toAdd){
        int aux = this.getTotal() + toAdd;
        this.setTotal(aux);
    }

    /**
     * @return the productosComprar
     */
    public List<Products> getProductosComprar() throws StorageException {
        for(int i = 0; i < productsId.size(); i++){
            Products p = ProductsController.getProduct(productsId.get(i));
            
            //obtener la posicion del producto con id de P
            //para obtener la cantidad a comprar de este segun la posicion en la lista.
            int index = 0;
            for(int x = 0; x < productsId.size(); x++){
                if(productsId.get(x) == p.getIdProduct()){
                    index = x;
                    break;
                }
            }
            p.setUnidades(productsAmount.get(index));
        
            boolean exists = false;
            for(int j = 0; j < productosComprar.size(); j++){
                if(p.getIdProduct() == productosComprar.get(i).getIdProduct())
                    exists = true;
            }
            
            if(!exists)
                productosComprar.add(p);
        }
        return productosComprar;
    }

    /**
     * @param productosComprar the productosComprar to set
     */
    public void setProductosComprar(List<Products> productosComprar) {
        this.productosComprar = productosComprar;
    }
    
    public int getCantidad() {
        setCantidad(productosComprar.size());
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

package entity;
// Generated 13/11/2014 18:21:59 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Products generated by hbm2java
 */
public class Products  implements java.io.Serializable {


     private Integer idProduct;
     private String product;
     private int price;
     private int unidades;
     private int stock;
     private Set detailses = new HashSet(0);

     
     private int idCompra;
     public int getIdCompra() {
        return this.idCompra;
    }
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
     
     
    public Products() {
    }

	
    public Products(String product, int price, int stock) {
        this.product = product;
        this.price = price;
        this.stock = stock;
    }
    public Products(String product, int price, int stock, Set detailses) {
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



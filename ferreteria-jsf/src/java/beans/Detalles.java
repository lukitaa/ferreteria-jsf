/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.InvalidParameterException;
import controllers.StorageException;
import controllers.UsersController;
import dao.DetailsDaoImpl;
import dao.ProductsDaoImpl;
import dao.PurchasesDaoImpl;
import entity.Details;
import entity.Products;
import entity.Purchases;
import entity.Users;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author usuario
 */
public class Detalles extends Details {
    
    private List<Details> detalles;
    private int cantidad;
    private int total;
    private boolean success = false;
    private boolean sinCompras;
    private List<Products> productos;
    
    public Detalles(){
        detalles = new ArrayList();
        productos = new ArrayList();
        success = false;
    }
    
    public List<Details> generateDetails(CarritoCompra cart) throws InvalidParameterException, StorageException {
        detalles = new ArrayList();
        // Check if there are any products to buy and store
        if (cart.getCantidad() <= 0)
            throw new InvalidParameterException("La cantidad de productos a comprar es nula.");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            for(int i = 0; i < cart.getProductosComprar().size(); i++){
                Products p = cart.getProductosComprar().get(i);
                getDetalles().add(new Details(null, p, p.getUnidades(), p.getPrice()));
            }
        }catch(HibernateException e) {
            if (session != null) {
                session.close();
            }
                throw new StorageException("Error interno al intentar cargar productos.");
        }
        return getDetalles();
    }
    
    public String purchaseProducts(CarritoCompra cart, int userLoggedID) throws StorageException, InvalidParameterException {
        generateDetails(cart);

        // Check if there are any products to buy and store
        if (getDetalles().size() <= 0)
            throw new InvalidParameterException("La cantidad de productos a comprar es nula.");
        
        Integer productAmount, productId;
        // Get the user who made the purchase
        Users user = UsersController.getUser(userLoggedID);
        // Generate a new purchase
        Purchases purchase = new Purchases(user, false);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            // Store the purchase to have an ID ;)
            new PurchasesDaoImpl(session).add(purchase);
            Products product = null;
            // Add each product into the purchase's details
            for(Details d : getDetalles()){
                productId = d.getProducts().getIdProduct();
                productAmount = d.getAmount();
                // Recover product data
                product = new ProductsDaoImpl(session).get(productId);
                // Check if amount to buy is available,
                // otherwise cancel the procedure
                if (productAmount <= 0 || productAmount > product.getStock())
                    throw new HibernateException("Stock not available.");//TODO: throw an invalid parameter exception
                // Bind the detail to the product
                d.setPurchases(purchase);
                // Update product stock
                product.setStock(product.getStock() - productAmount);
            }
            // Now the details list is complete and gotta be stored
            new DetailsDaoImpl(session).add(getDetalles());
            session.getTransaction().commit();
            setSuccess(true);
        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            throw new StorageException("Error interno al intentar guardar la compra.");
        }
        return "detalles";
    }

    
    

    /**
     * @return the detalles
     */
    public List<Details> getAllDetails() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        detalles = new DetailsDaoImpl(session).fetchAll();
        session.close();
        return detalles;
    }

    public String getUserDetails(Users user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        detalles = new ArrayList();
        detalles = new DetailsDaoImpl(session).fetchAll();
        productos = new ArrayList();
        for(int i = 0; i < detalles.size(); i++){
            if(detalles.get(i).getPurchases().getUsers().getIdUser() == user.getIdUser()){
                Details d = detalles.get(i);
                Products p = new Products(d.getProducts().getProduct(),d.getPrice(),d.getProducts().getStock());
                p.setUnidades(d.getAmount());
                productos.add(p);
            }
        }
        
        setTotal();
        session.close();
        return "history-detail";
    }
    
    public List<Details> getDetalles(){
        return detalles;
    }
    
    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(List<Details> detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void desSuccess(){
        this.setSuccess(false);
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getCantidad(){
        return cantidad;
    }
    
    public void setTotal() {
        int aux = 0;
        for(Products p : productos)
            aux += p.getUnidades()* p.getPrice();
        this.total = aux;
    }
    
    public int getTotal(){
        return total;
    }

    /**
     * @return the productos
     */
    public List<Products> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<Products> productos) {
        this.productos = productos;
    }

    /**
     * @return the sinCompras
     */
    public boolean isSinCompras() {
        return productos.size() > 0;
    }

    /**
     * @param sinCompras the sinCompras to set
     */
    public void setSinCompras(boolean sinCompras) {
        this.sinCompras = sinCompras;
    }
}

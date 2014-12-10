/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.InvalidParameterException;
import controllers.PurchaseController;
import controllers.StorageException;
import dao.PurchasesDaoImpl;
import entity.Details;
import entity.Products;
import entity.Purchases;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;

/**
 *
 * @author usuario
 */
public class Ordenes {
    
    private List<Purchases> listaCompras;
    private List<Products> listaDetalles;
    private int cantidad;
    private boolean success = false;
    private List<Purchases> detalleCompra;
    private List<Details> detalleCompraDetalle;
    
    public Ordenes(){
        listaCompras = new ArrayList();
        cantidad = -1;
        success = false;
        listaDetalles = new ArrayList();
    }
    
    public String getNotPendingOrders() throws StorageException {
        listaCompras = new ArrayList();
        listaDetalles = new ArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            listaCompras = new PurchasesDaoImpl(session).getNotPending();
            
            for(Purchases p : listaCompras){
                Iterator ite = p.getDetailses().iterator();
                while(ite.hasNext()){
                    Details d = (Details) ite.next();
                    d.getProducts().setUnidades(d.getAmount());
                    
                    boolean exists = false;
                    for(Products prod : listaDetalles){
                        if(prod.getProduct().equals(d.getProducts().getProduct()))
                            exists = true;
                    }
                    
                    if(!exists){
                        Products prod = new Products(d.getProducts().getProduct(), d.getProducts().getPrice(), d.getProducts().getStock());
                        prod.setUnidades(d.getAmount());
                        listaDetalles.add(prod);
                    }
                    else{
                        for(int i = 0; i < listaDetalles.size(); i++){
                            if(listaDetalles.get(i).getProduct().equals(d.getProducts().getProduct()))
                                listaDetalles.get(i).setUnidades(listaDetalles.get(i).getUnidades() + d.getAmount());
                        }
                    }
                }
            }
            
            session.close();
        } catch(HibernateException e) {
            if (session != null) {
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar las ordenes de piqueo.");
        }

        return "ordenes-piqueo";
    }
    
    public String getPendingOrders() throws StorageException {
        listaCompras = new ArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            listaCompras = new PurchasesDaoImpl(session).getPending();
            
        } catch(HibernateException e) {
            if (session != null) {
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar las ordenes pendientes.");
        }

        return "ordenes-pendientes";
    }

    public String setDonePurchase(int purchaseID) throws StorageException, InvalidParameterException {
        Purchases p = getPurchase(purchaseID);
        p.setDone(true);
        this.setSuccess(true);
        updatePurchase(p);
        return "";
    }
    
    public Purchases getPurchase(int purchaseId) throws StorageException{
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Purchases p = new PurchasesDaoImpl(session).get(purchaseId);

            session.close();

            return p;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar la compra.");
        }
    }
    
    public void updatePurchase(Purchases purchase)
            throws StorageException, InvalidParameterException {

        if (purchase == null)
            throw new InvalidParameterException("La orden ingresada es invalida.");

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            new PurchasesDaoImpl(session).update(purchase);

            session.getTransaction().commit();

            session.close();

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar actualizar la orden.");
        }
    }
    
    public Purchases getPurchase(int purchaseId, Session session) throws StorageException{
        
        try {
            Purchases p = new PurchasesDaoImpl(session).get(purchaseId);

            return p;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar la compra.");
        }
    }
    
    
   
    public List<Purchases> getListaCompras() throws StorageException {
        getPendingOrders();
        return listaCompras;
    }

    public void setListaCompras(List<Purchases> listaCompras) {
        this.listaCompras = listaCompras;
    }

    public List<Products> getListaDetalles() throws StorageException {
        getNotPendingOrders();
        return listaDetalles;
    }

    public void setListaDetalles(List<Products> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }
    
    public int getCantidad() {
        return listaCompras.size();
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void desSuccess(){
        this.setSuccess(false);
    }

    public List<Purchases> getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(List<Purchases> detalleCompra) {
        this.detalleCompra = detalleCompra;
    }
    
    public List<Details> getDetalleCompraDetalle() {
        return detalleCompraDetalle;
    }

    public void setDetalleCompraDetalle(List<Details> detalleCompraDetalle) {
        this.detalleCompraDetalle = detalleCompraDetalle;
    }

}

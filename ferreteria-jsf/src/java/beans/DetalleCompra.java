/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.StorageException;
import dao.PurchasesDaoImpl;
import entity.Details;
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
public class DetalleCompra {
    
    private Purchases compra;
    private List<Details> detalles;
    private boolean exists;
    private int total;
    
    public DetalleCompra(){
        compra = null;
        detalles = new ArrayList();
    }

    public String obtenerDetalleCompra(int purchaseID) throws StorageException{
        detalles = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            compra = new PurchasesDaoImpl(session).get(purchaseID);
            
            Iterator it = compra.getDetailses().iterator();
            while(it.hasNext()){
                Details d = (Details) it.next();
                detalles.add(d);
            }
            
    

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar la compra.");
        }
        return "detalle-compra";
    }
    
    
    /**
     * @return the compra
     */
    public Purchases getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Purchases compra) {
        this.compra = compra;
    }

    /**
     * @return the detalle
     */
    public List<Details> getDetalle() {
        return detalles;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(List<Details> detalle) {
        this.detalles = detalle;
    }

    public boolean isExists() {
        return detalles.size() > 0;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        this.total = 0;
        for(Details d : detalles){
            total += ( d.getAmount() * d.getPrice() );
        }
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
    
}

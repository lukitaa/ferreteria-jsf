package entity;
// Generated 13/11/2014 18:21:59 by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Purchases generated by hbm2java
 */
public class Purchases  implements java.io.Serializable {


     private Integer idPurchase;
     private Users users;
     private boolean done;
     private Set detailses = new HashSet(0);

    public Purchases() {
    }

	
    public Purchases(Users users, boolean done) {
        this.users = users;
        this.done = done;
    }
    public Purchases(Users users, boolean done, Set detailses) {
       this.users = users;
       this.done = done;
       this.detailses = detailses;
    }
   
    public Integer getIdPurchase() {
        return this.idPurchase;
    }
    
    public void setIdPurchase(Integer idPurchase) {
        this.idPurchase = idPurchase;
    }
    public Users getUsers() {
        return this.users;
    }
    
    public void setUsers(Users users) {
        this.users = users;
    }
    public boolean isDone() {
        return this.done;
    }
    
    public void setDone(boolean done) {
        this.done = done;
    }
    public Set getDetailses() {
        return this.detailses;
    }
    
    public void setDetailses(Set detailses) {
        this.detailses = detailses;
    }




}


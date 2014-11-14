/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.StorageException;
import dao.UsersDaoImpl;
import entity.Users;
import java.security.InvalidParameterException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import util.HibernateUtil;
import org.mindrot.jbcrypt.BCrypt;

/**
 * ESTA ES LA CLASE QUE SERA UTILIZADA POR JSF COMO BEAN.
 * ES IGUAL A LA CLASE USERS DE LAS ENTIDADES HIBERNATE, 
 * CON LA EXCEPCION QUE CONTIENE UN BOOL PARA VER SI ESTA LOGEADO
 */
public class Usuario extends Users {
    private boolean logeado;
    
    public Usuario(){
        logeado = false;
    }
    

    
    public String attempLogin() throws StorageException{
        String isLogged = "loguearse", 
               username = this.getUsername(), 
               password = this.getPassword();
        
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Users u = new UsersDaoImpl(session).getElementByName(username);

            session.getTransaction().commit();
            session.close();

            if (u == null)
                throw new InvalidParameterException("El usuario ingresado no existe.");

            // Check that an unencrypted password matches one that has previously been hashed
            if (!BCrypt.checkpw(password, u.getPassword()))
                throw new InvalidParameterException("El usuario ingresado y la contraseña no coinciden.");
            
            logeado = true;
            isLogged = "logueado";

        } catch(HibernateException e) {
            throw new StorageException("Error interno al intentar leer el usuario.");
        }
        
        return isLogged;
    }
    
    
    
    /**
     * @return the logeado
     */
    public boolean isLogeado() {
        return logeado;
    }

    /**
     * @param logeado the logeado to set
     */
    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }
}

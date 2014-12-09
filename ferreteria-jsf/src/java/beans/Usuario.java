/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import dao.StorageException;
import dao.UsersDaoImpl;
import entity.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateUtil;

/**
 * ESTA ES LA CLASE QUE SERA UTILIZADA POR JSF COMO BEAN.
 * ES IGUAL A LA CLASE USERS DE LAS ENTIDADES HIBERNATE, 
 * CON LA EXCEPCION QUE CONTIENE UN BOOL PARA VER SI ESTA LOGEADO
 */
public class Usuario extends Users {
    private boolean logeado;
    private boolean admin;
    private boolean loginError = false;
    private int userID;
    
    public Usuario(){
        logeado = false;
        loginError = false;
        userID = -1;
    }
    

    public String cancelarSesion() {
        String isLogged = "loguearse";
        this.setLogeado(false);
        return isLogged;
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

            loginError = false;
            if (u == null){
                loginError = true;
                //throw new InvalidParameterException("El usuario ingresado no existe.");
            }
            else{
                // Check that an unencrypted password matches one that has previously been hashed
                if (!BCrypt.checkpw(password, u.getPassword())){
                    loginError = true;
                    //throw new InvalidParameterException("El usuario ingresado y la contraseña no coinciden.");
                }
            }
            
            if(!loginError){
                this.setAdmin(u.isAdmin());
                logeado = true;
                userID = u.getIdUser();
                isLogged = "logueado";
            }

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

    /**
     * @return the admin
     */
    @Override
    public boolean isAdmin() {
        return admin;
    }

    /**
     * @param admin the admin to set
     */
    @Override
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * @return the loginError
     */
    public boolean isLoginError() {
        return loginError;
    }

    /**
     * @param loginError the loginError to set
     */
    public void setLoginError(boolean loginError) {
        this.loginError = loginError;
    }

    /**
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    
    
}

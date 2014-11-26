package beans;

import controllers.UsersController;
import dao.StorageException;
import dao.UsersDaoImpl;
import entity.Users;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateUtil;

public class PaginaUsuarios {
    
    private List<SelectItem> items;
    private String username;
    private String password;
    private boolean isAdmin;
    private String amin;
    private List<Users> listaUsuarios;
    
    public PaginaUsuarios(){
        username = "";
        password = "";
        isAdmin = false;
        items = new ArrayList();
    }
    
    /**
     * @return the items
     */
    public List<SelectItem> getItems() {
        items = new ArrayList();
        items.add(new SelectItem("Es administrador"));
        items.add(new SelectItem("No es administrador"));
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<SelectItem> items) {
        this.items = items;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isAdmin
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the listaUsuarios
     */
    public List<Users> getListaUsuarios() throws StorageException {
        listaUsuarios = new ArrayList();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            listaUsuarios = new UsersDaoImpl(session).fetchAll();
            session.getTransaction().commit();
            session.close();
            return listaUsuarios;
            
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }
            throw new StorageException("Error al intentar cargar los usuarios.");
        }
    }
    
    public static void eliminarUsuario(int userId) throws StorageException, controllers.StorageException {
        Users u = UsersController.getUser(userId);
        UsersController.deleteUser(u);
    }

    public static Users getUser(int userId) throws StorageException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            Users u = new UsersDaoImpl(session).get(userId);

            session.getTransaction().commit();
            session.close();

            return u;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar cargar el usuario.");
        }
    }
    
    public static void actualizarUsuario(int userId, String username, String password, boolean admin) throws StorageException {
        Users user = getUser(userId);
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            // Update user's attributes
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
            user.setAdmin(admin);

            new UsersDaoImpl(session).update(user);

            session.getTransaction().commit();
            session.close();

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar actualizar el usuario.");
        }
    }
    
    public Users addUser() throws InvalidParameterException, StorageException, controllers.StorageException, controllers.InvalidParameterException {
        String username = this.getUsername();
        String pass = this.getPassword();
        boolean isAdmin = this.getAmin().equals("Es administrador");
        UsersController.addUser(username, password, isAdmin);
        /*
        String username = this.getUsername();
        String pass = this.getPassword();
        boolean isAdmin = this.isAdmin;
        
        
        
        if (!Controller.validUsername(username))
            throw new InvalidParameterException("El nombre de usuario ingresado es invalido.");

        if (!Controller.validPassword(password))
            throw new InvalidParameterException("La contraseña ingresada es invalida.");

        // Encrypt the password before send it to the DAO
        password = BCrypt.hashpw(password, BCrypt.gensalt(12));

        Users u = new Users(username, password, this.isAdmin);

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            new UsersDaoImpl(session).add(u);

            session.getTransaction().commit();
            session.close();

            return u;

        } catch(HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
                session.close();
            }

            throw new StorageException("Error interno al intentar guardar el usuario.");
        }
        */
        return null;
    }
    
    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<Users> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the amin
     */
    public String getAmin() {
        return amin;
    }

    /**
     * @param amin the amin to set
     */
    public void setAmin(String amin) {
        this.amin = amin;
    }
}

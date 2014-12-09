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
    private boolean success = false;
    
    public PaginaUsuarios() throws StorageException{
        username = "";
        password = "";
        isAdmin = false;
        items = new ArrayList();
        success = false;
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
    
    public static void eliminarUsuario(Users user) throws StorageException, controllers.StorageException {
        if(user != null){
            Users u = getUser(user.getIdUser());
            UsersController.deleteUser(u);
        }
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
    
    public void actualizarUsuario(Users user) throws StorageException {
        if(user != null){
            desedit();
            Users u = getUser(user.getIdUser());
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();

                // Update user's attributes
                u.setUsername(user.getUsername());
                String aux = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
                if(!aux.equals(user.getPassword()))
                    u.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
                //CHEQUEANDO ESTA PARTE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                u.setAdmin(user.getNormalAdmin().equals("Es administrador"));

                new UsersDaoImpl(session).update(u);

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
    }
    
    public Users addUser() throws InvalidParameterException, StorageException, controllers.StorageException, controllers.InvalidParameterException {
        String username = this.getUsername();
        String pass = this.getPassword();
        boolean isAdmin = this.getAmin().equals("Es administrador");
        UsersController.addUser(username, password, isAdmin);
        setSuccess(true);
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

   private boolean editmode;

    public void edit() {
        this.setEditmode(true);
    }

    public void desedit() {
        this.setEditmode(false);
    }
    
    /**
     * @return the editmode
     */
    public boolean isEditmode() {
        return editmode;
    }

    /**
     * @param editmode the editmode to set
     */
    public void setEditmode(boolean editmode) {
        this.editmode = editmode;
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
}

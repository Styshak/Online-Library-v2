package com.styshak.beans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sergey
 */
@Named(value = "user")
@ManagedBean
@SessionScoped
public class User implements Serializable {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {
            ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).login(login, password);
            return "books";
        } catch (ServletException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesMessage message = new FacesMessage(bundle.getString("incorrect_login_password"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("login_form", message);
        }
        return "index";
    }

    public String logout() {
        String result = "/index.xhtml?faces-redirect=true";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return result;
    }
}

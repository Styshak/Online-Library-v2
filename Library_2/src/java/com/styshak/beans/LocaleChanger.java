package com.styshak.beans;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "localeChanger")
@ManagedBean(eager = true)
@SessionScoped
public class LocaleChanger implements Serializable{

    private Locale currentLocale;

    public LocaleChanger() {
        this.currentLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    }

    public void changeLocale(String localeCode) {
        currentLocale = new Locale(localeCode);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}

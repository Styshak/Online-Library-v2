package com.styshak.validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.styshak.validators.LoginValidator")
public class LoginValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String login = value.toString().trim();
        ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        try {
            if (login.length() < 5) {
                throw new IllegalArgumentException(bundle.getString("login_length_error"));
            }

            if (!Character.isLetter(login.charAt(0))) {
                throw new IllegalArgumentException(bundle.getString("login_first_character_error"));
            }
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}

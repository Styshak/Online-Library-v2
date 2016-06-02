package com.styshak.controllers;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@ManagedBean
@ApplicationScoped
@Named(value = "lettersController")
public class LettersController implements Serializable {

    private Character lettersArray[] = new Character[33];

    public Character[] getLettersArray() {
        
        lettersArray[0] = 'А';
        lettersArray[1] = 'Б';
        lettersArray[2] = 'В';
        lettersArray[3] = 'Г';
        lettersArray[4] = 'Д';
        lettersArray[5] = 'Е';
        lettersArray[6] = 'Ё';
        lettersArray[7] = 'Ж';
        lettersArray[8] = 'З';
        lettersArray[9] = 'И';
        lettersArray[10] = 'Й';
        lettersArray[11] = 'К';
        lettersArray[12] = 'Л';
        lettersArray[13] = 'М';
        lettersArray[14] = 'Н';
        lettersArray[15] = 'О';
        lettersArray[16] = 'П';
        lettersArray[17] = 'Р';
        lettersArray[18] = 'С';
        lettersArray[19] = 'Т';
        lettersArray[20] = 'У';
        lettersArray[21] = 'Ф';
        lettersArray[22] = 'Х';
        lettersArray[23] = 'Ц';
        lettersArray[24] = 'Ч';
        lettersArray[25] = 'Ш';
        lettersArray[26] = 'Щ';
        lettersArray[27] = 'Ъ';
        lettersArray[28] = 'Ы';
        lettersArray[29] = 'Ь';
        lettersArray[30] = 'Э';
        lettersArray[31] = 'Ю';
        lettersArray[32] = 'Я';
        
        return lettersArray;
    }

}

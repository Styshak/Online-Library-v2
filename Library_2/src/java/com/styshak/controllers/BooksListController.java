package com.styshak.controllers;

import com.styshak.beans.Book;
import com.styshak.db.DataAccess;
import com.styshak.db.MySQLDataAccessImpl;
import com.styshak.enums.SearchType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@ManagedBean(eager = true)
@SessionScoped
@Named(value = "booksListController")
public class BooksListController implements Serializable {

    private int booksOnPage = 5; // кол-во книг на странице
    private int pageCount;// кол-во страниц
    private int selectedGenreId = -1; // выбранный жанр
    private char selectedLetter = ' '; // выбранная буква алфавита
    private int selectedPageNumber = 1; // выбранный номер страницы в постраничной навигации
    private int totalBooksCount = -1; // общее кол-во книг (не на текущей странице, а всего), нажно для постраничности
    private List<Integer> pageNumbers = new ArrayList<Integer>(); // номера страниц в постраничности
    private SearchType searchType; //хранит выбранный тип поиска
    private String searchString; // хранит строку запроса поиска
    private List<Book> currentBookList; // текущий список книг для отображения
    private DataAccess da = new MySQLDataAccessImpl();
    private String currentQuery;
    private boolean editMode;
    
    public BooksListController() {
        updateValues(' ', 1, -1, da.getAllBooks());
    }
    
    public boolean isEditMode() {
        return editMode;
    }

    public void switchEditMode() {
        editMode = true;
    }
    
    public int getBooksOnPage() {
        return booksOnPage;
    }

    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
    }

    public int getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(int selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public long getTotalBooksCount() {
        return totalBooksCount;
    }

    public void setTotalBooksCount(int totalBooksCount) {
        this.totalBooksCount = totalBooksCount;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public List<Book> getCurrentBookList() {
        return currentBookList;
    }
    
    public void searchStringChanged(ValueChangeEvent e) {
        searchString = e.getNewValue().toString();
    }
    
    public void searchTypeChanged(ValueChangeEvent e) {
        searchType = (SearchType) e.getNewValue();
    }

    public void updateBooks() {  
        da.updateBooks(currentBookList);
        cancelEdit();
    }
    
    public void cancelEdit() {     
        editMode = false;
        for (Book book : currentBookList) {
            book.setEdit(false);
        }
    }
    
    public void fillBooksListByGenreId() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedGenreId = Integer.valueOf(params.get("genre_id"));
        updateValues(' ', 1, selectedGenreId, da.getBooksListByGenreId(selectedGenreId));
    }

    public void fillBooksListByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedLetter = params.get("letter").charAt(0);
        updateValues(selectedLetter, 1, -1, da.getBooksListByLetter(selectedLetter));
    }

    public void fillBookBySearchString() {
        if (searchString.trim().length() == 0) {
            updateValues(' ', 1, -1, da.getAllBooks()); 
        } else {
            updateValues(' ', 1, -1, da.getBookListBySearchString(searchString, searchType));        
        }
    }

    public void selectPage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedPageNumber = Integer.valueOf(params.get("page_number"));
        currentBookList = da.getBooks(currentQuery, totalBooksCount, selectedPageNumber, booksOnPage);
    }

    private void updateValues(Character selectedLetter, int selectedPageNumber, int selectedGenreId, String currentQuery) {
        this.selectedLetter = selectedLetter;
        this.selectedPageNumber = selectedPageNumber;
        this.selectedGenreId = selectedGenreId;
        this.currentQuery = currentQuery;
        totalBooksCount = da.getBooksCount(currentQuery);
        currentBookList = da.getBooks(currentQuery, totalBooksCount, selectedPageNumber, booksOnPage);
        fillPageNumbers(totalBooksCount, booksOnPage);
    }

    private void fillPageNumbers(int totalBooksCount, int booksCountOnPage) {

        if (totalBooksCount % booksCountOnPage == 0) {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) : 0;
        } else {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) + 1 : 0;
        }
        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
    }
    
    public void booksOnPageChanged(ValueChangeEvent e) {
        cancelEdit(); 
        booksOnPage = Integer.valueOf(e.getNewValue().toString());
        selectedPageNumber = 1;
        currentBookList = da.getBooks(currentQuery, totalBooksCount, selectedPageNumber, booksOnPage); 
        fillPageNumbers(totalBooksCount, booksOnPage);
    }
}

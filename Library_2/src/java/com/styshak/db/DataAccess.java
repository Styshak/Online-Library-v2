/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.styshak.db;

//import com.styshak.beans.Author;
import com.styshak.beans.Book;
import com.styshak.beans.Genre;
import com.styshak.enums.SearchType;
import java.util.List;

/**
 *
 * @author Sergey
 */
public interface DataAccess {

    public List<Genre> getGenres();
    
    public byte[] getBookImage(int book_id);
    
    public List<Book> getBooks(String query, int totalBooksCount, int selectedPageNumber, int booksOnPage);

    public String getBooksListByGenreId(int id);
    
    public String getBooksListByLetter(Character letter);
    
    public String getBookListBySearchString(String searchString, SearchType searchType);
    
    public byte[] getBookContent(int id);
    
    public int getBooksCount(String query);
    
    public String getBookName(int book_id);
    
    public void addBook(String name);
    
    public void updateBooks(List<Book> booksList);
    
    public String getAllBooks();
}

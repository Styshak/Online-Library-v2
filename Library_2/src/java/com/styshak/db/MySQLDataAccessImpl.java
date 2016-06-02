package com.styshak.db;

import com.styshak.beans.Book;
import com.styshak.beans.Genre;
import com.styshak.enums.SearchType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLDataAccessImpl implements DataAccess, Serializable{

    @Override
    public List<Book> getBooks(String query, int totalBooksCount, int selectedPageNumber, int booksOnPage) {
        List<Book> bookList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(query);
        if (totalBooksCount > booksOnPage) {
            sqlBuilder.append(" limit ").append(selectedPageNumber * booksOnPage - booksOnPage).append(",").append(booksOnPage);
        }
        try (Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sqlBuilder.toString())) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("page_count"),
                        rs.getString("isbn"), rs.getString("genre"),
                        rs.getString("author"), rs.getInt("publish_year"),
                        rs.getString("publisher"),
                        rs.getString("description"));
                bookList.add(book);
            }
        } catch (Exception e) {
        }
        return bookList;
    }

    @Override
    public String getBooksListByGenreId(int id) {

        StringBuilder sql = new StringBuilder();

        String mainSql = "select b.id, b.name, b.content, b.page_count, b.isbn, "
                + "g.name as genre, a.fio as author, b.publish_year, p.name as publisher, "
                + "b.description "
                + "from book as b "
                + "inner join genre as g on g.id = b.genre_id "
                + "inner join author as a on a.id = b.author_id "
                + "inner join publisher as p on p.id = b.publisher_id ";
        sql.append(mainSql);
        if (id != 0) {
            sql.append("where b.genre_id=");
            sql.append(id);
        }
        sql.append(" order by b.name");
        return sql.toString();
    }

    @Override
    public List<Genre> getGenres() {

        List<Genre> genreList = new ArrayList<>();
        String sql = "select * from genre order by name";
        try (Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Genre genre = new Genre(rs.getInt(1), rs.getString(2));
                genreList.add(genre);
            }
        } catch (SQLException e) {
        }
        return genreList;
    }

    @Override
    public String getBooksListByLetter(Character letter) {

        String sql = "select b.id, b.name, b.content, b.page_count, b.isbn, "
                + "g.name as genre, a.fio as author, b.publish_year, p.name as publisher, "
                + "b.description "
                + "from book as b "
                + "inner join genre as g on g.id = b.genre_id "
                + "inner join author as a on a.id = b.author_id "
                + "inner join publisher as p on p.id = b.publisher_id "
                + "where b.name like " + "'" + letter + "%' "
                + "order by b.name";
        return sql;
    }

    @Override
    public String getBookListBySearchString(String searchString, SearchType searchType) {

        StringBuilder sql = new StringBuilder();
        String mainSql = "select b.id, b.name, b.content, b.page_count, b.isbn, "
                + "g.name as genre, a.fio as author, b.publish_year, p.name as publisher, "
                + "b.description "
                + "from book as b "
                + "inner join genre as g on g.id = b.genre_id "
                + "inner join author as a on a.id = b.author_id "
                + "inner join publisher as p on p.id = b.publisher_id ";
        sql.append(mainSql);
        if (searchType == SearchType.TITLE) {
            sql.append("where b.name like ");
            sql.append("'%");
            sql.append(searchString);
            sql.append("%' ");
        } else {
            sql.append("where a.fio like ");
            sql.append("'%");
            sql.append(searchString);
            sql.append("%' ");
        }
        sql.append("order by b.name");
        return sql.toString();
    }

    @Override
    public byte[] getBookContent(int id) {
        byte[] content = null;
        String sql = "select content from book where Id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                content = rs.getBytes("content");
            }
        } catch (Exception e) {
        }
        return content;
    }

    @Override
    public byte[] getBookImage(int book_id) {
        byte[] image = null;
        String sql = "select image from book where Id = ?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, book_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                image = rs.getBytes("image");
            }
        } catch (Exception e) {

        }
        return image;
    }

    @Override
    public int getBooksCount(String query) {

        int count = 0;
        try (Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                rs.last();
                count = rs.getRow();
            }
        } catch (Exception e) {
        }
        return count;
    }

    @Override
    public String getBookName(int book_id) {
        String bookName = "book";
        String sql = "select name from book where id=" + book_id;
        try (Connection con = DBConnection.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                bookName = rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return bookName;
    }

    @Override
    public void addBook(String name) {
        String sql = "insert into book (name)"
                + "values(?)";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateBooks(List<Book> booksList) {
        String sql = "update book set name=?, page_count=?, isbn=?, publish_year=?, description=? "
                + "where id=?";
        try (Connection con = DBConnection.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            for (Book book : booksList) {
                if (!book.isEdit()) {
                    continue;
                }
                pst.setString(1, book.getName());
                pst.setInt(2, book.getPageCount());
                pst.setString(3, book.getIsbn());
                pst.setInt(4, book.getPublishYear());
                pst.setString(5, book.getDescription());
                pst.setInt(6, book.getId());
                pst.addBatch();
            }
            pst.executeBatch();
        } catch (SQLException e) {
        }
    }

    @Override
    public String getAllBooks() {
        String mainSql = "select b.id, b.name, b.content, b.page_count, b.isbn, "
                + "g.name as genre, a.fio as author, b.publish_year, p.name as publisher, "
                + "b.description "
                + "from book as b "
                + "inner join genre as g on g.id = b.genre_id "
                + "inner join author as a on a.id = b.author_id "
                + "inner join publisher as p on p.id = b.publisher_id "
                + "order by b.name";
        return mainSql;
    }
}

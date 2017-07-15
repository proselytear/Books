package com.skyblesoft.books.dao;

import com.skyblesoft.books.model.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Collection;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(book);
        logger.info("Book successfully saved. Book details: " + book);
    }

    @Override
    public void updateBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);
        logger.info("Book successfully updated. Book details: " + book);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> listBooks() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Book> booksList = session.createQuery("from Book").list();

        for (Book b : booksList) {
            logger.info("Books list: " + b);
        }

        return booksList;
    }

    @Override
    public Book getBooksById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, new Integer(id));
        logger.info("Book successfully loaded. Book details: " + book);

        return book;
    }

    @Override
    public void removeBook(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book book = (Book) session.load(Book.class, new Integer(id));

        if (book != null) {
            session.delete(book);
        }

        logger.info("Book deleted successfully. Book details: " + book);
    }

    @Override
    public Collection<Book> getBooks(String search) {
        Session session = this.sessionFactory.getCurrentSession();
        if(search==null||search.trim().isEmpty()){
            return session.createQuery("FROM Book").list();
        }
        return session.createQuery("select c from Book where c.name like :search").
                setParameter("search", search.trim() + "%").list();
    }
}

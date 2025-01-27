package com.netcompany.bookservice.query.projection;

import com.netcompany.bookservice.command.data.Book;
import com.netcompany.bookservice.command.data.BookRepository;
import com.netcompany.bookservice.query.model.BookResponseModel;
import com.netcompany.bookservice.query.queries.GetAllBookQuery;
import com.netcompany.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository; // truy van du lieu trong database

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query) { // listen to get all book query
        List<Book> list = bookRepository.findAll();
        List<BookResponseModel> listBookResponse = new ArrayList<>();
        list.forEach(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book, model);
            listBookResponse.add(model);
        });

//        List<BookResponseModel> result = list.stream().map(book -> {
//            BookResponseModel model = new BookResponseModel();
//            BeanUtils.copyProperties(book, model);
//            return model;
//        }).toList();

        return listBookResponse;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) {
        BookResponseModel bookResponseModel = new BookResponseModel();
        bookRepository.findById(query.getId()).ifPresent(book -> {
            BeanUtils.copyProperties(book, bookResponseModel);
        });
        return bookResponseModel;
    }
}

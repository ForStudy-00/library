package com.tuc.library;

import com.tcu.library.LibraryApplication;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.UserBookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = LibraryApplication.class)
@RunWith(SpringRunner.class)
@EnableScheduling
class LibraryApplicationTests {

    @Autowired
    private BooksService booksService;

    @Autowired
    private UserBookService userBookService;

    @Test
    void contextLoads() {

    }



}

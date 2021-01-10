package com.tuc.library;

import com.tcu.library.LibraryApplication;
import com.tcu.library.entity.UserBook;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.UserBookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
        UserBook userBook=new UserBook();
        userBook.setBorrowNum("10");
        userBook.setBookId("W-20");
        userBook.setTakeTime(new Date());
        userBook.setReturnTime(new Date());
        userBookService.save(userBook);
    }



}

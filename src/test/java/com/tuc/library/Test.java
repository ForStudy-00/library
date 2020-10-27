package com.tuc.library;

import com.tcu.library.LibraryApplication;
import com.tcu.library.entity.User;
import com.tcu.library.entity.UserBook;
import com.tcu.library.service.UserBookService;
import com.tcu.library.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

/**
 * @Author: yjn
 * @Date: 2020/10/25 22:39
 */
@SpringBootTest(classes = LibraryApplication.class)
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBookService userBookService;

    @org.junit.Test
    public void addUser() {

        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setOpenid("openid" + i);
            user.setBorrowNum("" + i);
            user.setNickname("name" + i);
            user.setPhone("" + i);
            userService.save(user);
        }
    }

    public static void main(String[] args) {
        Random random=new Random();
        for (int i = 0; i < 10; i++) {

            System.out.println(random.nextInt(3));
        }
    }

     @org.junit.Test
    public void addUserBook(){
        for (int i=0;i<50;i++){
            UserBook userBook=new UserBook();
            Random random=new Random();

            userBook.setBorrowNum(random.nextInt(9)+30+"");
            userBook.setBookId("test");

            userBook.setReturnTime(new Date());
            userBook.setTakeTime(new Date());
            userBook.setStatus(random.nextInt(4));
            userBookService.save(userBook);
        }
    }

}

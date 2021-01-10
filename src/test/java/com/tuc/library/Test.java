package com.tuc.library;

import com.tcu.library.entity.ReturnBook;
import com.tcu.library.entity.User;
import com.tcu.library.entity.UserBook;
import com.tcu.library.service.ReturnBookService;
import com.tcu.library.service.UserBookService;
import com.tcu.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @Author: yjn
 * @Date: 2020/10/25 22:39
 */
//@SpringBootTest(classes = LibraryApplication.class)
//@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private ReturnBookService returnBookService;

    @org.junit.Test
    public void addUser() {

        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setOpenid("openid" + i);
            user.setBorrowNum("" + i);
            user.setNickname("name" + i);
            user.setPhone("" + i);
            user.setGrade("计算机");
            user.setProfession("软件");
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

            userBook.setBorrowNum(random.nextInt(9)+10+"");
            userBook.setBookId("D-"+(random.nextInt(5)+20));

            userBook.setReturnTime(new Date());
            userBook.setTakeTime(new Date());
            userBook.setStatus(random.nextInt(4));
            userBookService.save(userBook);
        }
    }

    @org.junit.Test
    public void addReturnBook(){
        for (int i = 0; i <50 ; i++) {
            ReturnBook returnBook=new ReturnBook();
            Random random = new Random();
            returnBook.setBookId("D-"+(random.nextInt(5)+20));
            returnBook.setBorrowNum(random.nextInt(49)+"");
            returnBook.setReturnTime(new Date());
            returnBook.setState(random.nextInt(2));
            returnBook.setReason("测试");
            returnBookService.save(returnBook);
        }
    }

    @org.junit.Test
    public void testGetReturnTime() {
        List<HashMap<String, Object>> returnTimeList = userBookService.getReturnTime();
        Date today=new Date();
        returnTimeList.forEach((item)->{
            Date returnTime = (Date) item.get("return_time");
            String id = (String) item.get("id");
            //判断还书日期是否超过今天
            if (today.after(returnTime)){
                userBookService.updataStatusToThree(id);
            }
        });
    }
    @org.junit.Test
    public void testTime() throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = format.format(new Date());
        System.out.println(new Date());
        System.out.println(format1);
        System.out.println(format.parse(format1));
    }
}

package com.tcu.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tcu.library.entity.PendTakenBook;
import com.tcu.library.entity.UserBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
public interface UserBookService extends IService<UserBook> {

    ArrayList<PendTakenBook> getDetailsPendingTaken(String borrowNum);

    List<HashMap<String, Object>> getReturnTime();

    void updataStatusToThree(String id);

    int getBorrowedBookNum(String borrowNum);
}

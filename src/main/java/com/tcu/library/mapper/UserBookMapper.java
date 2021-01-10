package com.tcu.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcu.library.entity.PendTakenBook;
import com.tcu.library.entity.UserBook;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
public interface UserBookMapper extends BaseMapper<UserBook> {

    ArrayList<PendTakenBook> getDetailsPendingTaken(@Param("borrowNum") String borrowNum);

    List<HashMap<String, Object>> getReturnTime();

    void updataStatusToThree(@Param("id") String id);
}

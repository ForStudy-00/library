package com.tcu.library.mapper;

import com.tcu.library.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 通过用户名获取admin
     * @param username
     * @return
     */
   Admin getAdminByUserName(String username);
}

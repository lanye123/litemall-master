package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserExample;

public interface LitemallUserMapper {
    long countByExample(LitemallUserExample example);

    int deleteByExample(LitemallUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LitemallUser record);

    int insertSelective(LitemallUser record);

    List<LitemallUser> selectByExample(LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LitemallUser> selectByExampleSelective(@Param("example") LitemallUserExample example, @Param("selective") LitemallUser.Column ... selective);

    LitemallUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallUser selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallUser.Column ... selective);

    int updateByExampleSelective(@Param("record") LitemallUser record, @Param("example") LitemallUserExample example);

    int updateByExample(@Param("record") LitemallUser record, @Param("example") LitemallUserExample example);

    int updateByPrimaryKeySelective(LitemallUser record);

    int updateByPrimaryKey(LitemallUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallUser selectOneByExample(LitemallUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_user
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LitemallUser selectOneByExampleSelective(@Param("example") LitemallUserExample example, @Param("selective") LitemallUser.Column ... selective);
    List<LitemallUser> listByPlanner(LitemallUserExample example);
    int countByPlanner(LitemallUserExample example);
    List<LitemallUser> tjByCorps();
}
package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.SysBu;
import org.linlinjava.litemall.db.domain.SysBuExample;

public interface SysBuMapper {
    long countByExample(SysBuExample example);

    int deleteByExample(SysBuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysBu record);

    int insertSelective(SysBu record);

    List<SysBu> selectByExample(SysBuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bu
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<SysBu> selectByExampleSelective(@Param("example") SysBuExample example, @Param("selective") SysBu.Column ... selective);

    SysBu selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bu
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SysBu selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SysBu.Column ... selective);

    int updateByExampleSelective(@Param("record") SysBu record, @Param("example") SysBuExample example);

    int updateByExample(@Param("record") SysBu record, @Param("example") SysBuExample example);

    int updateByPrimaryKeySelective(SysBu record);

    int updateByPrimaryKey(SysBu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bu
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SysBu selectOneByExample(SysBuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bu
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    SysBu selectOneByExampleSelective(@Param("example") SysBuExample example, @Param("selective") SysBu.Column ... selective);
}
package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.CsJieguo;
import org.linlinjava.litemall.db.domain.CsJieguoExample;

public interface CsJieguoMapper {
    long countByExample(CsJieguoExample example);

    int deleteByExample(CsJieguoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CsJieguo record);

    int insertSelective(CsJieguo record);

    List<CsJieguo> selectByExample(CsJieguoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_jieguo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<CsJieguo> selectByExampleSelective(@Param("example") CsJieguoExample example, @Param("selective") CsJieguo.Column ... selective);

    CsJieguo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_jieguo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsJieguo selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") CsJieguo.Column ... selective);

    int updateByExampleSelective(@Param("record") CsJieguo record, @Param("example") CsJieguoExample example);

    int updateByExample(@Param("record") CsJieguo record, @Param("example") CsJieguoExample example);

    int updateByPrimaryKeySelective(CsJieguo record);

    int updateByPrimaryKey(CsJieguo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_jieguo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsJieguo selectOneByExample(CsJieguoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_jieguo
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsJieguo selectOneByExampleSelective(@Param("example") CsJieguoExample example, @Param("selective") CsJieguo.Column ... selective);
}
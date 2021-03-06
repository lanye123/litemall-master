package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.Praise;
import org.linlinjava.litemall.db.domain.PraiseExample;

public interface PraiseMapper {
    long countByExample(PraiseExample example);

    int deleteByExample(PraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Praise record);

    int insertSelective(Praise record);

    List<Praise> selectByExample(PraiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table praise
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<Praise> selectByExampleSelective(@Param("example") PraiseExample example, @Param("selective") Praise.Column ... selective);

    Praise selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table praise
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Praise selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Praise.Column ... selective);

    int updateByExampleSelective(@Param("record") Praise record, @Param("example") PraiseExample example);

    int updateByExample(@Param("record") Praise record, @Param("example") PraiseExample example);

    int updateByPrimaryKeySelective(Praise record);

    int updateByPrimaryKey(Praise record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table praise
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Praise selectOneByExample(PraiseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table praise
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Praise selectOneByExampleSelective(@Param("example") PraiseExample example, @Param("selective") Praise.Column ... selective);
}
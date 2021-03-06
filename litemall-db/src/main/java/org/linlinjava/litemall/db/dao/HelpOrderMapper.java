package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.HelpOrder;
import org.linlinjava.litemall.db.domain.HelpOrderExample;

public interface HelpOrderMapper {
    long countByExample(HelpOrderExample example);

    int deleteByExample(HelpOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HelpOrder record);

    int insertSelective(HelpOrder record);

    List<HelpOrder> selectByExample(HelpOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<HelpOrder> selectByExampleSelective(@Param("example") HelpOrderExample example, @Param("selective") HelpOrder.Column ... selective);

    HelpOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    HelpOrder selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") HelpOrder.Column ... selective);

    int updateByExampleSelective(@Param("record") HelpOrder record, @Param("example") HelpOrderExample example);

    int updateByExample(@Param("record") HelpOrder record, @Param("example") HelpOrderExample example);

    int updateByPrimaryKeySelective(HelpOrder record);

    int updateByPrimaryKey(HelpOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    HelpOrder selectOneByExample(HelpOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_order
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    HelpOrder selectOneByExampleSelective(@Param("example") HelpOrderExample example, @Param("selective") HelpOrder.Column ... selective);
}
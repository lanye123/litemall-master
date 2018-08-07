package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.HelpDetail;
import org.linlinjava.litemall.db.domain.HelpDetailExample;

public interface HelpDetailMapper {
    long countByExample(HelpDetailExample example);

    int deleteByExample(HelpDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HelpDetail record);

    int insertSelective(HelpDetail record);

    List<HelpDetail> selectByExample(HelpDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<HelpDetail> selectByExampleSelective(@Param("example") HelpDetailExample example, @Param("selective") HelpDetail.Column ... selective);

    HelpDetail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    HelpDetail selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") HelpDetail.Column ... selective);

    int updateByExampleSelective(@Param("record") HelpDetail record, @Param("example") HelpDetailExample example);

    int updateByExample(@Param("record") HelpDetail record, @Param("example") HelpDetailExample example);

    int updateByPrimaryKeySelective(HelpDetail record);

    int updateByPrimaryKey(HelpDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    HelpDetail selectOneByExample(HelpDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table help_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    HelpDetail selectOneByExampleSelective(@Param("example") HelpDetailExample example, @Param("selective") HelpDetail.Column ... selective);
}
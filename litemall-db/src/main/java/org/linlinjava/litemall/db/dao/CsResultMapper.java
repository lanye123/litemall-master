package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.CsResult;
import org.linlinjava.litemall.db.domain.CsResultExample;

public interface CsResultMapper {
    long countByExample(CsResultExample example);

    int deleteByExample(CsResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CsResult record);

    int insertSelective(CsResult record);

    List<CsResult> selectByExample(CsResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_result
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<CsResult> selectByExampleSelective(@Param("example") CsResultExample example, @Param("selective") CsResult.Column ... selective);

    CsResult selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_result
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsResult selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") CsResult.Column ... selective);

    int updateByExampleSelective(@Param("record") CsResult record, @Param("example") CsResultExample example);

    int updateByExample(@Param("record") CsResult record, @Param("example") CsResultExample example);

    int updateByPrimaryKeySelective(CsResult record);

    int updateByPrimaryKey(CsResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_result
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsResult selectOneByExample(CsResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_result
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsResult selectOneByExampleSelective(@Param("example") CsResultExample example, @Param("selective") CsResult.Column ... selective);

    CsResult getPicUrl(@Param("testId") Integer testId, @Param("account") Integer account);
}
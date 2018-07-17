package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.CsTest;
import org.linlinjava.litemall.db.domain.CsTestExample;

import java.util.List;

public interface CsTestMapper {
    long countByExample(CsTestExample example);

    int deleteByExample(CsTestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CsTest record);

    int insertSelective(CsTest record);

    List<CsTest> selectByExample(CsTestExample example);

    List<CsTest> selectByExample2(CsTestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_test
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<CsTest> selectByExampleSelective(@Param("example") CsTestExample example, @Param("selective") CsTest.Column ... selective);

    CsTest selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_test
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsTest selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") CsTest.Column ... selective);

    int updateByExampleSelective(@Param("record") CsTest record, @Param("example") CsTestExample example);

    int updateByExample(@Param("record") CsTest record, @Param("example") CsTestExample example);

    int updateByPrimaryKeySelective(CsTest record);

    int updateByPrimaryKey(CsTest record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_test
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsTest selectOneByExample(CsTestExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cs_test
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CsTest selectOneByExampleSelective(@Param("example") CsTestExample example, @Param("selective") CsTest.Column ... selective);

  CsTest cascate(Integer id);

  CsTest cascateDync(Integer id);
}

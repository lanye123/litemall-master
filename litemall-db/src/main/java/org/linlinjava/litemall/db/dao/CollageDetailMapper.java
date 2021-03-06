package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.CollageDetail;
import org.linlinjava.litemall.db.domain.CollageDetailExample;

import java.util.List;

public interface CollageDetailMapper {
    long countByExample(CollageDetailExample example);

    long countByExample2(CollageDetailExample example);

    int deleteByExample(CollageDetailExample example);

    int insert(CollageDetail record);

    int insertSelective(CollageDetail record);

    List<CollageDetail> selectByExample(CollageDetailExample example);

    List<CollageDetail> selectByExample2(CollageDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table collage_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<CollageDetail> selectByExampleSelective(@Param("example") CollageDetailExample example, @Param("selective") CollageDetail.Column ... selective);

    int updateByExampleSelective(@Param("record") CollageDetail record, @Param("example") CollageDetailExample example);

    int updateByExample(@Param("record") CollageDetail record, @Param("example") CollageDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table collage_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CollageDetail selectOneByExample(CollageDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table collage_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CollageDetail selectOneByExampleSelective(@Param("example") CollageDetailExample example, @Param("selective") CollageDetail.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table collage_detail
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    CollageDetail selectByPrimaryKeySelective(@Param("selective") CollageDetail.Column ... selective);

    Integer countByPid(Integer pid);

    CollageDetail selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);
}
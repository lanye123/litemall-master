package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.domain.MedalDetailsExample;

public interface MedalDetailsMapper {
    long countByExample(MedalDetailsExample example);

    int deleteByExample(MedalDetailsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MedalDetails record);

    int insertSelective(MedalDetails record);

    List<MedalDetails> selectByExample(MedalDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medal_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<MedalDetails> selectByExampleSelective(@Param("example") MedalDetailsExample example, @Param("selective") MedalDetails.Column ... selective);

    MedalDetails selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medal_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MedalDetails selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MedalDetails.Column ... selective);

    int updateByExampleSelective(@Param("record") MedalDetails record, @Param("example") MedalDetailsExample example);

    int updateByExample(@Param("record") MedalDetails record, @Param("example") MedalDetailsExample example);

    int updateByPrimaryKeySelective(MedalDetails record);

    int updateByPrimaryKey(MedalDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medal_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MedalDetails selectOneByExample(MedalDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table medal_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    MedalDetails selectOneByExampleSelective(@Param("example") MedalDetailsExample example, @Param("selective") MedalDetails.Column ... selective);
}
package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LearnPlanner;
import org.linlinjava.litemall.db.domain.LearnPlannerExample;

public interface LearnPlannerMapper {
    long countByExample(LearnPlannerExample example);

    int deleteByExample(LearnPlannerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LearnPlanner record);

    int insertSelective(LearnPlanner record);

    List<LearnPlanner> selectByExample(LearnPlannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table learn_planner
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<LearnPlanner> selectByExampleSelective(@Param("example") LearnPlannerExample example, @Param("selective") LearnPlanner.Column ... selective);

    LearnPlanner selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table learn_planner
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LearnPlanner selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LearnPlanner.Column ... selective);

    int updateByExampleSelective(@Param("record") LearnPlanner record, @Param("example") LearnPlannerExample example);

    int updateByExample(@Param("record") LearnPlanner record, @Param("example") LearnPlannerExample example);

    int updateByPrimaryKeySelective(LearnPlanner record);

    int updateByPrimaryKey(LearnPlanner record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table learn_planner
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LearnPlanner selectOneByExample(LearnPlannerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table learn_planner
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    LearnPlanner selectOneByExampleSelective(@Param("example") LearnPlannerExample example, @Param("selective") LearnPlanner.Column ... selective);
}
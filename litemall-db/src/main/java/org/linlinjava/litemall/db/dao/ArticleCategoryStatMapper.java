package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.ArticleCategoryStat;
import org.linlinjava.litemall.db.domain.ArticleCategoryStatExample;

public interface ArticleCategoryStatMapper {
    long countByExample(ArticleCategoryStatExample example);

    int deleteByExample(ArticleCategoryStatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCategoryStat record);

    int insertSelective(ArticleCategoryStat record);

    List<ArticleCategoryStat> selectByExample(ArticleCategoryStatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<ArticleCategoryStat> selectByExampleSelective(@Param("example") ArticleCategoryStatExample example, @Param("selective") ArticleCategoryStat.Column ... selective);

    ArticleCategoryStat selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleCategoryStat selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") ArticleCategoryStat.Column ... selective);

    int updateByExampleSelective(@Param("record") ArticleCategoryStat record, @Param("example") ArticleCategoryStatExample example);

    int updateByExample(@Param("record") ArticleCategoryStat record, @Param("example") ArticleCategoryStatExample example);

    int updateByPrimaryKeySelective(ArticleCategoryStat record);

    int updateByPrimaryKey(ArticleCategoryStat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleCategoryStat selectOneByExample(ArticleCategoryStatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_category_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleCategoryStat selectOneByExampleSelective(@Param("example") ArticleCategoryStatExample example, @Param("selective") ArticleCategoryStat.Column ... selective);
}
package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.ArticleDetails;
import org.linlinjava.litemall.db.domain.ArticleDetailsExample;

public interface ArticleDetailsMapper {
    long countByExample(ArticleDetailsExample example);

    int deleteByExample(ArticleDetailsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleDetails record);

    int insertSelective(ArticleDetails record);

    List<ArticleDetails> selectByExample(ArticleDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<ArticleDetails> selectByExampleSelective(@Param("example") ArticleDetailsExample example, @Param("selective") ArticleDetails.Column ... selective);

    ArticleDetails selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleDetails selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") ArticleDetails.Column ... selective);

    int updateByExampleSelective(@Param("record") ArticleDetails record, @Param("example") ArticleDetailsExample example);

    int updateByExample(@Param("record") ArticleDetails record, @Param("example") ArticleDetailsExample example);

    int updateByPrimaryKeySelective(ArticleDetails record);

    int updateByPrimaryKey(ArticleDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleDetails selectOneByExample(ArticleDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_details
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleDetails selectOneByExampleSelective(@Param("example") ArticleDetailsExample example, @Param("selective") ArticleDetails.Column ... selective);
}
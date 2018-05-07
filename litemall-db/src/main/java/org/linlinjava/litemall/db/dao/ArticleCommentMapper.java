package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.ArticleComment;
import org.linlinjava.litemall.db.domain.ArticleCommentExample;

public interface ArticleCommentMapper {
    long countByExample(ArticleCommentExample example);

    int deleteByExample(ArticleCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_comment
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<ArticleComment> selectByExampleSelective(@Param("example") ArticleCommentExample example, @Param("selective") ArticleComment.Column ... selective);

    ArticleComment selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_comment
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleComment selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") ArticleComment.Column ... selective);

    int updateByExampleSelective(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExample(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_comment
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleComment selectOneByExample(ArticleCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_comment
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ArticleComment selectOneByExampleSelective(@Param("example") ArticleCommentExample example, @Param("selective") ArticleComment.Column ... selective);
}
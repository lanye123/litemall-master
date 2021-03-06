package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.Letter;
import org.linlinjava.litemall.db.domain.LetterExample;

public interface LetterMapper {
    long countByExample(LetterExample example);

    int deleteByExample(LetterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Letter record);

    int insertSelective(Letter record);

    List<Letter> selectByExample(LetterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table letter
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<Letter> selectByExampleSelective(@Param("example") LetterExample example, @Param("selective") Letter.Column ... selective);

    Letter selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table letter
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Letter selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Letter.Column ... selective);

    int updateByExampleSelective(@Param("record") Letter record, @Param("example") LetterExample example);

    int updateByExample(@Param("record") Letter record, @Param("example") LetterExample example);

    int updateByPrimaryKeySelective(Letter record);

    int updateByPrimaryKey(Letter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table letter
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Letter selectOneByExample(LetterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table letter
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Letter selectOneByExampleSelective(@Param("example") LetterExample example, @Param("selective") Letter.Column ... selective);
}
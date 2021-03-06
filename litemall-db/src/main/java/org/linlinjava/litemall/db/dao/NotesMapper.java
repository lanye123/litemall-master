package org.linlinjava.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.domain.NotesExample;

import java.util.List;

public interface NotesMapper {
    long countByExample(NotesExample example);

    int deleteByExample(NotesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Notes record);

    int insertSelective(Notes record);

    List<Notes> selectByExample(NotesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notes
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<Notes> selectByExampleSelective(@Param("example") NotesExample example, @Param("selective") Notes.Column ... selective);

    Notes selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notes
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Notes selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Notes.Column ... selective);

    int updateByExampleSelective(@Param("record") Notes record, @Param("example") NotesExample example);

    int updateByExample(@Param("record") Notes record, @Param("example") NotesExample example);

    int updateByPrimaryKeySelective(Notes record);

    int updateByPrimaryKey(Notes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notes
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Notes selectOneByExample(NotesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notes
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Notes selectOneByExampleSelective(@Param("example") NotesExample example, @Param("selective") Notes.Column ... selective);
}
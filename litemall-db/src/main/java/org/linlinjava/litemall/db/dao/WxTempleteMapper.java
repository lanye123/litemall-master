package org.linlinjava.litemall.db.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.WxTemplete;
import org.linlinjava.litemall.db.domain.WxTempleteExample;

public interface WxTempleteMapper {
    long countByExample(WxTempleteExample example);

    int deleteByExample(WxTempleteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WxTemplete record);

    int insertSelective(WxTemplete record);

    List<WxTemplete> selectByExample(WxTempleteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_templete
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<WxTemplete> selectByExampleSelective(@Param("example") WxTempleteExample example, @Param("selective") WxTemplete.Column ... selective);

    WxTemplete selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_templete
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    WxTemplete selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") WxTemplete.Column ... selective);

    int updateByExampleSelective(@Param("record") WxTemplete record, @Param("example") WxTempleteExample example);

    int updateByExample(@Param("record") WxTemplete record, @Param("example") WxTempleteExample example);

    int updateByPrimaryKeySelective(WxTemplete record);

    int updateByPrimaryKey(WxTemplete record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_templete
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    WxTemplete selectOneByExample(WxTempleteExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_templete
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    WxTemplete selectOneByExampleSelective(@Param("example") WxTempleteExample example, @Param("selective") WxTemplete.Column ... selective);

    List<JSONObject> queryList();

    void deleteAll();
}
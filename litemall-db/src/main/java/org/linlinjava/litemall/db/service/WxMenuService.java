package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.WxMenuMapper;
import org.linlinjava.litemall.db.domain.WxMenu;
import org.linlinjava.litemall.db.domain.WxMenuExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName WxMenuService
 * Description TODO
 * Author leiqiang
 * Date 2018/8/10 10:43
 * Version 1.0
 **/
@Service
public class WxMenuService {
    @Resource
    private WxMenuMapper wxMenuMapper;

    public List<WxMenu> list() {
        WxMenuExample example=new WxMenuExample();
        WxMenuExample.Criteria criteria=example.createCriteria();
        criteria.andDeletedEqualTo(0);
        example.setOrderByClause("sort_no asc");
       return wxMenuMapper.selectByExample(example);
    }
}

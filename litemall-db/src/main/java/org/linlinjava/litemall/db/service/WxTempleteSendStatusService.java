package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.linlinjava.litemall.db.dao.WxTempleteSendStatusMapper;
import org.linlinjava.litemall.db.domain.WxGzhUser;
import org.linlinjava.litemall.db.domain.WxTempleteSendStatus;
import org.linlinjava.litemall.db.domain.WxTempleteSendStatusExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName WxTempleteSendStatusService
 * Description TODO
 * Author leiqiang
 * Date 2018/8/2 17:10
 * Version 1.0
 **/
@Service
public class WxTempleteSendStatusService {
    @Resource
    private WxTempleteSendStatusMapper wxTempleteSendStatusMapper;

    public void create(WxTempleteSendStatus sendStatus){
        wxTempleteSendStatusMapper.insertSelective(sendStatus);
    }

    public List<WxTempleteSendStatus> querySelective(String templete_id, String title, Integer page, Integer limit, String sort, String order) {
        WxTempleteSendStatusExample example=new WxTempleteSendStatusExample();
        WxTempleteSendStatusExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(templete_id))
        criteria.andTempleteIdEqualTo(templete_id);
        if(StringUtils.isNotEmpty(title))
        criteria.andTitleEqualTo(title);
        if(StringUtils.isNotEmpty(order))
        example.setOrderByClause(order);
        if(page!=null&&limit!=null)
        PageHelper.startPage(page, limit);
        return wxTempleteSendStatusMapper.queryByList(example);
    }

    public int countSeletive(String templete_id, String title) {
        WxTempleteSendStatusExample example=new WxTempleteSendStatusExample();
        WxTempleteSendStatusExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotEmpty(templete_id))
            criteria.andTempleteIdEqualTo(templete_id);
        if(StringUtils.isNotEmpty(title))
            criteria.andTitleEqualTo(title);
        example.setOrderByClause("create_date desc");
        return (int)wxTempleteSendStatusMapper.countByExample(example);
    }
}

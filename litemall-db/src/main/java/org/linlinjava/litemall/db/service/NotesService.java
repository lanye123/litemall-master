package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.NotesMapper;
import org.linlinjava.litemall.db.domain.Notes;
import org.linlinjava.litemall.db.domain.NotesExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
  * @author lanye
  * @Description 通知记录相关接口实现
  * @Date 2018/5/29 16:34
  * @Param
  * @return
  **/
@Service
public class NotesService {
    @Resource
    private NotesMapper notesMapper;
    
    public void add(Notes notes) {
        notesMapper.insertSelective(notes);
    }

    public void update(Notes notes) {
        notesMapper.updateByPrimaryKeySelective(notes);
    }

    public Notes findById(Integer id) {
        return notesMapper.selectByPrimaryKey(id);
    }

    public List<Notes> querySelective(Integer tempId,Integer type, Integer userId,Integer fromUserid,Integer infoid, String status, Integer page, Integer size, String sort, String order) {
        NotesExample example = new NotesExample();
        NotesExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(tempId)){
            criteria.andTempIdEqualTo(tempId);
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(infoid)){
            criteria.andInfoidEqualTo(infoid);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        criteria.example().setOrderByClause("create_date desc");

        if(page!=null && size!=null){

            PageHelper.startPage(page, size);
        }
        return notesMapper.selectByExample(example);
    }

    public int countSeletive(Integer tempId,Integer type, Integer userId,Integer fromUserid,Integer infoid, String status, Integer page, Integer size, String sort, String order) {
        NotesExample example = new NotesExample();
        NotesExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(tempId)){
            criteria.andTempIdEqualTo(tempId);
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(fromUserid)){
            criteria.andFromUseridEqualTo(fromUserid);
        }
        if(!StringUtils.isEmpty(infoid)){
            criteria.andInfoidEqualTo(infoid);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }

        return (int) notesMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        notesMapper.deleteByPrimaryKey(id);
    }
    
}

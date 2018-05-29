package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.NotesTempMapper;
import org.linlinjava.litemall.db.domain.NotesTemp;
import org.linlinjava.litemall.db.domain.NotesTempExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
  * @author lanye
  * @Description 通知模板相关接口实现
  * @Date 2018/5/29 16:29
  * @Param 
  * @return 
  **/
@Service
public class NotesTempService {
    @Resource
    private NotesTempMapper notesTempMapper;
    
    public void add(NotesTemp notesTemp) {
        notesTempMapper.insertSelective(notesTemp);
    }

    public void update(NotesTemp notesTemp) {
        notesTempMapper.updateByPrimaryKeySelective(notesTemp);
    }

    public NotesTemp findById(Integer id) {
        return notesTempMapper.selectByPrimaryKey(id);
    }

    public List<NotesTemp> querySelective(String no, String name, Integer type, String status, Integer page, Integer size, String sort, String order) {
        NotesTempExample example = new NotesTempExample();
        NotesTempExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(no)){
            criteria.andNoEqualTo(no);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo(name);
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        criteria.example().setOrderByClause("create_date desc");

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return notesTempMapper.selectByExample(example);
    }

    public int countSeletive(String no, String name, Integer type, String status, Integer page, Integer size, String sort, String order) {
        NotesTempExample example = new NotesTempExample();
        NotesTempExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(no)){
            criteria.andNoEqualTo(no);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameEqualTo(name);
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }

        return (int) notesTempMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        notesTempMapper.deleteByPrimaryKey(id);
    }
    
}

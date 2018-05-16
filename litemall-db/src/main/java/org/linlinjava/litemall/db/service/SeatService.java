package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.SeatMapper;
import org.linlinjava.litemall.db.domain.Seat;
import org.linlinjava.litemall.db.domain.SeatExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SeatService {
    @Resource
    private SeatMapper seatMapper;

    public Seat findById(Integer id) {
        return seatMapper.selectByPrimaryKey(id);
    }

    public void add(Seat Seat) {
        seatMapper.insertSelective(Seat);
    }

    public void update(Seat Seat) {
        seatMapper.updateByPrimaryKeySelective(Seat);
    }

    public List<Seat> querySelective(Integer formId, Integer page, Integer size, String sort, String order) {
        SeatExample example = new SeatExample();
        SeatExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(formId)){
            criteria.andFormIdEqualTo(formId);
        }
        criteria.example().setOrderByClause("create_date desc");

        if(page!=null && size!=null){
            PageHelper.startPage(page, size);
        }
        return seatMapper.selectByExample(example);
    }

    public int countSeletive(Integer formId, Integer page, Integer size, String sort, String order) {
        SeatExample example = new SeatExample();
        SeatExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(formId)){
            criteria.andFormIdEqualTo(formId);
        }

        return (int) seatMapper.countByExample(example);
    }

    public int count() {
        SeatExample example = new SeatExample();

        return (int)seatMapper.countByExample(example);
    }

    public void deleteById(Integer id) {
        seatMapper.deleteByPrimaryKey(id);
    }
}

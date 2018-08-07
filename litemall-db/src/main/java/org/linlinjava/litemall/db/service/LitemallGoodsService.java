package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallGoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.domain.LitemallGoodsExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LitemallGoodsService {
    @Resource
    private LitemallGoodsMapper goodsMapper;

    public List<LitemallGoods> queryByHot(int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIsHotEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<LitemallGoods> queryByNew(int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIsNewEqualTo(true).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<LitemallGoods> queryByCategory(List<Integer> catList, int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public int countByCategory(List<Integer> catList, int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andCategoryIdIn(catList).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<LitemallGoods> queryByCategory(Integer catId,Integer type, int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        criteria.andStateEqualTo(0);
        example.setOrderByClause("sort_no");
        PageHelper.startPage(offset, limit);
        return goodsMapper.selectByExample(example);
    }

    public List<LitemallGoods> helpList(Integer catId, Integer userId) {
        LitemallGoods goods = new LitemallGoods();
        goods.setCategoryId(catId);
        if(!StringUtils.isEmpty(userId)){
            goods.setUserId(userId);
        }
        goods.setOrderByClause("sort_no");
        goods.setType(3);
        goods.setState(0);
        return goodsMapper.selectByExample2(goods);
    }

    public int countByCategory(Integer catId, Integer page, Integer size) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<LitemallGoods> querySelective(Integer catId, Integer brandId, String keyword, Integer isHot, Integer isNew, Integer offset, Integer limit, String sort) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();

        if(catId != null && catId != 0){
            criteria.andCategoryIdEqualTo(catId);
        }
        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew.intValue() == 1);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot.intValue() == 1);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        if(sort != null){
            example.setOrderByClause(sort);
        }
        if(limit != null && offset != null) {
            PageHelper.startPage(offset, limit);
        }

        LitemallGoods.Column[] columns = new LitemallGoods.Column[]{LitemallGoods.Column.id, LitemallGoods.Column.name, LitemallGoods.Column.listPicUrl, LitemallGoods.Column.retailPrice};
        return goodsMapper.selectByExampleSelective(example ,columns);
    }

    public int countSelective(Integer catId, Integer brandId, String keyword, Integer isHot, Integer isNew, Integer offset, Integer limit, String sort) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();

        if(catId != null){
            criteria.andCategoryIdEqualTo(catId);
        }
        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew.intValue() == 1);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot.intValue() == 1);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public LitemallGoods findById(Integer id) {
        if(id==null){
            return null;
        }
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }


    public List<LitemallGoods> queryByIds(List<Integer> relatedGoodsIds) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIdIn(relatedGoodsIds).andDeletedEqualTo(false);
        return goodsMapper.selectByExample(example);
    }

    public Integer queryOnSale() {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<LitemallGoods> querySelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(order)){
            example.setOrderByClause(order);
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return goodsMapper.selectByExample(example);
    }

    public int countSelective(String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        return (int)goodsMapper.countByExample(example);
    }

    public void updateById(LitemallGoods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void deleteById(Integer id) {
        LitemallGoods goods = goodsMapper.selectByPrimaryKey(id);
        if(goods == null){
            return;
        }
        goods.setDeleted(true);
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void add(LitemallGoods goods) {
        goodsMapper.insertSelective(goods);
    }

    public int count() {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int)goodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keyword, Integer isHot, Integer isNew) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();

        if(brandId != null){
            criteria.andBrandIdEqualTo(brandId);
        }
        if(isNew != null){
            criteria.andIsNewEqualTo(isNew.intValue() == 1);
        }
        if(isHot != null){
            criteria.andIsHotEqualTo(isHot.intValue() == 1);
        }
        if(keyword != null){
            criteria.andKeywordsLike("%" + keyword + "%");
        }
        criteria.andDeletedEqualTo(false);

        List<LitemallGoods> goodsList = goodsMapper.selectByExampleSelective(example, LitemallGoods.Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for(LitemallGoods goods : goodsList){
            cats.add(goods.getCategoryId());
        }
        return cats;
    }
}

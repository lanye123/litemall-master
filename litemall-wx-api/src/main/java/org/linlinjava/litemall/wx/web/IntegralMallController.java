package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/integralMall")
public class IntegralMallController {
    private final Log logger = LogFactory.getLog(IntegralMallController.class);

    @Autowired
    private LitemallAdService adService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallBrandService brandService;
    @Autowired
    private LitemallTopicService topicService;
    @Autowired
    private LitemallCategoryService categoryService;
    @Autowired
    private LitemallProductService productService;
    @Autowired
    private LitemallIssueService goodsIssueService;
    @Autowired
    private LitemallGoodsAttributeService goodsAttributeService;
    @Autowired
    private LitemallCommentService commentService;
    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallCollectService collectService;
    @Autowired
    private LitemallFootprintService footprintService;
    @Autowired
    private LitemallGoodsSpecificationService goodsSpecificationService;

    /**
     * app首页
     *
     * @return app首页相关信息
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              banner: xxx,
     *              channel: xxx,
     *              newGoodsList: xxx,
     *              hotGoodsList: xxx,
     *              topicList: xxx,
     *              floorGoodsList: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("/goodsIndex")
    public Object index() {
        Map<String, Object> data = new HashMap<>();

        List<LitemallCategory> channel = categoryService.queryChannel();
        data.put("channel", channel);

        List<LitemallAd> banner = adService.queryByApid(1);
        data.put("banner", banner);

        List<LitemallGoods> newGoods = goodsService.queryByNew(0, 4);
        data.put("newGoodsList", newGoods);

        List<LitemallGoods> hotGoods = goodsService.queryByHot(0, 3);
        data.put("hotGoodsList", hotGoods);

        List<LitemallBrand> brandList = brandService.query(0,4);
        data.put("brandList", brandList);

        List<LitemallTopic> topicList = topicService.queryList(0, 3);
        data.put("topicList", topicList);

        List<Map> categoryList = new ArrayList<>();
        List<LitemallCategory> catL1List = categoryService.queryL1WithoutRecommend(0, 6);
        for (LitemallCategory catL1 : catL1List) {
            List<LitemallCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<Integer> l2List = new ArrayList<>();
            for (LitemallCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<LitemallGoods> categoryGoods = goodsService.queryByCategory(l2List, 0, 5);
            Map catGoods = new HashMap();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            categoryList.add(catGoods);
        }
        data.put("floorGoodsList", categoryList);

        return ResponseUtil.ok(data);
    }

    /**
     * 商品详情
     *
     * 用户可以不登录。
     * 如果用户登录，则记录用户足迹以及返回用户收藏信息。
     *
     * @param userId 用户ID
     * @param id 商品ID
     * @return 商品详情
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              info: xxx,
     *              userHasCollect: xxx,
     *              issue: xxx,
     *              comment: xxx,
     *              specificationList: xxx,
     *              productList: xxx,
     *              attribute: xxx,
     *              brand: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("goodsDetail")
    public Object detail(@LoginUser Integer userId, Integer id) {
        if(id == null){
            return ResponseUtil.badArgument();
        }

        // 商品信息
        LitemallGoods info = goodsService.findById(id);

        // 商品属性
        List<LitemallGoodsAttribute> goodsAttributeList = goodsAttributeService.queryByGid(id);

        // 商品规格
        // 返回的是定制的GoodsSpecificationVo
        Object specificationList = goodsSpecificationService.getSpecificationVoList(id);

        // 商品规格对应的数量和价格
        List<LitemallProduct> productList = productService.queryByGid(id);

        // 商品问题，这里是一些通用问题
        List<LitemallIssue> issue = goodsIssueService.query();

        // 商品品牌商
        LitemallBrand brand = brandService.findById(info.getBrandId());

        // 评论
        List<LitemallComment> comments = commentService.queryGoodsByGid(id, 0, 2);
        List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());
        int commentCount = commentService.countGoodsByGid(id, 0, 2);
        for(LitemallComment comment : comments){
            Map<String, Object> c = new HashMap<>();
            c.put("id", comment.getId());
            c.put("addTime", comment.getAddTime());
            c.put("content", comment.getContent());
            LitemallUser user = userService.findById(comment.getUserId());
            c.put("nickname", user.getNickname());
            c.put("avatar", user.getAvatar());
            c.put("picList", comment.getPicUrls());
            commentsVo.add(c);
        }
        Map<String, Object> commentList = new HashMap<>();
        commentList.put("count", commentCount);
        commentList.put("data", commentsVo);

        // 用户收藏
        int userHasCollect = 0;
        if(userId != null) {
            userHasCollect = collectService.count(userId, id);
        }

        // 记录用户的足迹
        if(userId != null) {
            LitemallFootprint footprint = new LitemallFootprint();
            footprint.setAddTime(LocalDateTime.now());
            footprint.setUserId(userId);
            footprint.setGoodsId(id);
            footprintService.add(footprint);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("info", info);
        data.put("userHasCollect", userHasCollect);
        data.put("issue", issue);
        data.put("comment", commentList);
        data.put("specificationList", specificationList);
        data.put("productList", productList);
        data.put("attribute", goodsAttributeList);
        data.put("brand", brand);

        return ResponseUtil.ok(data);
    }
}
package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/wx/letter")
public class WxLetterController {
    private final Log logger = LogFactory.getLog(WxLetterController.class);

    @Autowired
    private LetterService letterService;
    @Autowired
    private LetterReplyService letterReplyService;

    /**
     * app秘密城堡
     *
     * @return app 秘密城堡相关信息
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              returnLetters: returnLetters(每一条包含未读条数与信的内容)
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("/letterList")
    public Object letterList(@LoginUser Integer userId) {
        if(userId == null){
            return ResponseUtil.badArgument();
        }
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> dataItem;

        List<Letter> letterList = letterService.querySelective(userId, "", null,1, 99, "", "wxStatus");

        List<Map<String,Object>> returnLetters = new ArrayList<>();
        int count ;
        for(Letter letter:letterList){
            dataItem = new HashMap<>();
            count = letterReplyService.countSeletive(letter.getId(), "", 0, 1,99, "", "");
            dataItem.put("count",count);
            dataItem.put("letter",letter);
            returnLetters.add(dataItem);
        }
        data.put("returnLetters", returnLetters);

        return ResponseUtil.ok(data);
    }

    /**
     * app秘密城堡
     *
     * @return app 点击信后返回相关信息
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              letterReplyList: letterReplyList
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("/letterReplyList")
    public Object letterReplyList(Integer letterId) {
        if(letterId == null){
            return ResponseUtil.badArgument();
        }
        Map<String, Object> data = new HashMap<>();

        List<LetterReply> letterReplyList = letterReplyService.querySelective(letterId, "", null,1, 999, "", "wxCreateDate");
        data.put("letterReplyList", letterReplyList);

        return ResponseUtil.ok(data);
    }

    /**
     * app秘密城堡
     *
     * @return app 写信
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("/createLetter")
    public Object createLetter(@RequestBody Letter letter){
        logger.debug(letter);

        letterService.add(letter);
        return ResponseUtil.ok(letter);
    }

    /**
     * app秘密城堡
     *
     * @return app 回复
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("/createLetterReply")
    public Object createLetterReply(@RequestBody LetterReply letterReply){
        logger.debug(letterReply);

        letterReplyService.add(letterReply);
        return ResponseUtil.ok(letterReply);
    }

    /**
     * app秘密城堡
     *
     * @return app 更新回复已读状态
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("/updateLetterReplyStatus")
    public Object updateLetterReply(@RequestBody LetterReply letterReply){
        logger.debug(letterReply);

        letterReplyService.update(letterReply);
        return ResponseUtil.ok(letterReply);
    }

}
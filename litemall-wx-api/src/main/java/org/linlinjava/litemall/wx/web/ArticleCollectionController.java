package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.domain.ArticleCollection;
import org.linlinjava.litemall.db.domain.LitemallAddress;
import org.linlinjava.litemall.db.service.ArticleCollectionService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx/collection")
public class ArticleCollectionController {
    @Autowired
    private ArticleCollectionService articleCollectionService;
    /**
    *@Author:LeiQiang
    *@Description:收藏列表接口
    *@Date:23:19 2018/5/4
    */
    @GetMapping("list")
    public Object list(){
        return ResponseUtil.ok();
    }
    /**
     *@Author:LeiQiang
     *@Description:图文-收藏接口
     *@Date:23:24 2018/5/4
     */
    @PostMapping("save")
    public Object save(@LoginUser Integer userId, @RequestBody ArticleCollection collection) {
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        if(collection == null){
            return ResponseUtil.badArgument();
        }

        if (collection.getId() == null || collection.getId().equals(0)) {
            collection.setId(null);
            collection.setUserId(userId);
            articleCollectionService.add(collection);
        } else {
            collection.setUserId(userId);
            articleCollectionService.update(collection);
        }
        return ResponseUtil.ok();
    }
}

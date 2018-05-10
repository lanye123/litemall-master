package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.db.service.PraiseCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/praiseComment")
public class PraiseCommentController {
    @Autowired
    private PraiseCommentService praiseCommentService;


}

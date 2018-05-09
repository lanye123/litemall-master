package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.MedalDetails;
import org.linlinjava.litemall.db.service.MedalDetailsService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/sunlands/medalDetails")
public class MedalDetailsController {
    private final Log logger = LogFactory.getLog(MedalDetailsController.class);

    @Autowired
    private MedalDetailsService medalDetailsService;

    @GetMapping("/list")
    public Object list(Integer notesId,Integer articleId,Integer userId,Integer medalId,Integer amount,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<MedalDetails> medalDetailsList = medalDetailsService.querySelective(notesId, articleId, userId,medalId,amount,page, limit, sort, order);
        int total = medalDetailsService.countSeletive(notesId, articleId, userId,medalId,amount,page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", medalDetailsList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody MedalDetails medalDetails){
        logger.debug(medalDetails);

        medalDetailsService.add(medalDetails);
        return ResponseUtil.ok(medalDetails);
    }

    @PostMapping("/update")
    public Object update(@RequestBody MedalDetails medalDetails){
        logger.debug(medalDetails);

        medalDetailsService.update(medalDetails);
        return ResponseUtil.ok(medalDetails);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){

        medalDetailsService.deleteById(id);
        return ResponseUtil.ok(id);
    }
}

package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.domain.Seat;
import org.linlinjava.litemall.db.service.SeatService;
import org.linlinjava.litemall.db.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/seat")
public class SeatController {
    private final Log logger = LogFactory.getLog(SeatController.class);

    @Autowired
    private SeatService seatService;

    @GetMapping("/list")
    public Object list(Integer formId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        Map<String, Object> data = new HashMap<>();
        if(formId == null){
            return ResponseUtil.ok(data);
        }
        List<Seat> seatList = seatService.querySelective(formId,page, 99, sort, order);
        int total = seatService.countSeletive(formId, page, limit, sort, order);
        data.put("total", total);
        data.put("item", seatList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Seat seat){
        if(seat == null){
            return ResponseUtil.badArgument();
        }
        if(seat.getFormId() == null){
            return ResponseUtil.badArgument();
        }
        if(seat.getName() == null){
            return ResponseUtil.badArgument();
        }
        if(seat.getTelephone() == null){
            return ResponseUtil.badArgument();
        }
        logger.debug(seat);

        seatService.add(seat);
        return ResponseUtil.ok(seat);
    }

    @PostMapping("/update")
    public Object update(@RequestBody Seat seat){
        logger.debug(seat);

        seatService.update(seat);
        return ResponseUtil.ok(seat);
    }

    @PostMapping("/delete")
    public Object delete(Integer id){

        seatService.deleteById(id);
        return ResponseUtil.ok(id);
    }
}

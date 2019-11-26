package com.kgc.controller;
import com.kgc.domain.SysLog;
import com.kgc.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    @Autowired
    private ISysLogService logService;
    @RequestMapping("/findAll")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv  = new ModelAndView();
        List<SysLog> logList = logService.findAll();
        mv.addObject("sysLogs",logList);
        mv.setViewName("syslog-list");
        return mv;
    }
}

package com.dbsgapi.dbsgapi.api.ipo.controller;

import com.dbsgapi.dbsgapi.api.ipo.service.IpoService;
import com.dbsgapi.dbsgapi.api.ipo.dto.IpoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IpoController {
    private final IpoService ipoService;

    @RequestMapping(value = {"/ipo/ipoDashboard.do","/" })
    public ModelAndView openIpoDashboard() throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoDashboard");

        return mv;
    }

    @RequestMapping("/ipo/ipoInform.do")
    public ModelAndView openIpoInform() throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoInform");

        return mv;
    }

    @RequestMapping("/ipo/ipoAdmin.do")
    public ModelAndView openIpoAdmin() throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoAdmin");

        return mv;
    }

    @RequestMapping("/ipo/ipoList.do")
    public ModelAndView openIpoList() throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoList");

        return mv;
    }
    /*
    @RequestMapping("/ipo/ipoList.ajax")
    @ResponseBody
    public List<IpoSummaryDto> ipoListAjax(int page = 1, int num = 20, String kind = "all") throws Exception {
        List<IpoSummaryDto> list = ipoService.selectIpos(String kind, int page, int num);
        return list;
    }
     */

    @RequestMapping("/ipo/ipoEditer.do")
    public ModelAndView openIpoEditer(@RequestParam int ipoIndex) throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoEditer");
        log.warn("Start ipo Editer Controller");
        IpoDto ipo = ipoService.selectIpo(ipoIndex);

        mv.addObject("ipo", ipo);
        return mv;
    }

    @RequestMapping("/ipo/ipoWrite.do")
    public ModelAndView openIpoWrite() throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoWrite");
        return mv;
    }

//    @RequestMapping(value="/ipo", method=RequestMethod.POST)
//    public String insertIpo(IpoDto ipo) throws Exception {
//        log.warn("start POST controller => " + ipo);
//        ipoService.insertIpo(ipo);
//
//        return "redirect:/ipo/ipoList.do";
//    }
//
//    @RequestMapping(value="/ipo/{ipoIndex}", method=RequestMethod.PUT)
//    public String updateIpo(IpoDto ipo) throws Exception {
//        log.warn("start put controller => " + ipo);
//        ipoService.updateIpo(ipo);
//
//        return "redirect:/ipo/"+Integer.toString(ipo.getIpoIndex());
//    }
//
//    @RequestMapping(value="/ipo/{ipoIndex}", method=RequestMethod.DELETE)
//    public String deleteIpo(IpoDto ipo) throws Exception {
//        ipoService.deleteIpo(ipo.getIpoIndex());
//
//        return "redirect:/ipo/ipoList.do";
//    }
//
    @RequestMapping(value="/ipo/{ipoIndex}", method= RequestMethod.GET)
    public ModelAndView openIpoDetail(@PathVariable("ipoIndex") int ipoIndex) throws Exception {
        ModelAndView mv = new ModelAndView("/admin/page/ipoDetail");

        IpoDto ipo = ipoService.selectIpo(ipoIndex);
        mv.addObject("ipo", ipo);

        log.warn("IpoDetail Result <= " + mv);
        return mv;
    }
}

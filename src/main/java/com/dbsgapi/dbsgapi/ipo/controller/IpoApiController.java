package com.dbsgapi.dbsgapi.ipo.controller;

import com.dbsgapi.dbsgapi.ipo.dto.IpoDto;
import com.dbsgapi.dbsgapi.ipo.service.IpoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ipo")
public class IpoApiController {
    @Autowired
    private IpoService ipoService;

    @RequestMapping(value="", method = RequestMethod.GET)
    public ResponseEntity<List<IpoDto>> getIpoList() throws Exception {
        List<IpoDto> listIpo = ipoService.selectIpos();
        log.debug(listIpo.toString());
        return new ResponseEntity<>(listIpo, HttpStatus.OK);
    }
}

package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller


public class FrontController {


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getHomePage() {
	return "list";  // localhost:8080/list/
    }

}

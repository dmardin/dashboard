package edu.hm.hafner.warningsngui.controller;

import com.google.gson.Gson;
import edu.hm.hafner.warningsngui.rest.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @Autowired
    private RestService restService;

    @RequestMapping(path = {"/", "home"}, method = RequestMethod.GET)
    public String home(final Model model) {
        String result = restService.getDataFromJenkins("http://localhost:8080/job/Dashboard/7/warnings-ng/api/json");
        System.out.println(result);
        model.addAttribute("resultJson", result);

        String result1 = restService.getDataFromJenkins("http://localhost:8080/job/Dashboard/7/checkstyle/api/json");
        System.out.println(result1);
        model.addAttribute("resultJson1", result1);

        String result2 = restService.getDataFromJenkins("http://localhost:8080/job/Dashboard/7/checkstyle/new/api/json");
        System.out.println(result2);
        model.addAttribute("resultJson2", result2);

        String result3 = restService.getDataFromJenkins("http://localhost:8080/job/Dashboard/7/pmd/all/api/json");
        System.out.println(result3);
        model.addAttribute("resultJson3", result3);

        return "home";
    }
}

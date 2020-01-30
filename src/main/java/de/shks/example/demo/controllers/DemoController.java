package de.shks.example.demo.controllers;

import org.springframework.web.bind.annotation.*;
import org.slf4j.*;

@RestController
public class DemoController {
    Logger log = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/demo")
    public String demo(){
        return "Hello aus dem Controller";
    }

    @GetMapping("/demo/{name}")
    public String indexMitName(@PathVariable String name){
        return "Hello "+ name;
    }

    @PostMapping("/demo")
    public String index2(){
        return "Hallo aus POST";
    }

    @DeleteMapping("/demo")
    public void  index3(){
        log.info("DELETE wurde aufgerufen.");
        // return "Hallo aus DELETE";
    }

    @RequestMapping(value = "/demo", method=RequestMethod.GET, produces = "text/html")
    public String demoAccept(){
        return "<html>"+
            "<body>"+
            "<h1>Die HTML Demo</h1>" +
            "</body></html>" ;
    }
}
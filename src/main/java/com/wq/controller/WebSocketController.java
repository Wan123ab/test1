package com.wq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/message")
public class WebSocketController {

    @RequestMapping(value = "/ws", method = RequestMethod.GET)
    public String login() {

        return "ws";
    }
}

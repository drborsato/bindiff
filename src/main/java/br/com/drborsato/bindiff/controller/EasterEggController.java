package br.com.drborsato.bindiff.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/easter/egg")
public class EasterEggController {
    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public String getEasterEgg() {
        return "HAPPY EASTER!";
    }
}

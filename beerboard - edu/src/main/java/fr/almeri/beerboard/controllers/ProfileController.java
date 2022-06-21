package fr.almeri.beerboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @GetMapping("TODO")
    public String profile(Model pModel){
        /*TODO*/
        return "";
    }

    // COMMIT DU 21/06/2022 à 14H
}

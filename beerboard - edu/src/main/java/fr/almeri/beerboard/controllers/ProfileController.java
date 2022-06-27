package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.BiereId;
import fr.almeri.beerboard.models.Brasserie;
import fr.almeri.beerboard.models.Region;
import fr.almeri.beerboard.models.Utilisateur;
import fr.almeri.beerboard.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProfileController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/login")
    public String connexion(Model pModel){
        return "login";
    }

//    @PostMapping("/connect-user")
//    public String connectUtilisateur(@ModelAttribute Utilisateur utilisateur, RedirectAttributes redir){
//        if (utilisateurRepository.existsById(utilisateur.getIdUtilisateur())){
//            return "redirect:/index";
//        } else{
//            redir.addFlashAttribute("msg", "Ce compte n'existe pas. Vérifiez votre saisie.");
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/motdepasse")
    public String motdepasse(Model pModel){
        return "motdepasse";
    }

    @GetMapping("/register")
    public String inscription(Model pModel){
        return "register";
    }

    @PostMapping("/valid-user")
    public String addNouvelUtilisateur(@ModelAttribute Utilisateur utilisateur, RedirectAttributes redir){
        System.out.println("toto");
        System.out.println(utilisateur.getIdUtilisateur());
        if (!utilisateurRepository.existsById(utilisateur.getIdUtilisateur())){
            utilisateurRepository.save(utilisateur);
            return "redirect:/login";
        } else{
            redir.addFlashAttribute("msg", "Cet utilisateur existe déjà.");
            return "redirect:/register";
        }
    }
}

package fr.almeri.beerboard.controllers;
import fr.almeri.beerboard.models.Utilisateur;
import fr.almeri.beerboard.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Controller
public class ProfileController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/")
    public String connexion(Model pModel) {
        return "login";
    }

    @PostMapping("/connect-user")
    public String connectUtilisateur(@ModelAttribute Utilisateur utilisateur, HttpSession session, Model model) throws NoSuchAlgorithmException, NoSuchFieldException, NoSuchProviderException {

        // Vérification au niveau de la base de données.
        boolean isOk = this.checkPassword(utilisateur);
        if (isOk) {
            // Démarre une session
            // Création d'une session avec
            // utilisateur connecté
            utilisateur = utilisateurRepository.compteExistant(utilisateur.getEmailUtilisateur());
            session.setAttribute("infoConnexion", utilisateur);
            session.setMaxInactiveInterval(60);
            // Si connexion Ok
            return "redirect:/index";
        } else {
            model.addAttribute("isBad", true);
            return "redirect:/";
        }
    }

    @GetMapping("/motdepasse")
    public String motdepasse(Model pModel) {
        return "motdepasse";
    }

    @PostMapping("/mdp-user")
    public String mdpUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam String emailUtilisateur, RedirectAttributes redir) {
        Utilisateur userConnect = utilisateurRepository.compteExistant(emailUtilisateur);
        if (userConnect != null) {
            if (utilisateur.getEmailUtilisateur().equals(userConnect.getEmailUtilisateur())) {
                redir.addFlashAttribute("msg", "Un mail vous a été envoyé pour réinitialiser votre mot de passe.");
                return "redirect:/";
            }
        }
        redir.addFlashAttribute("msg", "Adresse email incorrect. Vérifiez votre saisie.");
        return "redirect:/motdepasse";
    }

    @GetMapping("/register")
    public String inscription(Model pModel) {
        return "register";
    }

    @PostMapping("/valid-user")
    public String addNouvelUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam String emailUtilisateur, @RequestParam String inputPasswordConfirm, @RequestParam String mdpUtilisateur, Model pModel, RedirectAttributes redir) throws NoSuchAlgorithmException, NoSuchFieldException, NoSuchProviderException {
        if (inputPasswordConfirm.equals(mdpUtilisateur)) {
            if (utilisateurRepository.emailExistant(emailUtilisateur) != null) {
                redir.addFlashAttribute("msg", "Email déjà existant. Veuillez vous connecter en utilisant cet email.");
                return "redirect:/register";
            } else {
                byte[] salt = UserController.getSalt();
                String password = UserController.hashMD5withSalt(utilisateur.getMdpUtilisateur(), salt);
                utilisateur.setMdpUtilisateur(password);
                utilisateur.setSalt(salt);
                utilisateurRepository.save(utilisateur);
                redir.addFlashAttribute("msg", "Le compte a bien été créé. Veuillez vous connecter.");
                return "redirect:/";
            }
        } else {
            redir.addFlashAttribute("msg", "Mots de passe incorrects. Veuillez recommencer.");
            return "redirect:/register";
        }
    }
    private boolean checkPassword(Utilisateur utilisateur) throws NoSuchAlgorithmException, NoSuchFieldException, NoSuchProviderException {
        // On cherche le user en BDD à l'aide de son login
        Utilisateur u = utilisateurRepository.compteExistant(utilisateur.getEmailUtilisateur());
        // Si mot de passe ne correspond pas
        if (u == null)
        {
            return false;
        }
        // Chiffrage du mot de passe saisi par l'utilisateur
        // Pour comparaison
        String newPass = UserController.hashMD5withSalt(utilisateur.getMdpUtilisateur(), u.getSalt());
        // Retourne True si mot de passe saisi == mot de passe de la base de données
        return newPass.equals(u.getMdpUtilisateur());
    }
}

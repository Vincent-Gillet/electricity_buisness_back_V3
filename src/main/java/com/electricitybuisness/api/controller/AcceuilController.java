package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.BorneDTO;
import com.electricitybuisness.api.dto.LieuDTO;
import com.electricitybuisness.api.model.Utilisateur;
import com.electricitybuisness.api.repository.UtilisateurRepository;
import com.electricitybuisness.api.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AcceuilController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/")
    public String acceuil(Model model) {
/*
        ResponseEntity<BorneDTO[]> response = restTemplate.getForEntity("http://localhost:8080/api/bornes", BorneDTO[].class);
        List<BorneDTO> borneList = Arrays.asList(response.getBody());
        model.addAttribute("borneList", borneList);
*/

        ResponseEntity<LieuDTO[]> response = restTemplate.getForEntity("http://localhost:8080/api/lieux", LieuDTO[].class);
        List<LieuDTO> lieuList = Arrays.asList(response.getBody());
        model.addAttribute("lieuList", lieuList);
/*        model.addAttribute("borneList", lieuList.stream()
                .flatMap(lieu -> lieu.getBornes().stream())
                .toList());*/
        return "index";
    }

    @GetMapping("/inscription")
    public String inscription(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult result) {
        if (result.hasErrors()) {
            return "inscription";
        }
        utilisateurService.saveUtilisateur(utilisateur);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());

/*
        ResponseEntity<Map<String, String>> response = restTemplate.postForEntity("http://localhost:8080/api/login", BorneDTO[].class);
*/
        return "login";
    }

/*    @PostMapping("/api/auth/login")
    public String login(@ModelAttribute Utilisateur utilisateur, Model model) {
        return "profil";
    }*/

    @GetMapping("/profil")
    public String profil() {
        return "profil";
    }
}

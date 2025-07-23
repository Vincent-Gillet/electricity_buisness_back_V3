package com.electricitybuisness.api.controller;

import com.electricitybuisness.api.dto.UtilisateurCreateDTO;
import com.electricitybuisness.api.mapper.EntityMapper;
import com.electricitybuisness.api.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class InscriptionController {

    private final UtilisateurService utilisateurService;
    private final EntityMapper mapper;

    @GetMapping("/acceuil-inscription")
    public String showForm(Model model) {
        model.addAttribute("utilisateur", new UtilisateurCreateDTO());
        return "inscription";
    }

    @PostMapping("/acceuil-inscription")
    public String register(@Valid @ModelAttribute("utilisateur") UtilisateurCreateDTO utilisateurDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "inscription";
        }
        utilisateurService.saveUtilisateur(mapper.toEntity(utilisateurDTO));
        return "redirect:/connexion";
    }
}
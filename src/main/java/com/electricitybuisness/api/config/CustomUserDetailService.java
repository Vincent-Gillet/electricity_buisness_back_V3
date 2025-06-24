package com.electricitybuisness.api.config;

import com.electricitybuisness.api.repository.ReparateurRepository;
import com.electricitybuisness.api.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    private final ReparateurRepository reparateurRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmailUtilisateur(username)
                .map(user -> (UserDetails) user)
                .or(() -> reparateurRepository.findByEmailReparateur(username).map(reparateur -> (UserDetails) reparateur))
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
    }
}

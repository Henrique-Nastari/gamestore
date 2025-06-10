package ufrn.br.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ufrn.br.gamestore.model.Usuario;
import ufrn.br.gamestore.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cadusuario")
    public String cadUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadusuario";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login"; // Simplesmente renderiza o arquivo login.html
    }

    @PostMapping("/salvarusuario")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        // Codifica a senha antes de salvar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);
        return "redirect:/login";
    }
}

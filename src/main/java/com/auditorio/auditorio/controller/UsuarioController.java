package com.auditorio.auditorio.controller;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.auditorio.auditorio.annotation.Publico;
import com.auditorio.auditorio.model.Reserva;
import com.auditorio.auditorio.model.Usuario;
import com.auditorio.auditorio.repository.UsuarioRepository;
import com.auditorio.auditorio.util.HashUtil;




@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	

	private Usuario usuario;
	
	@Publico
	@RequestMapping("Inicio")
	public String index() {
		
		return"Index/Index";
	}
	
	
	@Publico
	@RequestMapping("cadastroUsuario")
	public String cadUsuario() {
		return "usuario/cadUsuario";
	}

	@RequestMapping("listarUsuarios/{page}")
	public String listaUsuarios(Model model, @PathVariable("page") int page) {
		
		PageRequest pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.ASC, "nome"));
		
		Page<Usuario> pagina = repository.findAll(pageable);
		
		int totalPages = pagina.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		
		model.addAttribute("usuario", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		
		return "usuario/listaUsuarios";
	}
	
	@RequestMapping(value = "salvarUsuario", method = RequestMethod.POST)
	public String salvarUsuario(Usuario usuario, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os campos!");
			return "redirect:Inicio";
		}
		
		// VERIFICA SE ESTÁ SENDO FEITA UMA ALTERAÇÃO AO INVÉS DE UMA INSERÇÃO
				boolean alteracao = usuario.getIdUsuario() != null ? true : false;
				
				// VERIFICA SE A SENHA ESTÁ VAZIA
				if(usuario.getSenha().equals(HashUtil.hash256(""))) {
					// SE NÃO FOR ALTERAÇÃO, EU DEFINO A PRIMEIRA PARTE DO E-MAIL COMO A SENHA
					if(!alteracao) {
						// EXTRAI A PARTE DO E-MAIL ANTES DO @
						String parte = usuario.getEmail().substring(0, usuario.getEmail().indexOf("@"));
						// DEFINE A SENHA DO ADMIN
						usuario.setSenha(parte);
					}else {
						// BUSCA A SENHA ATUAL
						String hash = repository.findById(usuario.getIdUsuario()).get().getSenha();
						usuario.setSenhaComHash(hash);
					}
				}
		
		try {
			repository.save(usuario);
			attr.addFlashAttribute("mensagemSucesso", "Seja Bem-Vindo(a) " + usuario.getNome() + usuario.getEmail() + usuario.getTelefone()+" ,agora você é um usuário!")
			;
			return "redirect:loginUsuario";
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Usuário: "+e.getMessage());
		}
		
		return "redirect:Inicio";
		
		
	}
	@RequestMapping("perfilUsuario")
	public String perfilUsuario() {
		return"usuario/perfilUsuario";
		
	}
	@RequestMapping("atualizaUsuario")
	public String atualizaUsuario() {
		return"usuario/alterarUsuario";
	}
	
	@RequestMapping("excluirUsuario")
	public String excluir(Long idUsuario, HttpSession session) {
	repository.deleteById(idUsuario);
	
	logout(session);
	
	return "redirect:Inicio";
	}
	
	
	@RequestMapping("alterarUsuario")
	public String alterarUsuario(Model model, Long idUsuario) {
		Usuario user = repository.findById(idUsuario).get();
		model.addAttribute("usuario", user);
		return "forward:atualizaUsuario";
	}
	
	
	
	
	@Publico
	@RequestMapping("loginUsuario")
	public String loginUsuario() {

		return "usuario/usuarioLogin";
	}
	
	
	@Publico
	@RequestMapping("login")
	public String login(Usuario usuarioLogin, RedirectAttributes attr, HttpSession session) {

		
				Usuario usuario = repository.findByEmailAndSenha(usuarioLogin.getEmail(), usuarioLogin.getSenha());
				
				if(usuario == null) {
					
					attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido");
					return "redirect:loginUsuario ";
				}else {
					
					session.setAttribute("usuarioLogado", usuario);
					// DEPOIS ALTERAR O RETURN PARA A PAGINA DE RESERVA
					return "redirect:Inicio ";
				}		
	
		
		
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:Inicio";
	}
}

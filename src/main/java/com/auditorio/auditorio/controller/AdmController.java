package com.auditorio.auditorio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auditorio.auditorio.model.Reserva;
import com.auditorio.auditorio.model.Usuario;
import com.auditorio.auditorio.repository.ReservaRepository;
import com.auditorio.auditorio.repository.UsuarioRepository;



@Controller
public class AdmController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ReservaRepository repositoryreserva;
	
	@RequestMapping("pagLogin")
	public String paglogin() {
		return "adm/paglogin";
	}
	
	@RequestMapping("loginAdm")
	public String loginAdm(String senha, String nif, HttpSession session) {
		
		String admnif = "123";
		String admsenha = "admin";
		
		if(senha.equals(admsenha) && nif.equals(admnif)) {
			session.setAttribute("admLogado", admnif);
			return "redirect:listaEvento/1";
		}
		
		System.out.println("senha incorreta");
		return "redirect:pagLogin";
	}
	
	
	
	@RequestMapping("logoutAdm")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:Inicio";
	}
	
	@RequestMapping("excluirUsuarioAdm")
	public String excluir(Long idUsuario) {
	repository.deleteById(idUsuario);
	return "redirect:listarUsuarios/1";
	}
	
	@RequestMapping("listaEvento/{page}")
	public String listaReservas (Model model, @PathVariable ("page") int page) {
		
		PageRequest pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.ASC, "tituloEvento"));
		
		Page<Reserva> pagina = repositoryreserva.findAll(pageable);
		
		int totalPages = pagina.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		
		model.addAttribute("reserva", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		
				
		return "adm/reservas";
	}
}

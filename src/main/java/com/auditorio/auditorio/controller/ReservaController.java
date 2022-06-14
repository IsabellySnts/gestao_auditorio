package com.auditorio.auditorio.controller;




import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.auditorio.auditorio.model.Reserva;
import com.auditorio.auditorio.model.Usuario;
import com.auditorio.auditorio.repository.RepositoryAlterar;
import com.auditorio.auditorio.repository.ReservaRepository;
import com.auditorio.auditorio.repository.UsuarioRepository;







@Controller
public class ReservaController {

	@Autowired
	ReservaRepository repository;
	
	@Autowired	
	UsuarioRepository usuarioRep;
	
	@Autowired
	RepositoryAlterar alteracao;

	@RequestMapping("formReserva")
	public String formReserva() {
		
		return"reserva/reserva";
	}
	
	@RequestMapping("cadastroReserva")
	public String formReserva(Reserva reserva, RedirectAttributes attr, Usuario usuario) {

	//fazendo busca das datas salvas no banco de dados
	List<Reserva> dataReserva = repository.findByDataEvento(reserva.getDataEvento());
	List<Usuario> idUsuario = usuarioRep.findByIdUsuario(usuario.getIdUsuario());
	

	//verificando se a data está ou não disponivel
	if(dataReserva == null  ) {

		//se nao, salvar a reserva
		repository.save(reserva);
		
		// e retornar para o index
		return "suss/sucesso ";

	}else {

	
		for (Reserva r : dataReserva) {
			 
			 if (r.isManha() && reserva.isManha()) {
				//se nao estiver disponivel, avisar
					attr.addFlashAttribute("dataIndisponivel", "Periodo indisponivel");
					//e retornar para a mesma pagina de cadastro e tentar outra data
					return "redirect:formReserva";
			}else if (r.isTarde() && reserva.isTarde()) {
				//se nao estiver disponivel, avisar
				attr.addFlashAttribute("dataIndisponivel", "Periodo indisponivel");
				//e retornar para a mesma pagina de cadastro e tentar outra data
				return "redirect:formReserva";
			}else if (r.isNoite() && reserva.isNoite()) {
				attr.addFlashAttribute("dataIndisponivel", "Periodo indisponivel");
				//e retornar para a mesma pagina de cadastro e tentar outra data
				return "redirect:formReserva";
			}
		 }
		
		repository.save(reserva);
	
	
		return "suss/sucesso";
	}


	}
	
	@RequestMapping("listaReserva/{page}")
	public String listaReservas (Model model, @PathVariable ("page") int page) {
		
PageRequest pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.ASC, "tituloEvento"));
		
		Page<Reserva> pagina = repository.findAll(pageable);
		
		int totalPages = pagina.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		
		model.addAttribute("reserva", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		
				
				return"reserva/listaReservas";
	}
	@RequestMapping("listaReservaUsuario/{page}")
	public String listaReservaUsuario (Model model, @PathVariable ("page") int page) {
		
		PageRequest pageable = PageRequest.of(page-1, 5, Sort.by(Sort.Direction.ASC, "tituloEvento"));
		
		Page<Reserva> pagina = repository.findAll(pageable);
		
		int totalPages = pagina.getTotalPages();
		
		List<Integer> pageNumbers = new ArrayList<Integer>();
		
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		
		model.addAttribute("reserva", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		
		
				
				return"reserva/listaReservaUsuario";
	}
	@RequestMapping("buscarData") public String buscaData(Reserva dataReserva, Model model, RedirectAttributes att) { 
		List<Reserva> reserva = repository.findByDataEvento(dataReserva.getDataEvento()); 
		model.addAttribute("reserva", reserva); 
		return "reserva/listaReservas"; 
		}
	@RequestMapping("ManualPt1")
	public String manual () {
		
		return "Manual/index.html";
	}
	
	@RequestMapping("manual2")
	public String manualIndex2 () {
		
		return "Manual/index2.html";
	}
	
	@RequestMapping("manual3")
	public String manualIndex3 () {
		
		return "Manual/index3.html";
	}
	
	@RequestMapping("manual4")
	public String manualIndex4 () {
		
		return "Manual/index4.html";
	}
	
	@RequestMapping("manual5")
	public String manualIndex5 () {
		
		return "Manual/index5.html";
	}
	
	@RequestMapping("manual6")
	public String manualIndex6 () {
		
		return "Manual/index6.html";
	}
	
	@RequestMapping("manual7")
	public String manualIndex7 () {
		
		return "Manual/index7.html";
	}
	
	
	@RequestMapping("manual8")
	public String manualIndex8 () {
		
		return "Manual/index8.html";
	}
	
	@RequestMapping("manual9")
	public String manualIndex9 () {
		
		return "Manual/index9.html";
	}
	
	@RequestMapping("manual10")
	public String manualIndex10 () {
		
		return "Manual/index10.html";
	}
	
	@RequestMapping("manual11")
	public String manualIndex11 () {
		
		return "Manual/index11.html";
	}
	
	@RequestMapping("buscarReservas")
	public String verReservas(Model model, Long id) {
	model.addAttribute("reserva", repository.findByUsuarioIdUsuario(id));
	return "adm/reservas";
	}
	
	
	@RequestMapping("excluirReserva")
	public String excluir(Long idReserva, HttpSession session) {
	alteracao.deleteById(idReserva);
	
	
	return "redirect:Inicio";
	}
	
	
	@RequestMapping("alterarReserva")
	public String alterarUsuario(Model model, Long idReserva) {
		Reserva reserva = alteracao.findById(idReserva).get();
		return "forward:formReserva";
	}
	
	
	

	
	
	
		
		
		
	
}
package com.auditorio.auditorio.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.auditorio.auditorio.model.Reserva;




public interface ReservaRepository extends PagingAndSortingRepository<Reserva, Calendar>{

	
	public List<Reserva> findByDataEvento (Calendar dataReserva);

	public List<Reserva> findByUsuarioIdUsuario (Long idUsuario);
	
	
	
	
	

	
	
	
	
}

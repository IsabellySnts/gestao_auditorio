package com.auditorio.auditorio.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idReserva;
	private String tituloEvento;
	private String descricao;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar dataEvento;
	private boolean manha;
	private boolean tarde;
	private boolean noite;
	@ManyToOne
	private Usuario usuario;
	public Long getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}
	public String getTituloEvento() {
		return tituloEvento;
	}
	public void setTituloEvento(String tituloEvento) {
		this.tituloEvento = tituloEvento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Calendar getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Calendar dataEvento) {
		this.dataEvento = dataEvento;
	}
	public boolean isManha() {
		return manha;
	}
	public void setManha(boolean manha) {
		this.manha = manha;
	}
	public boolean isTarde() {
		return tarde;
	}
	public void setTarde(boolean tarde) {
		this.tarde = tarde;
	}
	public boolean isNoite() {
		return noite;
	}
	public void setNoite(boolean noite) {
		this.noite = noite;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}

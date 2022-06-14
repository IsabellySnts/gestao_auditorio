package com.auditorio.auditorio.repository;

import java.util.Calendar;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.auditorio.auditorio.model.Reserva;

public interface RepositoryAlterar extends PagingAndSortingRepository<Reserva, Long> {

}

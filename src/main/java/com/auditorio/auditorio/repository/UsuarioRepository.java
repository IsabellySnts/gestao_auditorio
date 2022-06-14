package com.auditorio.auditorio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.auditorio.auditorio.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{
	
	public Usuario findByEmailAndSenha(String email, String senha);
	
	public List<Usuario> findByIdUsuario (Long id);
	


}

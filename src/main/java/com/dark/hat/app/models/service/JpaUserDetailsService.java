package com.dark.hat.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dark.hat.app.models.dao.IUsuarioDao;
import com.dark.hat.app.models.entity.Role;
import com.dark.hat.app.models.entity.Usuario;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(usuario==null) {
			log.error("Error login: no existe el usuario'".concat(username).concat("'"));
			throw new UsernameNotFoundException("Username ".concat(username).concat(" no existe en el sistema!"));
		}
		
		for (Role role: usuario.getRoles()) {
			log.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			log.error("Error login: usuario'".concat(username).concat("' no tiene roles asignados!"));
			throw new UsernameNotFoundException("Error login: usuario'".concat(username).concat("' no tiene roles asignados!"));
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	
	
}

package com.nguyenhung.bta.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;

import com.nguyenhung.bta.entity.Log;
import com.nguyenhung.bta.entity.User;
import com.nguyenhung.bta.repository.LogRepository;
import com.nguyenhung.bta.repository.UserRepository;
import com.nguyenhung.bta.service.LogService;

@Service
public class LogServiceImpl implements LogService, ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private LogRepository logRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<Log> getAllLogs() {
		return this.logRepo.findAll();
	}

	@Override
	public void removeLog(Long id) {
		this.logRepo.deleteById(id);
	}

	@Override
	public Log addLog(Log log) {
		return this.logRepo.save(log);
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		User currentUser = this.userRepo.findByUsername(event.getAuthentication().getName()).get();
		Log log = new Log(Long.valueOf(0), dtf.format(LocalDateTime.now()), currentUser);
		this.addLog(log);
	}

}

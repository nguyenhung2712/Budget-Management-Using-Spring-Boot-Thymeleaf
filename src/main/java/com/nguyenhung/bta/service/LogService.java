package com.nguyenhung.bta.service;

import java.util.List;

import com.nguyenhung.bta.entity.Log;

public interface LogService {
	List<Log> getAllLogs();
	Log addLog(Log log);
	void removeLog(Long id);
}

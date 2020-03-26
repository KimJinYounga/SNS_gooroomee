package com.gooroomee.api.error.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@Setter
@Table(name = "ERROR_LOGS")
public class ErrorLog {

	@Id
	@Column(name = "ID", precision = 20)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "PHASE", length = 10)
	private String phase;

	@Column(name = "SYSTEM_NAME", length = 50)
	private String systemName;

	@Column(name = "LOGGER_NAME", length = 300)
	private String loggerName;

	@Column(name = "SERVER_NAME", length = 50)
	private String serverName;

	@Column(name = "HOST_NAME", length = 50)
	private String hostName;

	@Column(name = "PATH", length = 2048)
	private String path;

	@Column(name = "MESSAGE", columnDefinition = "TEXT")
	private String message;

	@Column(name = "TRACE_TYPE", columnDefinition = "TEXT")
	private String traceType;

	@Column(name = "ERROR_DATETIME")
	private LocalDateTime errorDatetime = LocalDateTime.now();

	@Column(name = "ALERT_YN", length = 1)
	private String alertYn = "N";

	@Column(name = "HEADER_MAP", columnDefinition = "TEXT")
	private String headerMap;

	@Column(name = "PARAMETER_MAP", columnDefinition = "TEXT")
	private String parameterMap;

	@Column(name = "USER_INFO", columnDefinition = "TEXT")
	private String userInfo;

	@Column(name = "AGENT_DETAIL", columnDefinition = "TEXT")
	private String agentDetail;



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ErrorLog errorLog = (ErrorLog) o;

		if (id != null ? !id.equals(errorLog.id) : errorLog.id != null) return false;
		if (phase != null ? !phase.equals(errorLog.phase) : errorLog.phase != null) return false;
		if (systemName != null ? !systemName.equals(errorLog.systemName) : errorLog.systemName != null) return false;
		if (loggerName != null ? !loggerName.equals(errorLog.loggerName) : errorLog.loggerName != null) return false;
		if (serverName != null ? !serverName.equals(errorLog.serverName) : errorLog.serverName != null) return false;
		if (hostName != null ? !hostName.equals(errorLog.hostName) : errorLog.hostName != null) return false;
		if (path != null ? !path.equals(errorLog.path) : errorLog.path != null) return false;
		if (message != null ? !message.equals(errorLog.message) : errorLog.message != null) return false;
		if (traceType != null ? !traceType.equals(errorLog.traceType) : errorLog.traceType != null) return false;
		if (errorDatetime != null ? !errorDatetime.equals(errorLog.errorDatetime) : errorLog.errorDatetime != null)
			return false;
		if (alertYn != null ? !alertYn.equals(errorLog.alertYn) : errorLog.alertYn != null) return false;
		if (headerMap != null ? !headerMap.equals(errorLog.headerMap) : errorLog.headerMap != null) return false;
		if (parameterMap != null ? !parameterMap.equals(errorLog.parameterMap) : errorLog.parameterMap != null)
			return false;
		if (userInfo != null ? !userInfo.equals(errorLog.userInfo) : errorLog.userInfo != null) return false;
		return !(agentDetail != null ? !agentDetail.equals(errorLog.agentDetail) : errorLog.agentDetail != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (phase != null ? phase.hashCode() : 0);
		result = 31 * result + (systemName != null ? systemName.hashCode() : 0);
		result = 31 * result + (loggerName != null ? loggerName.hashCode() : 0);
		result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
		result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
		result = 31 * result + (path != null ? path.hashCode() : 0);
		result = 31 * result + (message != null ? message.hashCode() : 0);
		result = 31 * result + (traceType != null ? traceType.hashCode() : 0);
		result = 31 * result + (errorDatetime != null ? errorDatetime.hashCode() : 0);
		result = 31 * result + (alertYn != null ? alertYn.hashCode() : 0);
		result = 31 * result + (headerMap != null ? headerMap.hashCode() : 0);
		result = 31 * result + (parameterMap != null ? parameterMap.hashCode() : 0);
		result = 31 * result + (userInfo != null ? userInfo.hashCode() : 0);
		result = 31 * result + (agentDetail != null ? agentDetail.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ErrorLog{" +
				"id=" + id +
				", phase='" + phase + '\'' +
				", system='" + systemName + '\'' +
				", loggerName='" + loggerName + '\'' +
				", serverName='" + serverName + '\'' +
				", hostName='" + hostName + '\'' +
				", path='" + path + '\'' +
				", message='" + message + '\'' +
				", trace='" + traceType + '\'' +
				", errorDatetime=" + errorDatetime +
				", alertYn='" + alertYn + '\'' +
				", headerMap='" + headerMap + '\'' +
				", parameterMap='" + parameterMap + '\'' +
				", userInfo='" + userInfo + '\'' +
				", agentDetail='" + agentDetail + '\'' +
				'}';
	}
}

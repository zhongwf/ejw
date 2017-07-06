create sequence SEQ_EJW_SYSLOG
  start with 1
  increment by 1
  minvalue 1
  nocache
  order;

create table EJW_SYSLOG (
	SEQ number(9) not null,
	LOG_LEVEL varchar2(10) not null,
	LOG_TIME date not null,
	APP varchar2(20),
	MODULE varchar2(20),
	OP_NAME varchar2(20) not null,
	OP_TARGET varchar2(20),
	OP_RESULT varchar2(10),
	OP_CONTENT clob,
	PRIN_ID varchar2(20),
	PRIN_IP varchar2(50),
	PRIN_UA varchar2(512),
	EXTRA clob,
	primary key (SEQ) 
);
comment on table EJW_SYSLOG is 'ejw syslog';

create index IDX_EJW_SYSLOG_LOG_LEVEL on EJW_SYSLOG (LOG_LEVEL);
create index IDX_EJW_SYSLOG_LOG_TIME on EJW_SYSLOG (LOG_TIME);
create index IDX_EJW_SYSLOG_APP_MODULE on EJW_SYSLOG (APP, MODULE);

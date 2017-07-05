create table "EJW_SYSLOG" (
	"SEQ" number(9) not null,
	"LEVEL" varchar2(10) not null,
	"TIME" date not null,
	"APP" varchar2(20),
	"MODULE" varchar2(20),
	"OP_NAME" varchar2(20) not null,
	"OP_RESULT" varchar2(10),
	"OP_CONTENT" clob,
	"PRIN_ID" varchar2(20),
	"PRIN_NAME" varchar2(50),
	"PRIN_IP" varchar2(50),
	"PRIN_UA" varchar2(512),
	"TRG_ID" varchar2(20),
	"TRG_NAME" varchar2(50),
	"EXTRA" clob,
	primary key ("SEQ") 
);
comment on table "EJW_SYSLOG" is 'ejw syslog';

create index IDX_EJW_SYSLOG_LEVEL on EJW_SYSLOG ("LEVEL");
create index IDX_EJW_SYSLOG_TIME on EJW_SYSLOG ("TIME");
create index IDX_EJW_SYSLOG_APP_MODULE on EJW_SYSLOG ("APP", "MODULE");

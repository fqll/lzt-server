/*==============================================================*/
/* Table: company_info                                          */
/*==============================================================*/
drop table if exists company_info;
create table company_info (
   id                   SERIAL not null,
   company_name         VARCHAR(128)            null,
   incumbents_count     INT4                 null,
   credit_code          VARCHAR(64)             null,
   log_url              VARCHAR(256)            null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
    CONSTRAINT "company_info_pkey" PRIMARY KEY ("id")
);

comment on table company_info is '公司表';

comment on column company_info.id is 'id';

comment on column company_info.company_name is '公司名称';

comment on column company_info.incumbents_count is '在职人数';

comment on column company_info.credit_code is '社会统一信用代码';

comment on column company_info.log_url is 'log图片地址';

comment on column company_info.created_id is '创建人ID';

comment on column company_info.last_modify_id is '最后修改人的ID';

comment on column company_info.created_time is '创建时间';

comment on column company_info.last_modify_time is '最后修改时间';

comment on column company_info.version_num is '版本号';

comment on column company_info.deleted is '是否逻辑删除';

comment on column company_info.is_valid is '是否有效';


/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
drop table if exists user_info;
create table user_info (
   id                   SERIAL not null,
   portrait_url         VARCHAR(1024)                  null,
   open_id              VARCHAR(64)             null,
   last_company_id      INT4                 null,
   employee_status      VARCHAR(1)              null,
   entry_company_id     INT4                 null,
   entry_status         VARCHAR(1)              null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "user_info_pkey" PRIMARY KEY ("id")
);
CREATE UNIQUE INDEX uidx_user_info_open_id ON user_info (open_id);
comment on table user_info is '用户表';

comment on column user_info.id is 'id';

comment on column user_info.portrait_url is '微信头像url';

comment on column user_info.open_id is '微信openId';

comment on column user_info.last_company_id is '上家公司ID';

comment on column user_info.employee_status is '雇员状态';

comment on column user_info.entry_company_id is '入职公司ID';

comment on column user_info.entry_status is '入职状态';

comment on column user_info.created_id is '创建人ID';

comment on column user_info.last_modify_id is '最后修改人的ID';

comment on column user_info.created_time is '创建时间';

comment on column user_info.last_modify_time is '最后修改时间';

comment on column user_info.version_num is '版本号';

comment on column user_info.deleted is '是否逻辑删除';

comment on column user_info.is_valid is '是否有效';


/*==============================================================*/
/* Table: user_role_info                                        */
/*==============================================================*/
drop table if exists user_role_info;
create table user_role_info (
   id                   SERIAL not null,
   user_id              INT4                 null,
   company_id           INT4                 null,
   audit_status         VARCHAR(2)           null,
   job_status           VARCHAR(1)           null,
   is_default           VARCHAR(1)           DEFAULT '0',
   nick_name            VARCHAR(64)           null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "user_role_info_pkey" PRIMARY KEY ("id")
);

comment on table user_role_info is '用户角色关联表';

comment on column user_role_info.id is 'id';

comment on column user_role_info.user_id is '用户ID';

comment on column user_role_info.company_id is '公司ID';

comment on column user_role_info.audit_status is '审核状态，0：待审核，1：审核通过';

comment on column user_role_info.job_status is '在职状态，0：在职，1：离职，2：待入职';

COMMENT ON COLUMN user_role_info.is_default IS '是否是主企业，0不是，1是';

COMMENT ON COLUMN user_role_info.nick_name IS '员工昵称';

comment on column user_role_info.created_id is '创建人ID';

comment on column user_role_info.last_modify_id is '最后修改人的ID';

comment on column user_role_info.created_time is '创建时间';

comment on column user_role_info.last_modify_time is '最后修改时间';

comment on column user_role_info.version_num is '版本号';

comment on column user_role_info.deleted is '是否逻辑删除';

comment on column user_role_info.is_valid is '是否有效';


/*==============================================================*/
/* Table: role_info                                             */
/*==============================================================*/
drop table if exists role_info;
create table role_info (
   id                   SERIAL not null,
   role_name            VARCHAR(64)             null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "role_info_pkey" PRIMARY KEY ("id")
);

comment on table role_info is '角色表';

comment on column role_info.id is 'id';

comment on column role_info.role_name is '角色名称';

comment on column role_info.created_id is '创建人ID';

comment on column role_info.last_modify_id is '最后修改人的ID';

comment on column role_info.created_time is '创建时间';

comment on column role_info.last_modify_time is '最后修改时间';

comment on column role_info.version_num is '版本号';

comment on column role_info.deleted is '是否逻辑删除';

comment on column role_info.is_valid is '是否有效';


/*==============================================================*/
/* Table: departure_info                                        */
/*==============================================================*/
drop table if exists departure_info;
create table departure_info (
   id                   SERIAL not null,
   company_id           INT4                 null,
   employee_id          INT4                 null,
   audit_status         VARCHAR(1)              null,
   audit_user_id        INT4                 null,
   departure_reason     VARCHAR(64)             null,
   official_departure_reason VARCHAR(64)             null,
   personal_departure_reason VARCHAR(64)             null,
   employee_name        VARCHAR(64)             null,
   gender               VARCHAR(1)              null,
   id_card_no           VARCHAR(32)             null,
   department           VARCHAR(64)             null,
   employee_post        VARCHAR(64)             null,
   entry_date           VARCHAR(32)             null,
   submit_date          VARCHAR(32)             null,
   departure_date       VARCHAR(32)             null,
   official_evaluate    VARCHAR(1024)           null,
   personal_evaluate    VARCHAR(1024)           null,
   is_check             varchar(1)            DEFAULT '0',
   code                 VARCHAR(64)             null,
   remark               VARCHAR(1024)             null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "departure_info_pkey" PRIMARY KEY ("id")
);

comment on table departure_info is '离职信息';

comment on column departure_info.id is 'id';

comment on column departure_info.company_id is '公司ID';

comment on column departure_info.employee_id is '雇员ID';

comment on column departure_info.audit_status is '当前审批状态';

comment on column departure_info.audit_user_id is '当前审批人ID';

COMMENT ON COLUMN departure_info.departure_reason IS '离职原因，0：公司原因，1：个人原因';

comment on column departure_info.official_departure_reason is '官方离职原因';

comment on column departure_info.personal_departure_reason is '个人离职原因';

comment on column departure_info.employee_name is '姓名';

comment on column departure_info.gender is '性别';

comment on column departure_info.id_card_no is '身份证号码';

comment on column departure_info.department is '所属部门';

comment on column departure_info.employee_post is '岗位';

comment on column departure_info.entry_date is '入职时间';

COMMENT ON COLUMN departure_info.submit_date IS '提出离职的时间';

comment on column departure_info.departure_date is '离职时间';

comment on column departure_info.official_evaluate is '公司评价';

comment on column departure_info.personal_evaluate is '个人评价';

COMMENT ON COLUMN departure_info.is_check IS '是否核验，0：未核验，1：已核验';

COMMENT ON COLUMN departure_info.code IS '核验码';

COMMENT ON COLUMN departure_info.remark IS '离职备注';

comment on column departure_info.created_id is '创建人ID';

comment on column departure_info.last_modify_id is '最后修改人的ID';

comment on column departure_info.created_time is '创建时间';

comment on column departure_info.last_modify_time is '最后修改时间';

comment on column departure_info.version_num is '版本号';

comment on column departure_info.deleted is '是否逻辑删除';

comment on column departure_info.is_valid is '是否有效';


/*==============================================================*/
/* Table: departure_audit                                       */
/*==============================================================*/
drop table if exists departure_audit;
create table departure_audit (
   id                   SERIAL not null,
   departure_id         INT4                 null,
   user_id              INT4                 null,
   audit_role_type      VARCHAR(1)           null,
   operate_type         VARCHAR(1)           null,
   audit_order          INT4                 null,
   audit_result         VARCHAR(1)           null,
   audit_opinions       VARCHAR(1024)        null,
   read_status          VARCHAR(1)           DEFAULT '0',
   follow_status        VARCHAR(1)           DEFAULT '0',
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "departure_audit_pkey" PRIMARY KEY ("id")
);

comment on table departure_audit is '离职审批表';

comment on column departure_audit.id is 'id';

comment on column departure_audit.departure_id is '离职表单ID';

comment on column departure_audit.user_id is '用户ID';

comment on column departure_audit.audit_role_type is '审批角色类型：0：抄送人，1：审批人，2：发起人，3：撤回人';

comment on column departure_audit.operate_type is '操作类型，0：待审批，1：审批中，2：同意离职，3：拒绝离职';

comment on column departure_audit.audit_order is '审批顺序';

comment on column departure_audit.audit_result is '审批结果，0：不通过，1：通过';

comment on column departure_audit.audit_opinions is '审批意见';

comment on column departure_audit.read_status is '已读状态：0：未读，1：已读';

comment on column departure_audit.follow_status is '是否关注：0：未关注，1：关注';

comment on column departure_audit.created_id is '创建人ID';

comment on column departure_audit.last_modify_id is '最后修改人的ID';

comment on column departure_audit.created_time is '创建时间';

comment on column departure_audit.last_modify_time is '最后修改时间';

comment on column departure_audit.version_num is '版本号';

comment on column departure_audit.deleted is '是否逻辑删除';

comment on column departure_audit.is_valid is '是否有效';


/*==============================================================*/
/* Table: chat_log                                       */
/*==============================================================*/
drop table if exists chat_log;
create table chat_log (
   id                   SERIAL not null,
   departure_id         INT4                 null,
   pre_user_id          INT4                 null,
   suffix_user_id       INT4                 null,
   chat_content         VARCHAR(1024)        null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "chat_log_pkey" PRIMARY KEY ("id")
);

comment on table chat_log is '聊天记录表';

comment on column chat_log.id is 'id';

comment on column chat_log.departure_id is '离职表单ID';

comment on column chat_log.pre_user_id is '前公司关注人ID';

comment on column chat_log.suffix_user_id is '待入职关注人ID';

comment on column chat_log.chat_content is '聊天内容';

comment on column chat_log.created_id is '创建人ID';

comment on column chat_log.last_modify_id is '最后修改人的ID';

comment on column chat_log.created_time is '创建时间';

comment on column chat_log.last_modify_time is '最后修改时间';

comment on column chat_log.version_num is '版本号';

comment on column chat_log.deleted is '是否逻辑删除';

comment on column chat_log.is_valid is '是否有效';

INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (1, '老板', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (2, '人事', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (3, '总监', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (4, '经理', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (5, '员工', NULL, NULL, NULL, NULL, NULL, NULL, NULL);


/*==============================================================*/
/* Table: authority_info                                        */
/*==============================================================*/
drop table if exists authority_info;
create table authority_info (
   id                   SERIAL not null,
   authority_code       VARCHAR(50)       null,
   authority_name       VARCHAR(100)      null,
   status               VARCHAR(1)        null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "authority_info_pkey" PRIMARY KEY ("id")
);

comment on table authority_info is '离职审批表';

comment on column authority_info.id is 'id';

comment on column authority_info.authority_code is '权限编码';

comment on column authority_info.authority_name is '权限名称';

comment on column authority_info.status is '状态，0：无效，1：有效';

comment on column authority_info.created_id is '创建人ID';

comment on column authority_info.last_modify_id is '最后修改人的ID';

comment on column authority_info.created_time is '创建时间';

comment on column authority_info.last_modify_time is '最后修改时间';

comment on column authority_info.version_num is '版本号';

comment on column authority_info.deleted is '是否逻辑删除';

comment on column authority_info.is_valid is '是否有效';

INSERT INTO "public"."authority_info"("id", "authority_code", "authority_name", "status", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (1, 'create_departure', '开具离职证明', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."authority_info"("id", "authority_code", "authority_name", "status", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (2, 'my_approval', '我的审批', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."authority_info"("id", "authority_code", "authority_name", "status", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (5, 'analysis_departure', '离职分析', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."authority_info"("id", "authority_code", "authority_name", "status", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (8, 'handle_entry', '入职办理', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."authority_info"("id", "authority_code", "authority_name", "status", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (9, 'investigation', '背景调查', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."authority_info"("id", "authority_code", "authority_name", "status", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (10, 'add_approver', '添加审批人', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

/*==============================================================*/
/* Table: role_authority_info                                   */
/*==============================================================*/
drop table if exists role_authority_info;
create table role_authority_info (
   id                   SERIAL not null,
   role_id              INT4                 null,
   authority_id         INT4                 null,
   company_id           INT4                 null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "role_authority_info_pkey" PRIMARY KEY ("id")
);

comment on table role_authority_info is '离职审批表';

comment on column role_authority_info.id is 'id';

comment on column role_authority_info.role_id is '角色ID';

comment on column role_authority_info.authority_id is '权限ID';

comment on column role_authority_info.company_id is '公司ID';

comment on column role_authority_info.created_id is '创建人ID';

comment on column role_authority_info.last_modify_id is '最后修改人的ID';

comment on column role_authority_info.created_time is '创建时间';

comment on column role_authority_info.last_modify_time is '最后修改时间';

comment on column role_authority_info.version_num is '版本号';

comment on column role_authority_info.deleted is '是否逻辑删除';

comment on column role_authority_info.is_valid is '是否有效';

--老板权限
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 1, 1, 1 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 2, 1, 2 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 3, 1, 3);
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 4, 1, 4 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 5, 1, 5 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 6, 1, 6 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 7, 1, 7 );
--HR权限
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 8, 2, 1 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 9, 2, 2 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 10, 2, 3 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 11, 2, 4 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 12, 2, 6 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 13, 2, 7 );
--总监权限
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 14, 3, 2 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 15, 3, 4 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 16, 3, 7 );
--经理权限
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 17, 4, 2 );
INSERT INTO "public"."role_authority_info" ( "id", "role_id", "authority_id" ) VALUES	( 18, 4, 7 );


/*==============================================================*/
/* Table: user_authority_info                                   */
/*==============================================================*/
drop table if exists user_authority_info;
create table user_authority_info (
   id                   SERIAL not null,
   user_id              INT4                 null,
   company_id           INT4                 null,
   authority_id         INT4                 null,
   created_id           INT4                 null,
   last_modify_id       INT4                 null,
   created_time         TIMESTAMP            null,
   last_modify_time     TIMESTAMP            null,
   version_num          INT4                 null,
   deleted              VARCHAR(1)              null,
   is_valid            VARCHAR(1)              null,
   CONSTRAINT "user_authority_info_pkey" PRIMARY KEY ("id")
);

comment on table user_authority_info is '离职审批表';

comment on column user_authority_info.id is 'id';

comment on column user_authority_info.user_id is '用户ID';

comment on column user_authority_info.company_id is '公司ID';

comment on column user_authority_info.authority_id is '权限ID';

comment on column user_authority_info.created_id is '创建人ID';

comment on column user_authority_info.last_modify_id is '最后修改人的ID';

comment on column user_authority_info.created_time is '创建时间';

comment on column user_authority_info.last_modify_time is '最后修改时间';

comment on column user_authority_info.version_num is '版本号';

comment on column user_authority_info.deleted is '是否逻辑删除';

comment on column user_authority_info.is_valid is '是否有效';


--20191129版本更新
ALTER TABLE "public"."user_role_info"
  ADD COLUMN "email_address" varchar(64),
  ADD COLUMN "email_password" varchar(255);
COMMENT ON COLUMN "public"."user_role_info"."email_address" IS '电子邮箱地址';
COMMENT ON COLUMN "public"."user_role_info"."email_password" IS '邮箱密码或者授权码';

--20191213更新
ALTER TABLE "public"."company_info"
ADD COLUMN "process_mode" varchar(1) DEFAULT '0';
COMMENT ON COLUMN "public"."company_info"."process_mode" IS '运营模式，0：线下模式，1：线上模式';

ALTER TABLE "public"."user_info"
ADD COLUMN "nick_name" varchar(64);
COMMENT ON COLUMN "public"."user_info"."nick_name" IS '微信昵称';

ALTER TABLE "public"."departure_info"
ADD COLUMN "check_stage" varchar(1) DEFAULT '0';
COMMENT ON COLUMN "public"."departure_info"."check_stage" IS '背调阶段，0：未开始背调，1：开始聊天背调';
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

--初始化角色表数据
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (1, '老板', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (2, '人事', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (3, '总监', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (4, '经理', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "public"."role_info"("id", "role_name", "created_id", "last_modify_id", "created_time", "last_modify_time", "version_num", "deleted", "is_valid") VALUES (5, '员工', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

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
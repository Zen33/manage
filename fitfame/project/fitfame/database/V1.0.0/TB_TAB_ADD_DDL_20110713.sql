/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   uid                  varchar(64) not null unique comment '用户id',
   pw                   varchar(32) not null comment '用户密码',
   tel                  varchar(12) not null unique comment '用户电话',
   sex                  varchar(6) not null comment '用户性别',
   username             varchar(32) not null comment '用户昵称',
   icon                 varchar(512) not null comment '头像地址',
   primary key (uid)
);

/*==============================================================*/
/* Table: user_state_info                                       */
/*==============================================================*/
create table user_state_info
(
   uid                  varchar(64) not null unique comment '用户id',
   level                int comment '等级',
   score                int comment '积分',
   match                int comment '拼购数',
   wr                   float comment '胜率',
   primary key (uid)
);

/*==============================================================*/
/* Table: address_info                                       */
/*==============================================================*/
create table address_info
(
   id                   int  primary key not  null auto_increment,
   uid                  varchar(64) not null comment '用户id',
   province             varchar(12) not null comment '省',
   city                 varchar(12) not null comment '市',
   address              varchar(128) not null comment '地址',
   zip                  varchar(12)  not null comment '邮政编码',
   tel                  varchar(12) not null comment '用户电话'
);

/*==============================================================*/
/* Table: ad_info                                       */
/*==============================================================*/
create table ad_info
(
   type            int comment '广告类型',
   pic             varchar(512) not null comment '图片地址',
   id              int comment '商品或悬赏大赛id'
);

/*==============================================================*/
/* Table: goods_comments                                       */
/*==============================================================*/
create table goods_comments
(
   id              int  primary key not  null auto_increment,
   gid             int comment '商品id',
   uid             varchar(64) not null comment '用户id',
   content         varchar(128) not null comment '评论内容',
   score             int comment '评分',
   date             int comment '日期',
   check             int comment '审核'
);

/*==============================================================*/
/* Table: goods_free_info                                       */
/*==============================================================*/
create table goods_free_info
(
   gid             int comment '商品id',
   spreadrebate    int comment '每用户推广返还乐币',
   buyrebate       int comment '每用户购买返还',
   wantedrebate    float comment '每次拼购获胜返还',
   primary key (gid)
);


/*==============================================================*/
/* Table: goods_info                                       */
/*==============================================================*/
create table goods_info
(
   id              int  primary key not  null auto_increment,
   name            varchar(64) not null comment '商品名称',
   orgprice        float comment '原价',
   price           float comment '现价',
   classes         varchar(24) not null comment '分类',
   onsale          int comment '是否上架',
   detail          varchar(4098) not null comment '详细信息'
);

/*==============================================================*/
/* Table: goods_pic                                      */
/*==============================================================*/
create table goods_pic
(
   id              int comment '商品id',
   pic             varchar(512) not null comment '图片地址',
);

/*==============================================================*/
/* Table: home_page_goods                                       */
/*==============================================================*/
create table home_page_goods
(
   id              int  comment '商品id',
   pic             varchar(512) not null comment '图片地址',
   size            int comment '图片大小',
   primary key (id)
);

/*==============================================================*/
/* Table: order_goods                                       */
/*==============================================================*/
create table order_goods
(
   id              int  primary key not  null auto_increment,
   oid             int comment '订单id',
   gid             int comment '商品id',
   sid             int comment '种类id',
   size            varchar(24) not null comment '尺寸',
   round           int comment '战斗轮数',
   end             int comment '是否结束',
   proc            varchar(512) not null comment '战斗过程'
);


/*==============================================================*/
/* Table: order_info                                      */
/*==============================================================*/
create table order_info
(
   id              int  primary key not  null auto_increment,
   uid             varchar(64) not null comment '用户id',
   type            int comment '订单类型',
   aid             int comment '收货地址',
   trackno         varchar(24)  comment '物流单号',
   carrier         varchar(24)  comment '物流公司',
   state           int comment '状态',
   payaccount      varchar(24)  comment '付款账号',
   payway          varchar(24)  comment '付款方式',
   sdate           int comment '开始时间',
   pdate           int comment '付款时间',
   amount          int comment '付款金额',
   bdate           int comment '红条返还时间'
);

/*==============================================================*/
/* Table: question_and_answer                                      */
/*==============================================================*/
create table question_and_answer
(
   id              int  primary key not  null auto_increment,
   question        varchar(128) not null comment '问题',
   url             varchar(512)  comment '问题图片地址',
   a1              varchar(512)  comment '答案1',
   a2              varchar(512)  comment '答案2',
   a3              varchar(512)  comment '答案3',
   a4              varchar(512)  comment '答案4',
   a5              varchar(512)  comment '答案5',
   a6              varchar(512)  comment '答案6',
   right           varchar(8)  comment '正确答案',
   type            int comment '问题归类'
);


/*==============================================================*/
/* Table: question_class                                      */
/*==============================================================*/
create table question_class
(
   id              int  primary key not  null auto_increment,
   fid             int comment '父id',
   name            varchar(24)  comment '类别名字'
);


/*==============================================================*/
/* Table: rank_info                                      */
/*==============================================================*/
create table rank_info
(
   wid             int comment '悬赏大赛id',
   uid             varchar(64) not null comment '用户id',
   ranking         int comment '用户积分'
);

/*==============================================================*/
/* Table: sort_desc                                      */
/*==============================================================*/
create table sort_desc
(
   sid             int comment '品种id',
   size            varchar(24) not null comment '尺寸',
   count           int comment '剩余数量',
);


/*==============================================================*/
/* Table: sort_info                                      */
/*==============================================================*/
create table sort_info
(
   sid             int  primary key not  null auto_increment,
   id              int comment '商品id',
   name            varchar(24) not null comment '品种名',
   pic             varchar(512) not null comment '图片地址'
);


/*==============================================================*/
/* Table: wanted_info                                      */
/*==============================================================*/
create table wanted_info
(
   id              int  primary key not  null auto_increment,
   name            varchar(24) not null comment '品种名',
   start           int comment '开始时间',
   end             int comment '结束时间',
   desc            varchar(1024) not null comment '详细描述'
);
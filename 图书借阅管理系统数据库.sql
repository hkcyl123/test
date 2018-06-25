create database ks
on
( 
  name=ks_data,
  filename='D:\Users\admin\Desktop\城院\课程设计\数据库\ksdata.mdf',
  size=10,
  maxsize=50,
  filegrowth=5
)
log on
( 
  name=ks_log,
  filename='D:\Users\admin\Desktop\城院\课程设计\数据库\ksdata.ldf',
  size=5,
  maxsize=25,
  filegrowth=5
)
use ks
create table administrator(
  MID nchar(12) not null constraint admin_pk primary key,
  Mname nchar(5) not null,
  Mpsw varchar(16) not null,
  Mteleph char(11) not null,
)
create table bookclass(
  BCNo char(2) not null constraint bc_pk primary key,
  BCname nchar(5) not null,
)
create table bookpublish(
  BPNo char(5) not null constraint bp_pk primary key,
  BPname nvarchar(20) not null,
  BPteleph char(20) not null,
  BPaddr nvarchar(30) not null,
)
create table book(
  ISBN char(13) not null constraint book_pk primary key,
  bookname nchar(10) not null,
  bookwriter nchar(10) not null,
  BPNo char(5) not null constraint book_fk1 foreign key references bookpublish(BPNo),
  bookprice decimal(5,2),
  BCNo char(2) constraint book_fk2 foreign key references bookclass(BCNo),
  bookmain varchar(200),
  bookprim nchar(20),
  bookstate nchar(1) not null,
)
create table readclass(
  RCNo char(2) not null constraint rc_pk primary key,
  RCName nchar(4) not null,
  RCMaxBook smallint not null,
  RCMAXTime nchar(3) not null,
)
create table reader(
  readNo char(10) not null constraint reader_pk primary key,
  readSex nchar(1) not null,
  readID char(17) not null,
  readCompany nvarchar(30),
  readAddr nvarchar(30) not null,
  readcont char(11) not null,
  RCNo char(2) not null constraint reader_fk foreign key references readclass(RCNo),
)
create table bookno(
  bookNo char(10) not null constraint bookno_pk primary key,
  ISBN char(13) not null constraint b_fk1 foreign key references book(ISBN),
  )
create table information(
  readNo char(10) not null constraint in_fk1 foreign key references reader(readNo),
  bookNo char(10) not null constraint in_fk2 foreign key references bookno(bookNo),
  Mname nchar(12) not null constraint in_fk3 foreign key references administrator(MID),
  outdate smalldatetime not null,
  indate smalldatetime not null,
  Tindate smalldatetime,
  Clstate nchar(3),
  penalty decimal(5,2),
  constraint in_pk primary key(readNo,bookNo,Mname)
)


insert into administrator(MID,Mname,Mpsw,Mteleph)
values ('201535020209','黄凯成','hkcyl1','12345678901')
insert into administrator(MID,Mname,Mpsw,Mteleph)
values ('201535020244','祝祯浩','zzh960804','12345678901')
insert into readclass(RCNo,RCName,RCMaxBook,RCMAXTime)
values ('01','普通','2','2个月')
insert into readclass(RCNo,RCName,RCMaxBook,RCMAXTime)
values ('02','会员','4','4个月')

go
create view view_2(ISBN,bookNo,bookname,bookwriter,BPname,bookprice,BCname,bookmain,bookprim,bookstate)
as select bookno.ISBN,bookname,bookwriter,bookpublish.BPNo,book.BPNo,bookprice,bookclass.BCNo,book.BCNo,bookmain,bookprim,bookstate,bookNo
from book,bookno,bookclass,bookpublish
where bookno.ISBN=book.ISBN and book.BCNo = bookclass.BCNo and book.BPNo = bookpublish.BPNo
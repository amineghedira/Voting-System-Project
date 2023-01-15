
create user VotingSystem with password 'password' ;
create database VotingSystemDB with template=template0 owner=VotingSystem ;
\connect VotingSystemDB ;
alter default privileges grant all on tables to VotingSystem ;
alter default privileges grant all on sequences to VotingSystem ;

create table admin(
adminID integer primary key not null,
adminName varchar(40) not null,
password text not null
);

create table resident(
CIN integer primary key not null,
fullName varchar(40) not null,
DOB varchar(10) not null,
gender char not null
);

create table users(
userID integer primary key not null,
CIN integer not null,
email varchar(30) not null,
password text not null
);
alter table users add constraint users_fk
foreign key (CIN) references resident(CIN) ON DELETE CASCADE;

create table voting_period(
VP_ID integer primary key not null,
DateTime varchar(20) not null,
prop1 text not null,
prop2 text not null,
prop3 text,
prop4 text
);


create table voting_history(
userID integer not null,
VP_ID integer not null,
vote varchar(5) not null,
primary key(userID, VP_ID)
);
alter table voting_history add constraint VH_FK1
foreign key (userID) references users(userID) ON DELETE CASCADE;
alter table voting_history add constraint VH_FK2
foreign key (VP_ID) references voting_period(VP_ID);

create table results(
VP_ID integer primary key not null,
prop1 int not null,
prop2 int not null,
prop3 int,
prop4 int
);
alter table results add constraint results_fk
foreign key (VP_ID) references voting_period(VP_ID);


create sequence adminID_seq increment 1 start 1;
create sequence resident_seq increment 1 start 1;
create sequence userID_seq increment 1 start 1;
create sequence VP_ID_seq increment 1 start 1;

create database test2
use test2

alter table account_opening_form alter column dob date null
alter table account_opening_form alter column aadharnumber bigint 

insert into account_opening_form values
(default,'savings','sarthak','2025-11-11',1234,9650,0.00,'chandigarh','pending');


insert into account_opening_form values
(default,'savings','rahul','2024-11-11',7896,9650,0.00,'chandigarh','pending');

create table account_opening_form (
   [date] date default getdate(),
   account_type varchar(20) default 'savings',
   account_holdername varchar(30),
   dob DATE,
   aadharnumber int not null unique,
   mobilenumber int not null,
   accountopeningbalance decimal(10,2),
   fulladdress varchar(100),
   kyc varchar(20) default 'pending'
);

create table bank (
   accountNumber bigint identity(10000000,1),
   account_type varchar(20),
   account_opening_date date default getdate(),
   currentbalance decimal(10,2)
);

create table accountholderdetails (
   accountNumber bigint,
   account_holdername varchar(20),
   dob date,
   aadharnumber int,
   mobilenumber int,
   fulladdress varchar(100)
);


-- TRIGGER TO UPDATE BANK AND ACCOUNTHOLDERDETAILS TABLES AFTER APPROVAL OF KYC IN ACCOUNT_OPENING_FORM TABLE
create trigger trg_after_kyc_approval on account_opening_form
after update
as
begin
declare @AccountNumber int
insert into bank (account_type,currentbalance)
select account_type,accountopeningbalance from inserted where kyc='approved'

set @AccountNumber = SCOPE_IDENTITY();
insert into accountholderdetails (accountNumber,account_holdername,dob,aadharnumber,mobilenumber,fulladdress)
select @AccountNumber,account_holdername,dob,aadharnumber,mobilenumber,fulladdress from inserted where kyc='approved';
end;


UPDATE account_opening_form
SET KYC = 'approved'
WHERE aadharnumber = 7896;
SELECT * FROM account_opening_form
SELECT * FROM bank
SELECT * FROM accountholderdetails



create table transactiondetails (
     accountnumber bigint,
     paymenttype varchar(20),
     transactionamount decimal(10,2),
     dateoftransaction date default getdate()
);

select * from bank
select * from accountholderdetails
select * from transactiondetails
select * from account_opening_form

alter table bank add constraint pk_bank primary key (accountNumber)

alter table accountholderdetails add constraint fk_accountholderdetails foreign key (accountNumber) references bank(accountNumber)

alter table transactiondetails add constraint fk_transactiondetails foreign key (accountnumber) references bank(accountNumber)

-- TRIGGER TO UPDATE BANK TABLE AFTER INSERTING INTO TRANSACTIONDETAILS TABLE

create trigger trg_after_transaction on transactiondetails
after insert
as
begin

    update b
    set b.currentbalance = b.currentbalance + t.transactionamount
    from bank b
    join inserted t on b.accountNumber = t.accountnumber
    where t.paymenttype = 'credit';

    update b 
    set b.currentbalance = b.currentbalance - t.transactionamount
    from bank b
    join inserted t on b.accountNumber = t.accountnumber
    where t.paymenttype = 'debit';

end;

insert into transactiondetails values (10000001,'credit',5000.00,'2025-12-01');

select * from bank
select * from transactiondetails

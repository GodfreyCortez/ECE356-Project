-- drop tables
drop table if exists History;
drop table if exists News;
drop table if exists IPO;
drop table if exists Indicators;
drop table if exists Stocks;

-- create Stocks table and load it in the rest of the script
select '----------------------------------------------------------------' as '';
select 'Stocks' as '';
create table Stocks (
	symbol char(15) not null,
    primary key (symbol)
);

-- create History table
select '----------------------------------------------------------------' as '';
select 'History' as '';
create table History (
	date date not null,
	symbol char(15) not null,
	open decimal(21, 16) not null,
	high decimal(21, 16) not null,
	low decimal(21, 16) not null,
	close decimal(21, 16) not null,
	adj_close decimal(21, 16) not null,
	volume decimal(21, 16) not null,
    primary key (date, symbol)
);

-- load the History table
load data infile '/var/lib/mysql-files/18-Stocks/fh_5yrs.csv' ignore into table History
     fields terminated by ','
     -- enclosed by '"'
     lines terminated by '\n'
     ignore 1 lines
     (date, volume, open, high, low, close, adj_close, symbol);

-- insert into Stocks table
insert into Stocks select distinct symbol from History;
-- add the foreign key (need to do after making sure Stocks table has the stock)
alter table History add constraint fk_History_Stock foreign key (symbol) references Stocks(symbol);

-- create News table
select '----------------------------------------------------------------' as '';
select 'News' as '';
create table News (
	date date not null,
    headline varchar(255) not null,
    publisher varchar(255) not null,
    url varchar(255) not null,
    symbol char(15) not null,
    primary key  (date, headline)
);	

-- load News table with Benzinga data
load data infile '/var/lib/mysql-files/18-Stocks/raw_analyst_ratings.csv' ignore into table News
     fields terminated by ','
     optionally enclosed by '"' escaped by '\b'
     lines terminated by '\n'
     ignore 1 lines
     (@ignore, headline, url, publisher, date, symbol);

-- load News table with Benzinga partner data
load data infile '/var/lib/mysql-files/18-Stocks/raw_partner_headlines.csv' ignore into table News
     fields terminated by ','
     optionally enclosed by '"' escaped by '\b'
     lines terminated by '\n'
     ignore 1 lines
     (@ignore, headline, url, publisher, date, symbol);

-- insert into Stocks table if stock doesn't exist already
insert into Stocks select distinct t1.symbol from News t1 where not exists ( select symbol from Stocks t2 where t2.symbol = t1.symbol );
-- add the foreign key (need to do after making sure Stocks table has the stock)
alter table News add constraint fk_News_Stock foreign key (symbol) references Stocks(symbol);

-- create IPO table
select '----------------------------------------------------------------' as '';
select 'IPO' as '';
create table IPO (
	date date not null,
	symbol char(15) not null,
	open decimal(21, 16) not null,
	high decimal(21, 16) not null,
	low decimal(21, 16) not null,
	close decimal(21, 16) not null,
	volume decimal(21, 16) not null,
    primary key (date, symbol)
);

-- TO DO: LOAD THE IPO TABLE
-- TO DO: ADD FK REFERENCING STOCKS TABLE

-- create Indicators table
select '----------------------------------------------------------------' as '';
select 'Indicators' as '';
create table Indicators (
    year int not null,
	symbol char(15) not null,
	eps decimal(21, 16) not null,
	revenue decimal(21, 16) not null,
	cost_of_revenue decimal(21, 16) not null,
	gross_profit decimal(21, 16) not null,
	pe_ratio decimal(21, 16) not null,
	debt_ratio decimal(21, 16) not null,
    dividend_yield decimal(21, 16) not null,
    dividend_payout_ratio decimal(21, 16) not null,
    pb_value_ratio decimal(21, 16) not null,
    pe_growth_ratio decimal(21, 16) not null,
    primary key (year, symbol)
);

-- TO DO: LOAD THE INDICATORS TABLE
-- TO DO: ADD FK REFERENCING STOCKS TABLE
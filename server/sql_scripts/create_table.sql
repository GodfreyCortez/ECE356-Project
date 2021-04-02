-- Clean out the old teefile; likely this command is system specific
\! rm -f project-outfile.txt
tee project-outfile.txt;

-- Show warnings after every statement
warnings;

-- drop tables
drop view if exists Publisher;
drop table if exists History;
drop table if exists News;
drop table if exists IPO;
drop table if exists Indicator;
drop table if exists Comment;
drop table if exists Stock;
drop table if exists Sector;

-- create Sector table and load it in the rest of the script
select '----------------------------------------------------------------' as '';
select 'Sector' as '';
create table Sector (
	sector char(25) not null,
    primary key (sector)
);

-- create Stock table and load it in the rest of the script
select '----------------------------------------------------------------' as '';
select 'Stock' as '';
create table Stock (
	symbol char(5) not null,
    sector char(25),
    primary key (symbol)
);

-- create Indicator table
select '----------------------------------------------------------------' as '';
select 'Indicator' as '';
create table Indicator (
    year decimal(4) not null,
     symbol char(5) not null,
     eps decimal(13, 4),
     revenue bigint,
     costOfRevenue bigint,
     grossProfit bigint,
     peRatio decimal(10, 4),
     debtRatio decimal(8, 4),
    dividendYield decimal(12, 9),
    dividendPayoutRatio decimal(12, 9),
    pbValueRatio decimal(13, 4),
    pegRatio decimal(15, 9),
    epsGrowth decimal(8, 4),
    sector char(25) not null,
    priceVar decimal(16, 9) not null,
    primary key (year, symbol)
);

-- load the 2014 Indicator data into Indicator table
load data infile '/var/lib/mysql-files/18-Stocks/Project37/2014_Financial_Data_Clean.csv' ignore into table Indicator
     fields terminated by ','
     lines terminated by '\n'
     ignore 1 lines
     (symbol, @revenue, @costOfRevenue, @grossProfit, @eps, @pbValueRatio, @peRatio, @pegRatio, @dividendYield, @debtRatio, @dividendPayoutRatio, @epsGrowth, sector, priceVar)
     set  year = 2014,
          revenue = nullif(@revenue, ''),
          costOfRevenue = nullif(@costOfRevenue, ''),
          grossProfit = nullif(@grossProfit, ''),
          eps = nullif(@eps, ''),
          pbValueRatio = nullif(@pbValueRatio,''),
          peRatio = nullif(@peRatio,''),
          pegRatio = nullif(@pegRatio,''),
          dividendYield = nullif(@dividendYield,''),
          debtRatio = nullif(@debtRatio,''),
          dividendPayoutRatio = nullif(@dividendPayoutRatio,''),
          epsGrowth = nullif(@epsGrowth,'');

-- load the 2015 Indicator data into Indicator table
load data infile '/var/lib/mysql-files/18-Stocks/Project37/2015_Financial_Data_Clean.csv' ignore into table Indicator
     fields terminated by ','
     lines terminated by '\n'
     ignore 1 lines
     (symbol, @revenue, @costOfRevenue, @grossProfit, @eps, @pbValueRatio, @peRatio, @pegRatio, @dividendYield, @debtRatio, @dividendPayoutRatio, @epsGrowth, sector, priceVar)
     set  year = 2015,
          revenue = nullif(@revenue, ''),
          costOfRevenue = nullif(@costOfRevenue, ''),
          grossProfit = nullif(@grossProfit, ''),
          eps = nullif(@eps, ''),
          pbValueRatio = nullif(@pbValueRatio,''),
          peRatio = nullif(@peRatio,''),
          pegRatio = nullif(@pegRatio,''),
          dividendYield = nullif(@dividendYield,''),
          debtRatio = nullif(@debtRatio,''),
          dividendPayoutRatio = nullif(@dividendPayoutRatio,''),
          epsGrowth = nullif(@epsGrowth,'');

-- load the 2016 Indicator data into Indicator table
load data infile '/var/lib/mysql-files/18-Stocks/Project37/2016_Financial_Data_Clean.csv' ignore into table Indicator
     fields terminated by ','
     lines terminated by '\n'
     ignore 1 lines
     (symbol, @revenue, @costOfRevenue, @grossProfit, @eps, @pbValueRatio, @peRatio, @pegRatio, @dividendYield, @debtRatio, @dividendPayoutRatio, @epsGrowth, sector, priceVar)
     set  year = 2016,
          revenue = nullif(@revenue, ''),
          costOfRevenue = nullif(@costOfRevenue, ''),
          grossProfit = nullif(@grossProfit, ''),
          eps = nullif(@eps, ''),
          pbValueRatio = nullif(@pbValueRatio,''),
          peRatio = nullif(@peRatio,''),
          pegRatio = nullif(@pegRatio,''),
          dividendYield = nullif(@dividendYield,''),
          debtRatio = nullif(@debtRatio,''),
          dividendPayoutRatio = nullif(@dividendPayoutRatio,''),
          epsGrowth = nullif(@epsGrowth,'');

-- load the 2017 Indicator data into Indicator table
load data infile '/var/lib/mysql-files/18-Stocks/Project37/2017_Financial_Data_Clean.csv' ignore into table Indicator
     fields terminated by ','
     lines terminated by '\n'
     ignore 1 lines
     (symbol, @revenue, @costOfRevenue, @grossProfit, @eps, @pbValueRatio, @peRatio, @pegRatio, @dividendYield, @debtRatio, @dividendPayoutRatio, @epsGrowth, sector, priceVar)
     set  year = 2017,
          revenue = nullif(@revenue, ''),
          costOfRevenue = nullif(@costOfRevenue, ''),
          grossProfit = nullif(@grossProfit, ''),
          eps = nullif(@eps, ''),
          pbValueRatio = nullif(@pbValueRatio,''),
          peRatio = nullif(@peRatio,''),
          pegRatio = nullif(@pegRatio,''),
          dividendYield = nullif(@dividendYield,''),
          debtRatio = nullif(@debtRatio,''),
          dividendPayoutRatio = nullif(@dividendPayoutRatio,''),
          epsGrowth = nullif(@epsGrowth,'');

-- load the 2018 Indicator data into Indicator table
load data infile '/var/lib/mysql-files/18-Stocks/Project37/2018_Financial_Data_Clean.csv' ignore into table Indicator
     fields terminated by ','
     lines terminated by '\n'
     ignore 1 lines
     (symbol, @revenue, @costOfRevenue, @grossProfit, @eps, @pbValueRatio, @peRatio, @pegRatio, @dividendYield, @debtRatio, @dividendPayoutRatio, @epsGrowth, sector, priceVar)
     set  year = 2018,
          revenue = nullif(@revenue, ''),
          costOfRevenue = nullif(@costOfRevenue, ''),
          grossProfit = nullif(@grossProfit, ''),
          eps = nullif(@eps, ''),
          pbValueRatio = nullif(@pbValueRatio,''),
          peRatio = nullif(@peRatio,''),
          pegRatio = nullif(@pegRatio,''),
          dividendYield = nullif(@dividendYield,''),
          debtRatio = nullif(@debtRatio,''),
          dividendPayoutRatio = nullif(@dividendPayoutRatio,''),
          epsGrowth = nullif(@epsGrowth,'');

-- insert into Sector table
insert into Sector select distinct sector from Indicator;
-- insert into Stock table if stock doesn't exist already
insert into Stock select distinct symbol, sector from Indicator;
-- add the foreign key (need to do after making sure Sector table has the sector)
alter table Stock add constraint fk_Stock_Sector foreign key (sector) references Sector(sector);
-- add the foreign key (need to do after making sure Stock table has the stock)
alter table Indicator add constraint fk_Indicator_Stock foreign key (symbol) references Stock(symbol);

-- create News table
select '----------------------------------------------------------------' as '';
select 'News' as '';
create table News (
	date date not null,
    headline varchar(500) not null,
    symbol char(5) not null,
    publisher varchar(255) not null,
    url varchar(255) not null,
    primary key (date, headline)
);	

-- load News table with Benzinga data
load data infile '/var/lib/mysql-files/18-Stocks/raw_analyst_ratings.csv' ignore into table News
     fields terminated by ','
     optionally enclosed by '"' escaped by '\b'
     lines terminated by '\n'
     ignore 1 lines
     (@ignore, headline, url, publisher, @date, symbol)
     set date = date_format(@date, '%Y-%m-%d');

-- load News table with Benzinga partner data
load data infile '/var/lib/mysql-files/18-Stocks/raw_partner_headlines.csv' ignore into table News
     fields terminated by ','
     optionally enclosed by '"' escaped by '\b'
     lines terminated by '\n'
     ignore 1 lines
     (@ignore, headline, url, publisher, @date, symbol)
     set date = date_format(@date, '%Y-%m-%d');

-- insert into Stock table if stock doesn't exist already
insert into Stock select distinct t1.symbol, NULL from News t1 where not exists ( select distinct symbol from Stock t2 where t2.symbol = t1.symbol );
-- add the foreign key (need to do after making sure Stock table has the stock)
alter table News add constraint fk_News_Stock foreign key (symbol) references Stock(symbol);

-- create publisher view
select '----------------------------------------------------------------' as '';
select 'Publisher' as '';
create view Publisher as select distinct publisher from News;

-- create History table
select '----------------------------------------------------------------' as '';
select 'History' as '';
create table History (
	date date not null,
	symbol char(5) not null,
	open decimal(26, 18) not null,
	high decimal(26, 18) not null,
	low decimal(26, 18) not null,
	close decimal(26, 18) not null,
	adjClose decimal(26, 18) not null,
	volume int unsigned not null,
    primary key (date, symbol)
);

-- load the History table
load data infile '/var/lib/mysql-files/18-Stocks/fh_5yrs.csv' ignore into table History
     fields terminated by ','
     lines terminated by '\n'
     ignore 1 lines
     (date, volume, open, high, low, close, adjClose, symbol);

-- insert into Stock table
insert into Stock select distinct t1.symbol, NULL from History t1 where not exists ( select distinct symbol from Stock t2 where t2.symbol = t1.symbol );
-- add the foreign key (need to do after making sure Stock table has the stock)
alter table History add constraint fk_History_Stock foreign key (symbol) references Stock(symbol);

-- create Comment table
select '----------------------------------------------------------------' as '';
select 'Comment' as '';
create table Comment (
	date datetime not null,
	symbol char(5) not null,
    comment varchar(255) not null,
    primary key (date, symbol),
    foreign key (symbol) references Stock(symbol)
);

-- Done
nowarning
notee
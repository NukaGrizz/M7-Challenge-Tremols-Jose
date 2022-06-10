use music_store_catalog;

insert into artist(`name`, instagram, twitter)values
    ('Stephine', '@Steph', '@Stepht'),
	('Joel', '@Joel', '@Joell');

insert into label(`name`, website) values
    ('Dans label','www.dan.com'),
    ('Adams label','www.adam.com');

insert into album(title, artist_id, release_date, label_id, list_price)values
    ('World of Goo', '', 2022-06-06, '', 9.99),
    ('Floating', '', 2022-06-01, '', 9.99);

insert into track(album_id, title, run_time) values
    ('', 'Liquid Gold', 120),
    ('', 'Fracking', 180),
    ('', 'Drill Baby Drill', 180),
    ('', 'Endless Summer', 180),
    ('', 'Eco Revolution', 120),
    ('', 'PinaColada Problems', 140);
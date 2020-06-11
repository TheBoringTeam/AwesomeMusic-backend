--- Init languages
insert into
awesome_language(language_id, language_code, language_name)
values
(1, 'EN', 'English'),
(2, 'PL', 'Polish'),
(3, 'RU', 'Russian');

--- Init countries
insert into
country(country_id, country_code, country_name)
values
(1, 'PL', 'Poland'),
(2, 'UA', 'Ukraine'),
(3, 'RU', 'Russia'),
(4, 'BL', 'Belarus'),
(5, 'UK', 'United Kingdom'),
(6, 'DE', 'Germany');

--- Init song owner
insert into song_owner(song_owner_id, comment, song_owner_email, song_owner_name)
values (1, 'Unknown', 'Unknown', 'Unknown');

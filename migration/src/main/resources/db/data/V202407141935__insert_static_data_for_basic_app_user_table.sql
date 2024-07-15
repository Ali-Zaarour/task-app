-------------------------------------------------------------------------------------------
--@author  Ali-Zaarour
-------------------------------------------------------------------------------------------
--@name    data/2024/05/V202405302050__insert_static_data_for_basic_app_user_table.sql
-------------------------------------------------------------------------------------------
--@doc     This SQL doc will provide the static meta-date related to app-user table.
-------------------------------------------------------------------------------------------
insert into app_user_permission (key)
values ('CREATE'),
       ('READ'),
       ('UPDATE'),
       ('DELETE')
ON CONFLICT DO NOTHING;

insert into app_user_role (key)
values
    ('ADMIN'),
    ('USER'),
    ('MANAGER')
on conflict do nothing;

insert into app_user_status (key)
values
    ('ACTIVATED'),
    ('SUSPENDED'),
    ('DELETED')
on conflict do nothing;
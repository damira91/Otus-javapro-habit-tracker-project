insert into users (password, email, first_name, last_name, age)
values
    ('user213', 'user1@gmail.com','John', 'Doe', 23)
returning id;

insert into categories (name)
values
    ('Health'),
    ('Beauty'),
    ('Study');

insert into habits (name, goal, category_id, user_id)
values
    ('Drink water', '8 glasses', 1,1),
    ('Caring face skin', 'pure skin', 2, 1),
    ('Reading a book', '15 minutes a day', 3,1);

insert into practices (date, done, habit_id)
values
    ('2024-10-07', true, 2),
    ('2024-10-08', false, 2),
    ('2024-10-09', true, 3),
    ('2024-10-16', true, 3),
    ('2024-10-18', true, 5);




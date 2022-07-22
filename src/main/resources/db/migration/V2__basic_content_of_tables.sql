INSERT INTO company (name, description) VALUES
('Future Technology', 'Approaching humanity to the near future'),
('Agro firm', 'Intellectual provision of agricultural machinery'),
('Integrate and use', 'Moving your business to the digital world');

INSERT INTO customer (first_name, second_name, age) VALUES
('Aller', 'Han', 38),
('Kevin', 'Stoon', 34),
('Liz', 'Krabse', 40);

INSERT INTO project (name, company_id, customer_id, creation_date) VALUES
('Artificial intelligence for milling machine', 1, 2, '2022-06-07'),
('App for simple options', 3, 1, '2022-06-07'),
('Finding profitable ways to exchange currencies', 1, 1, '2022-06-07');

INSERT INTO developer (first_name, second_name, age, gender, salary) VALUES
('Did', 'Panas', 61, 'MALE', 7000),
('Fedir', 'Tomson', 45, 'MALE', 4000),
('Olga', 'Dzi', 50, 'FEMALE', 1000),
('Oleg', 'Filli', 23, 'MALE', 200),
('Nina', 'Weendi', 24, 'FEMALE', 500);

INSERT INTO project_developer (project_id, developer_id) VALUES
(1, 1),
(1, 3),
(1, 5),
(2, 2),
(2, 4),
(2, 5),
(3, 1),
(3, 2);

INSERT INTO skill (department, skill_level) VALUES
('java', 'junior'),
('java', 'middle'),
('java', 'senior'),
('python', 'junior'),
('python', 'middle'),
('python', 'senior');

INSERT INTO developer_skill (developer_id, skill_id) VALUES
(1, 3),
(1, 6),
(2, 3),
(3, 2),
(4, 4),
(5, 4),
(5, 1);

UPDATE project AS T1
SET T1.cost = (
    SELECT SUM(salary)
    FROM developer AS T2
    WHERE T2.id IN (
        SELECT T3.developer_id
        FROM project_developer AS T3
        WHERE T3.project_id=T1.id
    )
)
WHERE id IN (1, 2, 3);
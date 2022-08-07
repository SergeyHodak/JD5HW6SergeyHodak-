ALTER TABLE project
DROP FOREIGN KEY project_ibfk_1;

ALTER TABLE project
DROP FOREIGN KEY project_ibfk_2;

ALTER TABLE project
ADD CONSTRAINT company_id_fk
FOREIGN KEY(company_id)
REFERENCES company(id)
ON DELETE CASCADE;

ALTER TABLE project
ADD CONSTRAINT customer_id_fk
FOREIGN KEY(customer_id)
REFERENCES customer(id)
ON DELETE CASCADE;


ALTER TABLE project_developer
DROP FOREIGN KEY project_developer_ibfk_1;

ALTER TABLE project_developer
DROP FOREIGN KEY project_developer_ibfk_2;

ALTER TABLE project_developer
ADD CONSTRAINT project_id_fk
FOREIGN KEY(project_id)
REFERENCES project(id)
ON DELETE CASCADE;

ALTER TABLE project_developer
ADD CONSTRAINT developer_id_fk
FOREIGN KEY(developer_id)
REFERENCES developer(id)
ON DELETE CASCADE;


ALTER TABLE developer_skill
DROP FOREIGN KEY developer_skill_ibfk_1;

ALTER TABLE developer_skill
DROP FOREIGN KEY developer_skill_ibfk_2;

ALTER TABLE developer_skill
ADD CONSTRAINT developer_id_fk2
FOREIGN KEY(developer_id)
REFERENCES developer(id)
ON DELETE CASCADE;

ALTER TABLE developer_skill
ADD CONSTRAINT skill_id_fk
FOREIGN KEY(skill_id)
REFERENCES skill(id)
ON DELETE CASCADE;
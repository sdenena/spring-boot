INSERT INTO mas_role(role_name, admin, version) VALUES ('Admin', true, 0);
INSERT INTO mas_user(first_name, last_name, username, password, email, version) VALUES ('John', 'Doe', 'admin', '{bcrypt}$2a$10$0yMmx72SmRq7buHGf4F2ze83.vBrzbVzET20QmwrDktYnreYZy1kW', null, 0);
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
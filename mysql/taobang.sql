USE thuchanhbai12;
DELIMITER $$
CREATE PROCEDURE get_user_by_id(IN user_id INT)
BEGIN
SELECT users.name,users.email,users.country
FROM users WHERE users.id=user_id;
END $$
DELIMITER ;
CREATE TABLE permision(
    id int(11) PRIMARY KEY ,
    name VARCHAR(50)
);
CREATE TABLE User_pernision(
    permision_id INT(11),
    user_id INT(11)
);
INSERT INTO permision(id, name) VALUE (1,'add'),(2,'edit'),(3,'delete'),(4,'view');



USE thuchanhbai12;
DELIMITER $$
CREATE PROCEDURE get_user_by_id(IN user_id INT)
BEGIN
SELECT users.name,users.email,users.country
FROM users WHERE users.id=user_id;
END $$
DELIMITER ;



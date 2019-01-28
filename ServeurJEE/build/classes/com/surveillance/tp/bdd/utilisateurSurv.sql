CREATE USER 'AdminSurv'@'localhost' IDENTIFIED BY 'mdpAdmin';
CREATE DATABASE projetsurv;
GRANT ALL PRIVILEGES ON projetsurv.* TO 'AdminSurv'@'localhost';
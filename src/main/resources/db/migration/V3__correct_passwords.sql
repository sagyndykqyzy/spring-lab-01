
UPDATE app_user
SET password_hash = '$2a$10$ovxS1PcmosmcRc2CU0Ea4e8YJGQzOgt4QvEMhTGWHNo4YsvQRH6SS'
WHERE username = 'user';

UPDATE app_user
SET password_hash = '$2a$10$lEhLNkADgg0VKQ7AeQpC7.pWC9dESYFmnXTEikpF3RKJaMuBnMVZq'
WHERE username = 'admin';

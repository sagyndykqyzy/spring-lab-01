UPDATE app_user
SET password_hash = 'СКОПИРОВАННЫЙ_USER_HASH'
WHERE username = 'user';

UPDATE app_user
SET password_hash = 'СКОПИРОВАННЫЙ_ADMIN_HASH'
WHERE username = 'admin';
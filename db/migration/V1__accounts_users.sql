CREATE TABLE accounts (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  owner_id INTEGER,
  default_role_id INTEGER,
  created_at TIMESTAMP WITH TIME ZONE default (now() at time zone 'utc'),
  updated_at TIMESTAMP WITH TIME ZONE default (now() at time zone 'utc'),
  CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE users (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  username VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  account_id INTEGER NOT NULL,
  role_id INTEGER NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE default (now() at time zone 'utc'),
  updated_at TIMESTAMP WITH TIME ZONE default (now() at time zone 'utc'),
  CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);

ALTER TABLE accounts ADD CONSTRAINT uc_accounts_name UNIQUE (name);
ALTER TABLE accounts ADD CONSTRAINT FK_ACCOUNTS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);


CREATE TABLE roles (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  account_id INTEGER NOT NULL,
  permissions VARCHAR(500) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE default (now() at time zone 'utc'),
  updated_at TIMESTAMP WITH TIME ZONE default (now() at time zone 'utc'),
  CONSTRAINT pk_roles PRIMARY KEY (id)
);

ALTER TABLE roles ADD CONSTRAINT FK_ROLES_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);
ALTER TABLE users ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE accounts ADD CONSTRAINT FK_ACCOUNTS_ON_ROLE FOREIGN KEY (default_role_id) REFERENCES roles (id);

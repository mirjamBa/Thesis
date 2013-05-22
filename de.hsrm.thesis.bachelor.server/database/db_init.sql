 CREATE TABLE TABUSERS (
          u_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT USERS_PK PRIMARY KEY,
          username VARCHAR(32) NOT NULL,
          pass VARCHAR(256) NOT NULL, 
          salt VARCHAR(64) NOT NULL, 
          permission_id INT NOT NULL, 
          icon BLOB 
          );
          
CREATE UNIQUE INDEX IX_USERNAME ON TABUSERS (username);

CREATE TABLE file (
    file_id   BIGINT NOT NULL GENERATED ALWAYS BY DEFAULT AS IDENTITY PRIMARY KEY,
    number    BIGINT,
    filetype  BIGINT, 
    user      INT);

CREATE TABLE tag_file (
    tag_id    BIGINT,
    file_id   BIGINT);
    
CREATE TABLE tag (
    tag_id    BIGINT NOT NULL GENERATED ALWAYS BY DEFAULT AS IDENTITY PRIMARY KEY,
    name      VARCHAR(256) NOT NULL);
    
CREATE TABLE filetype (
    filetype_id BIGINT NOT NULL GENERATED ALWAYS BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR(256) NOT NULL, 
    mimetype    VARCHAR(64) NOT NULL, 
    language    VARCHAR(32),
    versioning  CHAR NOT NULL);
    
CREATE TABLE role_file_permission (
    file_id     BIGINT NOT NULL,
    permission_id INT NOT NULL );
    
CREATE TABLE version (
    version_id  BIGINT NOT NULL GENERATED ALWAYS AS IDNETITY PRIMARY KEY,
    file_id     BIGINT NOT NULL,
    version     VARCHAR(8));
    
CREATE TABLE metadata (
    metadata_id BIGINT NOT NULL GENERATED ALWAYS BY DEFAULT AS IDENTITY PRIMARY KEY,
    version     VARCHAR(8),
    file_id     BIGINT NOT NULL,
    attribute_id  BIGINT,
    value       VARCHAR(512));

CREATE TABLE metadataattribute (
    attribute_id  BIGINT NOT NULL GENERATED ALWAYS BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    filetype_id   BIGINT NOT NULL);

   
  
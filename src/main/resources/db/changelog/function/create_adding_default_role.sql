CREATE OR REPLACE FUNCTION adding_default_role()
    returns trigger
    language plpgsql
AS
'
    DECLARE
    BEGIN
        INSERT INTO person_role(person_id, role)
        values (new.id, ''USER'');
        RETURN new;
    END;
';

CREATE OR REPLACE TRIGGER adding_default_role
    AFTER insert
    ON person
    for each row
EXECUTE FUNCTION adding_default_role();
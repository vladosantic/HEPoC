INSERT INTO election (name, start_date, end_date)
SELECT 'Election 2024', '2024-01-01', '2024-12-31'
WHERE NOT EXISTS (
    SELECT 1
    FROM election
    WHERE name = 'Election 2024'
);

INSERT INTO election (name, start_date, end_date)
SELECT 'Election 2023', '2023-01-01', '2023-12-31'
WHERE NOT EXISTS (
    SELECT 1
    FROM election
    WHERE name = 'Election 2023'
);

INSERT INTO election (name, start_date, end_date)
SELECT 'Election 2022', '2022-01-01', '2022-12-31'
WHERE NOT EXISTS (
    SELECT 1
    FROM election
    WHERE name = 'Election 2022'
);

INSERT INTO election (name, start_date, end_date)
SELECT 'Election 2021', '2021-01-01', '2021-12-31'
WHERE NOT EXISTS (
    SELECT 1
    FROM election
    WHERE name = 'Election 2021'
);

-- Insert candidate records, using the election_id for "Election 2024"
INSERT INTO candidate (email, last_name, name, party, election_id)
SELECT email, last_name, name, party, election_id
FROM (
         VALUES
             ('john.doe@example.com', 'Doe', 'John', 'Democratic'),
             ('jane.smith@example.com', 'Smith', 'Jane', 'Republican'),
             ('alice.johnson@example.com', 'Johnson', 'Alice', 'Green'),
             ('bob.brown@example.com', 'Brown', 'Bob', 'Libertarian'),
             ('carol.white@example.com', 'White', 'Carol', 'Independent')
     ) AS new_candidates (email, last_name, name, party)
-- Retrieve election_id for "Election 2024"
         JOIN (
    SELECT id AS election_id
    FROM election
    WHERE name = 'Election 2024'
) AS election_data
-- Join new candidates with the correct election_id
              ON true
WHERE NOT EXISTS (
    SELECT 1
    FROM candidate c
    WHERE c.email = new_candidates.email
      AND c.election_id = election_data.election_id
);

INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_ADMIN');

INSERT INTO roles (name)
SELECT 'ROLE_CITIZEN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ROLE_CITIZEN');

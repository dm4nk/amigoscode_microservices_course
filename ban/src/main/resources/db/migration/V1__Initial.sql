CREATE TABLE IF NOT EXISTS bans (
	id UUID NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY, --replace with random_uuid() on clean-install
	customer_id UUID NOT NULL,
	valid_from timestamp with time zone NOT NULL,
	valid_to timestamp with time zone NOT NULL
);

CREATE INDEX IF NOT EXISTS bans_customer_id ON bans USING HASH (customer_id);

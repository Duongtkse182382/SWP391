CREATE TRIGGER trg_UpdateCustomerRank
ON Customer
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Assuming 'i' is the alias for the 'inserted' pseudo-table
    -- and 'c' is the alias for the 'Customer' table
    UPDATE c
    SET c.Rank = CASE
        WHEN i.LoyalPoint < 100 THEN 'Copper'
        WHEN i.LoyalPoint >= 100 AND i.LoyalPoint < 200 THEN 'Silver'
        WHEN i.LoyalPoint >= 200 AND i.LoyalPoint < 300 THEN 'Gold'
        WHEN i.LoyalPoint >= 300 THEN 'Diamond'
    END
    FROM Customer c
    INNER JOIN inserted i ON c.CustomerID = i.CustomerID
    WHERE c.LoyalPoint <> i.LoyalPoint;
END;

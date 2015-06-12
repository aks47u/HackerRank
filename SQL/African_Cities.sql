SELECT City.Name
  FROM City
  JOIN Country
    ON City.CountryCode = Country.Code
    WHERE Continent = 'Africa';

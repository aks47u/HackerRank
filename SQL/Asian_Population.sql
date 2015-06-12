SELECT SUM(City.Population)
  FROM City
  JOIN Country ON City.CountryCode = Country.Code
    WHERE Country.Continent = 'Asia';

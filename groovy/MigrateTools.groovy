import groovy.sql.Sql
import org.flywaydb.core.Flyway
import org.flywaydb.core.internal.util.jdbc.DriverDataSource

public class FlywayMigrateAll { 

  public void migrate(String shemaName) { 
    def driverName = 'org.postgresql.Driver'
    Flyway flyway = new Flyway()
    
    flyway.setDataSource(new DriverDataSource(Thread.currentThread().getContextClassLoader(), 
        driverName, 
        'jdbc:postgresql://localhost:5432/mydrive', 
        'mydrive', 
        'mydrive'))
    
    flyway.schemas = [shemaName]
    flyway.migrate()
  } 
} 

def f = new FlywayMigrateAll()
f.migrate(args[0])
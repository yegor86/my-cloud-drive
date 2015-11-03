package org.odesamama.mcd.multitenancy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private static final long serialVersionUID = 4736696065367100407L;
    @Autowired
    private DataSource dataSource;

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        try {
            ResultSet rs = connection.createStatement().executeQuery(
                    String.format("select count(*) from pg_namespace where nspname = '%s'", tenantIdentifier));

            if (rs.next() && rs.getInt(1) > 0) {
                connection.createStatement().execute(String.format("set search_path to '%s'", tenantIdentifier));
            } else {
                connection.createStatement().execute("set search_path to 'public'");
            }
        } catch (SQLException e) {
            throw new HibernateException(
                    "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute(String.format("set search_path to 'public'", tenantIdentifier));
        } catch (SQLException e) {
            throw new HibernateException(
                    "Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

}

package com.paymybill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${jdbc.driver_class_name}")
    public String jdbcDriverClassName;

    @Value("${jdbc.username}")
    public String jdbcUsername;

    @Value("${jdbc.password}")
    public String jdbcPassword;

    @Value("${jdbc.host}")
    public String jdbcHost;

    @Value("${jdbc.port}")
    public String jdbcPort;

    @Value("${jdbc.database_name}")
    public String jdbcDatabaseName;

    @Value("${jdbc.connection_string}")
    public String jdbcConnectionString;

    @Value("${jdbc.connection_string_opts}")
    public String jdbcConnectionStringOpts;

    @Value("${app.files_path}")
    public String filesPath;

    @Value("${app.date_format")
    public String dateFormat;

    public String getJdbcURL() {
        return this.getJdbcConnectionString() + this.getJdbcHost() + ":" + this.getJdbcPort() + "/" + this.getJdbcDatabaseName() + this.getJdbcConnectionStringOpts();
    }

    public String getJdbcDriverClassName() {
        return jdbcDriverClassName;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public String getJdbcHost() {
        return jdbcHost;
    }

    public String getJdbcPort() {
        return jdbcPort;
    }

    public String getJdbcDatabaseName() {
        return jdbcDatabaseName;
    }

    public String getJdbcConnectionString() {
        return jdbcConnectionString;
    }

    public String getJdbcConnectionStringOpts() {
        return jdbcConnectionStringOpts;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}

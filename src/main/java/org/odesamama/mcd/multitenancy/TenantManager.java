package org.odesamama.mcd.multitenancy;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

import groovy.lang.GroovyShell;

public class TenantManager {

    public void create(String tenantName) throws CompilationFailedException, IOException {
        GroovyShell shell = new GroovyShell(getClass().getClassLoader());
        shell.run(new File("groovy/MigrateTools.groovy"), new String[] { tenantName });
    }
}

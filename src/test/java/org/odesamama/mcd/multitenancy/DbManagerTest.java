package org.odesamama.mcd.multitenancy;

import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.junit.Test;

public class DbManagerTest {

    @Test
    public void testMigrate() throws CompilationFailedException, IOException {
        TenantManager dbManager = new TenantManager();
        dbManager.create("yfadeev");
    }

}

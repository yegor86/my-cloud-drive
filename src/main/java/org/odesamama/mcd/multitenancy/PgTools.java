package org.odesamama.mcd.multitenancy;

public class PgTools {

    // def set_search_path(name, include_public = true)
    // path_parts = [name.to_s, ("public" if include_public)].compact
    // ActiveRecord::Base.connection.schema_search_path = path_parts.join(",")
    // end
    //
    // def restore_default_search_path
    // ActiveRecord::Base.connection.schema_search_path = default_search_path
    // end

    public void setSchema(String name) {

    }
}

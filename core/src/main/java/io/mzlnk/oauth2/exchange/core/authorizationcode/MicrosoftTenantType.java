package io.mzlnk.oauth2.exchange.core.authorizationcode;

public enum MicrosoftTenantType {

    COMMON("common"),
    ORGANIZATIONS("organizations"),
    CONSUMERS("consumers"),
    CUSTOM();

    private String tenant;

    MicrosoftTenantType(String tenant) {
        this.tenant = tenant;
    }

    MicrosoftTenantType() {
    }

    public static MicrosoftTenantType from(String tenant) {
        var tenantType = MicrosoftTenantType.CUSTOM;
        tenantType.tenant = tenant;

        return tenantType;
    }

    public String getTenant() {
        return this.tenant;
    }

}
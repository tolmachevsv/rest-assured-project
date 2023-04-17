package com.tolmachevsv.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/credentials.properties"})
public interface CredentialsConfig extends Config {
    String loginSelenoid();
    String passwordSelenoid();
}

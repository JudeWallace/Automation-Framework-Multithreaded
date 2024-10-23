package org.automationsuite.config;


public class RunTimeConfiguration {
    public static void environmentSetup(){
        if (System.getenv("PIPELINE") == null) {
        }
    }
}

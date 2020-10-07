package com.vinicius.meuprojetomodule.config;

import org.opensingular.requirement.studio.init.RequirementStudioAppInitializer;

public class MeuProjetoModuleInitializer implements RequirementStudioAppInitializer {

    @Override
    public String moduleCod() {
        return "MEUPROJETO";
    }

    @Override
    public String[] springPackagesToScan() {
        return new String[]{"com.vinicius"};
    }

}

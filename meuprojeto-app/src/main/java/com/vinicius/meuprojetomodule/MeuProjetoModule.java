package com.vinicius.meuprojetomodule;

import com.vinicius.meuprojetomodule.flow.MeuProjetoFlow;
import com.vinicius.meuprojetomodule.form.MeuProjetoForm;
import com.vinicius.meuprojetomodule.box.MeuProjetoCaixaPendencia;

import org.opensingular.requirement.commons.SingularRequirement;
import org.opensingular.requirement.module.FormFlowSingularRequirement;
import org.opensingular.requirement.module.RequirementConfiguration;
import org.opensingular.requirement.module.SingularModule;
import org.opensingular.requirement.module.WorkspaceConfiguration;
import org.opensingular.requirement.module.workspace.DefaultDonebox;
import org.opensingular.requirement.module.workspace.DefaultDraftbox;
import org.opensingular.requirement.module.workspace.DefaultInbox;
import org.opensingular.requirement.module.workspace.DefaultOngoingbox;

public class MeuProjetoModule implements SingularModule {

    public static final String              MEUPROJETO = "MEUPROJETO";
    private             SingularRequirement meuprojeto = new FormFlowSingularRequirement("MeuProjeto", MeuProjetoForm.class, MeuProjetoFlow.class);

    @Override
    public String abbreviation() {
        return MEUPROJETO;
    }

    @Override
    public String name() {
        return "Módulo MeuProjeto";
    }

    @Override
    public void requirements(RequirementConfiguration config) {
        config
                .addRequirement(meuprojeto);
    }

    @Override
    public void workspace(WorkspaceConfiguration config) {
        config
                .addBox(new DefaultDraftbox()).newFor(meuprojeto)
                .addBox(new MeuProjetoCaixaPendencia())
                .addBox(new DefaultInbox())
                .addBox(new DefaultOngoingbox())
                .addBox(new DefaultDonebox());
    }
}

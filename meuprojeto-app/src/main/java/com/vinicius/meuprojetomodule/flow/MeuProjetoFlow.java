package com.vinicius.meuprojetomodule.flow;

import com.vinicius.meuprojetomodule.MeuProjetoModule;
import org.opensingular.flow.core.DefinitionInfo;
import org.opensingular.flow.core.ITaskDefinition;
import org.opensingular.flow.core.FlowInstance;
import org.opensingular.flow.core.defaults.PermissiveTaskAccessStrategy;

import org.opensingular.requirement.commons.flow.builder.RequirementFlowBuilder;
import org.opensingular.requirement.commons.flow.builder.RequirementFlowDefinition;
import org.opensingular.requirement.commons.wicket.view.form.FormPage;


import javax.annotation.Nonnull;

import static com.vinicius.meuprojetomodule.flow.MeuProjetoFlow.MeuProjetoTasks.*;

@DefinitionInfo("meuprojeto")
public class MeuProjetoFlow extends RequirementFlowDefinition<FlowInstance> {

    public static String ENVIAR_DIRETOR = "Enviar para Diretor";
    public static String ENVIAR_RH = "Enviar para RH";

    public enum MeuProjetoTasks implements ITaskDefinition {

        ANALISE_CHEFIA("Analise da Chefia"),
        DECISAO_ANTECIPACAO_13("Encaminhar"),
        ANALISE_DIRETOR("Analise do Dieretor"),
        ANALISE_RH("Analise do RH"),
        APROVADO("Aprovado"),
        REPROVADO("Reprovado"),
        SOLICITACAO_COM_PENDENCIAS("Solicitação com pendências");

        private String taskName;

        MeuProjetoTasks(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String getName() {
            return taskName;
        }
    }

    public MeuProjetoFlow() {
        super(FlowInstance.class);
        this.setName(MeuProjetoModule.MEUPROJETO, "MeuProjeto");

    }

    @Override
    protected void buildFlow(@Nonnull RequirementFlowBuilder flow) {

        flow.addHumanTask(ANALISE_CHEFIA)
                .uiAccess(new PermissiveTaskAccessStrategy()).withExecutionPage(FormPage.class);

        flow.addJavaTask(DECISAO_ANTECIPACAO_13)
                .call(new DecisaoAnaliseDiretor());

        flow.addHumanTask(ANALISE_DIRETOR)
                .uiAccess(new PermissiveTaskAccessStrategy()).withExecutionPage(FormPage.class);

        flow.addHumanTask(ANALISE_RH)
                .uiAccess(new PermissiveTaskAccessStrategy()).withExecutionPage(FormPage.class);

        flow.addHumanTask(SOLICITACAO_COM_PENDENCIAS).uiAccess(new PermissiveTaskAccessStrategy())
                .withExecutionPage(FormPage.class);

        flow.addEndTask(REPROVADO);
        flow.addEndTask(APROVADO);

        flow.setStartTask(ANALISE_CHEFIA);
        flow.from(ANALISE_CHEFIA).go(DECISAO_ANTECIPACAO_13);
        flow.from(DECISAO_ANTECIPACAO_13).go("Enivar para Diretor", ANALISE_DIRETOR);
        flow.from(DECISAO_ANTECIPACAO_13).go("Enivar para RH", ANALISE_RH);

        flow.from(ANALISE_DIRETOR).go("Aprovar", ANALISE_RH);
        flow.from(ANALISE_DIRETOR).go("Reprovar", REPROVADO);

        flow.from(ANALISE_RH).go("Aprovar",APROVADO);
        flow.from(ANALISE_RH).go("Reprovar",REPROVADO);
        flow.from(ANALISE_RH).go("Solicitar ajustes",SOLICITACAO_COM_PENDENCIAS);

        flow.from(SOLICITACAO_COM_PENDENCIAS).go("Concluir Pendência", ANALISE_CHEFIA);
    }


}
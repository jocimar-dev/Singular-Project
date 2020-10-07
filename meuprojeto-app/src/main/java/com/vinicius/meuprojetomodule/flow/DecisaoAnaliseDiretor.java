package com.vinicius.meuprojetomodule.flow;

import com.vinicius.meuprojetomodule.form.MeuProjetoForm;
import org.opensingular.flow.core.ExecutionContext;
import org.opensingular.flow.core.FlowInstance;
import org.opensingular.flow.core.TaskJavaCall;
import org.opensingular.form.SIComposite;
import org.opensingular.requirement.commons.service.RequirementInstance;
import org.opensingular.requirement.commons.service.RequirementService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class DecisaoAnaliseDiretor implements TaskJavaCall<FlowInstance> {

    @Inject
    private RequirementService requirementService;

    @Override
    public void call(ExecutionContext<FlowInstance> executionContext) {

        RequirementInstance requirementInstance = requirementService.getRequirementInstance(executionContext.getTaskInstance());
        Optional formOpt = requirementService.findLastFormRequirementInstanceByType(requirementInstance, MeuProjetoForm.class);

        if (formOpt.isPresent()){
            SIComposite form = (SIComposite) formOpt.get();
            Boolean antecipar13 = (Boolean) form.getField("antecipar13").getValue();
            if (antecipar13) {
                executionContext.setTransition(MeuProjetoFlow.ENVIAR_DIRETOR);
            } else {
                executionContext.setTransition(MeuProjetoFlow.ENVIAR_RH);
            }
        }
    }
}

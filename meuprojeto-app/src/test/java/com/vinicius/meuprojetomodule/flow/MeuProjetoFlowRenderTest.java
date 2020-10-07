package com.vinicius.meuprojetomodule.flow;

import com.vinicius.meuprojetomodule.flow.MeuProjetoFlow;
import org.junit.Test;
import org.opensingular.requirement.commons.test.flow.AbstractFlowRenderTest;

public class MeuProjetoFlowRenderTest extends AbstractFlowRenderTest {

    public MeuProjetoFlowRenderTest() {
        setOpenGeneratedFiles(false);
    }

    @Test
    public void render() {
        super.renderImage(new MeuProjetoFlow());
    }
}

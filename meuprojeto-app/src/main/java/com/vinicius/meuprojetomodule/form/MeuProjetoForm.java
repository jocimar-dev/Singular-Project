package com.vinicius.meuprojetomodule.form;

import java.util.Arrays;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.SInstance;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.SIInteger;
import org.opensingular.form.type.core.STypeBoolean;
import org.opensingular.form.type.core.STypeInteger;
import org.opensingular.form.type.core.STypeString;
import org.opensingular.form.type.util.STypeYearMonth;
import org.opensingular.form.util.SingularPredicates;
import org.opensingular.form.validation.InstanceValidatable;
import org.opensingular.form.view.SViewByBlock;

import static org.opensingular.form.util.SingularPredicates.typeValueIsTrueAndNotNull;

@SInfoType(spackage = MeuProjetoPackage.class, name = "MeuProjeto")
public class MeuProjetoForm extends STypeComposite<SIComposite> {

    public STypeYearMonth inicioPeriodoAquisitivo;
    public STypeYearMonth fimPeriodoAquisitivo;
    public STypeInteger quantidadeDiasFerias;
    public  STypeBoolean venderDias;
    public  STypeInteger quantidadeDiasVendidos;
    public STypeBoolean antecipar13;
    public STypeString observacao;

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        asAtr().displayString("Solicitação de férias");

        inicioPeriodoAquisitivo = addField("inicioPeriodoAquisitivo",
                STypeYearMonth.class);

        inicioPeriodoAquisitivo
                .asAtr()
                .label("Início do período aquisitivo")
                .required()
                .asAtrBootstrap()
                .colPreference(2);

        fimPeriodoAquisitivo = addField("fimPeriodoAquisitivo",
                STypeYearMonth.class);

        fimPeriodoAquisitivo
                .asAtr()
                .label("fim do período aquisitivo")
                .required()
                .asAtrBootstrap()
                .colPreference(2);

        quantidadeDiasFerias = addFieldInteger("quantidadeDiasFerias");

        quantidadeDiasFerias
                .addInstanceValidator(this:: validarNrMaximoDiasFerias)
                .asAtr()
                .label("Quantidade de dias")
                .required()
                .asAtrBootstrap()
                .colPreference(2);

        venderDias = addFieldBoolean("venderDias");
        venderDias.withRadioView("Sim", "Não")
                .asAtr()
                .label("Vender dias?")
                .asAtrBootstrap()
                .colPreference(2);

        quantidadeDiasVendidos = addFieldInteger("quantidadeDiasVendidos");

        quantidadeDiasVendidos
                .asAtr()
                .label("Quantidade dias de vendidos")
                .dependsOn(venderDias)
                .exists(isVenderDias())
                .asAtrBootstrap()
                .colPreference(2);

        antecipar13 = addFieldBoolean("antecipar13");
        antecipar13.withRadioView("Sim", "Não")
                .asAtr()
                .label("Antecipar décimo terceiro")
                .required()
                .asAtrBootstrap()
                .colPreference(2);

        observacao = addFieldString("observacao");
        observacao
                .asAtr()
                .label("Observação")
                .asAtr().maxLength(4000);
        observacao.withTextAreaView(sViewTextArea ->
                sViewTextArea.setLines(4));

        this.addInstanceValidator(this:: validarSomaDiasFerias);

        this.withView(new SViewByBlock(), sViewByBlock ->
                sViewByBlock.newBlock("Solicitação de férias")
        .add(inicioPeriodoAquisitivo)
        .add(fimPeriodoAquisitivo)
        .add(quantidadeDiasFerias)
        .add(venderDias)
        .add(quantidadeDiasVendidos)
        .add(antecipar13)
        .add(observacao));
    }

    private void validarSomaDiasFerias(InstanceValidatable<SIComposite> siCompositeInstanceValidatable) {
        SIComposite form = siCompositeInstanceValidatable.getInstance();

        Integer diasFerias = form.findNearestOrException(quantidadeDiasFerias).getInteger();
        Integer diasVendidos = form.findNearestOrException(quantidadeDiasVendidos).getInteger();
        Boolean isVenderDias = form.findNearestOrException(venderDias).getValue();

        if (isVenderDias !=null && isVenderDias){
            if (diasVendidos == null || diasVendidos <1){
                siCompositeInstanceValidatable.error("Se foi selecionado a opção de vender dias");
            }
        }

        if (diasFerias + diasVendidos > 30) {
            siCompositeInstanceValidatable.error("A soma dos dias de férias + os dias vendidos não pode ser maior que 30");
        }
    }

    private Predicate<SInstance> isVenderDias() {
        return typeValueIsTrueAndNotNull(venderDias);
    }

    private void validarNrMaximoDiasFerias(InstanceValidatable<SIInteger> siIntegerInstanceValidatable) {
        SIInteger diasFerias = siIntegerInstanceValidatable.getInstance();

        if (diasFerias.getInteger() > 30) {
            siIntegerInstanceValidatable.error("Não é possível solicitar mais que 30 dias de férias");
        }
    }


}


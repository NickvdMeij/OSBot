package com.acescripts.scripts.overloadaio.tutorialisland.nodes;

import com.acescripts.scripts.overloadaio.OverloadAIO;
import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.tutorialisland.methods.CharacterDesign;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Random;

import static org.osbot.rs07.script.MethodProvider.random;
import static org.osbot.rs07.script.MethodProvider.sleep;

/**
 * Created by Transporter on 07/08/2016 at 00:11.
 */

public class CreateCharacter extends Node{
    public CreateCharacter(OverloadAIO script) {
        super(script);
    }

    private void interactionAmount(CharacterDesign button, RS2Widget interactAmount) throws InterruptedException {
        Random rn = new Random();
        int min = 0;
        int max = button.getPossibilities();
        int chooseInteractAmount = rn.nextInt(max - min + 1) + min;

        for(int i = 0; i < chooseInteractAmount; i++) {
            if(interactAmount != null) {
                interactAmount.interact();
            }
            sleep(random(500, 1250));
        }
    }

    private void randomPickButtons(CharacterDesign button) throws InterruptedException {
        Random rn = new Random();
        int pickButtonMin = 0;
        int pickButtonMax = 1;
        int chooseButton = rn.nextInt(pickButtonMax - pickButtonMin + 1) + pickButtonMin;

        RS2Widget buttonLeft = script.widgets.get(button.getParent(), button.getChildLeft());
        RS2Widget buttonRight = script.widgets.get(button.getParent(), button.getChildRight());

        if(chooseButton == 0) {
            interactionAmount(button, buttonLeft);
        } else if(chooseButton == 1) {
            interactionAmount(button, buttonRight);
        }
    }

    @Override
    public void execute() throws InterruptedException {
        script.setStatus("Designing Character.");

        Random rn = new Random();
        int max = CharacterDesign.GENDER.getPossibilities();
        int min = 0;
        int chooseGender = rn.nextInt(max - min + 1) + min;

        RS2Widget genderButton = script.widgets.get(269, CharacterDesign.GENDER.getChildRight());

        if(chooseGender == 9 || chooseGender == 10) {
            if(genderButton != null)  {
                genderButton.interact();
            }
        }

        randomPickButtons(CharacterDesign.HEAD_DESIGN);
        randomPickButtons(CharacterDesign.JAW_DESIGN);
        randomPickButtons(CharacterDesign.TORSO_DESIGN);
        randomPickButtons(CharacterDesign.ARMS_DESIGN);
        randomPickButtons(CharacterDesign.HANDS_DESIGN);
        randomPickButtons(CharacterDesign.LEGS_DESIGN);
        randomPickButtons(CharacterDesign.FEET_DESIGN);
        randomPickButtons(CharacterDesign.HAIR_COLOUR);
        randomPickButtons(CharacterDesign.TORSO_COLOUR);
        randomPickButtons(CharacterDesign.LEG_COLOUR);
        randomPickButtons(CharacterDesign.FEET_COLOUR);
        randomPickButtons(CharacterDesign.SKIN_COLOUR);

        RS2Widget makeChar = script.widgets.get(269, 100);

        makeChar.interact();
        new ConditionalSleep(5000) {
            @Override
            public boolean condition() throws InterruptedException {
                return script.widgets.get(269, 97) != null;
            }
        }.sleep();
    }

    @Override
    public boolean validate() throws InterruptedException {
        return script.widgets.get(269, 97) != null && script.configs.get(406) == 0;
    }
}

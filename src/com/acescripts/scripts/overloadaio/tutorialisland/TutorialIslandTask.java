package com.acescripts.scripts.overloadaio.tutorialisland;

import com.acescripts.scripts.overloadaio.framework.Node;
import com.acescripts.scripts.overloadaio.framework.Task;
import com.acescripts.scripts.overloadaio.tutorialisland.nodes.*;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.script.Script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Transporter on 03/08/2016 at 22:20.
 */

public class TutorialIslandTask implements Task {
    private Script script;
    public List<Node> nodes = new ArrayList<>();

    public TutorialIslandTask(Script script) {
        this.script = script;
    }

    @Override
    public void proceed() throws InterruptedException {
        if(nodes.isEmpty()) {
            Collections.addAll(nodes,
                    new CreateCharacter(script),
                    new RunescapeGuide(script),
                    new SurvivalExpert(script),
                    new MasterChef(script),
                    new QuestGuide(script),
                    new MiningInstructor(script),
                    new CombatInstructor(script),
                    new BankInstructor(script),
                    new FinancialAdvisor(script),
                    new BrotherBrace(script),
                    new MagicInstructor(script)
            );
        } else {
            for(Node n: nodes) {
                if(n.validate()) {
                    n.execute();
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        Area tutorialIslandArea = new Area(3059, 3136, 3151, 3059);
        return script.configs.get(406) == 20 && !tutorialIslandArea.contains(script.myPosition());
    }
}

package com.adis.advent.d10;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.List;

public class Machine {

    private int mini = 99999;

    private int[] etatFinal;
    private Bouton[] boutons;
    private int[] joltage;

    public Machine(String input) {
        parse(input);
        resolve();
    }

    private void resolve() {
        int maxNb = (int) Math.pow(2, boutons.length);
        for (int i = 0; i < maxNb; i++) {
            var aMythoDecoder = Integer.toBinaryString(i).toCharArray();
            var aDecoder = new char[boutons.length];
            int delta = boutons.length- aMythoDecoder.length;
            System.arraycopy(aMythoDecoder, 0, aDecoder, delta, aMythoDecoder.length);
            var combinaison = new ArrayList<Bouton>();
            int size = 0;
            for (int j = 0; j < aDecoder.length; j++) {
                if (aDecoder[j] == '1') {
                    combinaison.add(boutons[j]);
                    size++;
                }
            }
            if (size >= mini) {
                continue;
            }
            if (valide(combinaison)) {
                if(size<mini){
                    mini = size;
                }
            }
        }
    }

    private boolean valide(List<Bouton> combinaison) {
        int[] aTester = new int[etatFinal.length];
        for (Bouton bouton : combinaison) {
            for (int aSwitch : bouton.switches) {
                aTester[aSwitch] = 1 - aTester[aSwitch];
            }
        }
        for (int i = 0; i < etatFinal.length; i++) {
            if (aTester[i] != etatFinal[i]) {
                return false;
            }
        }
        return true;
    }


    private void parse(String input) {
        final var split = input.split(" ");
        parseEtatFinal(split[0]);
        boutons = new Bouton[split.length - 2];
        for (int i = 1; i < split.length - 1; i++) {
            boutons[i - 1] = new Bouton(split[i]);
        }
        parseJoltage(split[split.length - 1]);
    }

    private void parseJoltage(String s) {
        final var tmp = s.substring(1, s.length() - 1).split(",");
        joltage = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            joltage[i] = Integer.parseInt(tmp[i]);
        }

    }

    private void parseEtatFinal(String s) {
        final var charArray = s.substring(1, s.length() - 1).toCharArray();
        etatFinal = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            etatFinal[i] = charArray[i] == '.' ? 0 : 1;
        }
    }

    public int mini() {
        return mini;
    }

    public int resolveJoltage() {
        try (Context ctx = new Context()) {
            Optimize opt = ctx.mkOptimize();

            IntExpr[] nbAppuis = new IntExpr[boutons.length];
            for (int i = 0; i < boutons.length; i++) {
                nbAppuis[i] = ctx.mkIntConst("button_" + i);
                // Contrainte : nombre d'appuis >= 0
                opt.Add(ctx.mkGe(nbAppuis[i], ctx.mkInt(0)));
            }

            for (int j = 0; j < joltage.length; j++) {
                //au debut on a zero appuis
                ArithExpr<IntSort> somme = ctx.mkInt(0);

                for (int i = 0; i < boutons.length; i++) {
                    // Vérifier si ce bouton affecte ce joltage
                    if (boutons[i].canSwitch(j)) {
                        somme = ctx.mkAdd(somme, nbAppuis[i]);
                    }
                }

                // Contrainte : la somme doit égaler l'état final
                opt.Add(ctx.mkEq(somme, ctx.mkInt(joltage[j])));
            }

            // minimiser la somme des appuis
            ArithExpr<IntSort> totalAppuis = ctx.mkInt(0);
            for (IntExpr appui : nbAppuis) {
                totalAppuis = ctx.mkAdd(totalAppuis, appui);
            }
            opt.MkMinimize(totalAppuis);

            // Résoudre
            if (opt.Check() == Status.SATISFIABLE) {
                Model model = opt.getModel();
                int total = 0;
                for (int i = 0; i < nbAppuis.length; i++) {
                    int valeur = ((IntNum) model.evaluate(nbAppuis[i], false)).getInt();
                        System.out.printf("[Bt%d->%d]", i, valeur);
                    total += valeur;
                }
                System.out.printf(" Somme: %d%n",total);
                return total;
            } else {
                throw new RuntimeException("Oups i did it again");
            }
        }
    }
}

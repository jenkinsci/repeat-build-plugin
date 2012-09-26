/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jenkinsci.plugins.repeateBuild;

import hudson.Extension;
import hudson.matrix.MatrixConfiguration;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.TransientBuildActionFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Lucie Votypkova
 */
@Extension
public class RepeatBuildActionfactory extends TransientBuildActionFactory{
    
    public Collection<? extends Action> createFor(Run target) {
        List<Action> actions = new ArrayList<Action>();
        if(target.getAction(ParametersAction.class)!=null && !(target.getParent() instanceof MatrixConfiguration)){
                actions.add(new RepeatBuild(target));
        }
        return actions;       
    }
}

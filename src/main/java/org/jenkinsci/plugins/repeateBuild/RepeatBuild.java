/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jenkinsci.plugins.repeateBuild;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Cause;
import hudson.model.CauseAction;
import hudson.model.Job;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.model.Run;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 *
 * @author Lucie Votypkova
 */
public class RepeatBuild implements Action{
    
    private Run run;
    
    public RepeatBuild(Run run){
        this.run=run;
    }
    
    public Run getBuild(){
        return run;
    }

    public String getIconFileName() {
        return "clock.png";
    }

    public String getDisplayName() {
        return "Repeat build";
    }

    public String getUrlName() {
        return "repeat";
    }
    
    public void doRepeatBuild(StaplerRequest request, StaplerResponse response) throws IOException{
        Job owner = run.getParent();
        List<ParameterValue> values = run.getAction(ParametersAction.class).getParameters();

    	Jenkins.getInstance().getQueue().schedule(
                (AbstractProject)owner, 0, new ParametersAction(values), new CauseAction(new Cause.UserIdCause()));
        response.sendRedirect(Jenkins.getInstance().getRootUrl() + run.getParent().getUrl());
    }
    
    public void doIndex(StaplerRequest request, StaplerResponse response) throws IOException, ServletException{
        if(run.getParent().getConfigFile().getFile().lastModified()<run.getTimeInMillis()){            
            doRepeatBuild(request,response);
        }
        else{
            request.getView(this, "confirm.jelly").forward(request, response);
        }
    }
    
}

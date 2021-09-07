package com.algoengines.frm.AlertWorkflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Logger;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Decision implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String url="jdbc:mysql://localhost:3306/alert";
        String user="root";
        String password="Ankit@4321";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con =DriverManager.getConnection(url, user, password);
        PreparedStatement statement = con.prepareStatement("INSERT INTO DECISIONS (vcUniqueTransID, vcComment, bApprove) VALUES (?,?,?);");
		statement.setNString(1, (String) execution.getVariable("vcUniqueTransID"));
		statement.setNString(2, (String) execution.getVariable("Comment"));
		statement.setBoolean(3, (boolean) execution.getVariable("Approve"));
		statement.executeUpdate();

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
	            + "activtyName='" + execution.getCurrentActivityName() + "'"
	            + ", activtyId=" + execution.getCurrentActivityId()
	            + ", processDefinitionId=" + execution.getProcessDefinitionId()
	            + ", processInstanceId=" + execution.getProcessInstanceId()
	            + ", businessKey=" + execution.getProcessBusinessKey()
	            + ", executionId=" + execution.getId()
	            + ", variables=" + execution.getVariables()
	            + ", sqlurl=" + DriverManager.getConnection(url, user, password)
	            + " \n\n");
        
        
    }
    
}

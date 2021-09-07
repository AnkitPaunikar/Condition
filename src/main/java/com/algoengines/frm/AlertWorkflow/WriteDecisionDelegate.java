package com.algoengines.frm.AlertWorkflow;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.sql.*;
import java.util.*;

public class WriteDecisionDelegate implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Properties props = new Properties();
		props.load(WriteDecisionDelegate.class.getClassLoader().getResourceAsStream("application.properties"));
		Connection conn = DriverManager.getConnection(props.getProperty("url"), props);
		PreparedStatement statement = conn.prepareStatement("INSERT INTO DECISIONS (vcUniqueTransID, vcComment, bApprove) VALUES (?,?,?);");
		statement.setNString(1, (String) execution.getVariable("vcUniqueTransID"));
		statement.setNString(2, (String) execution.getVariable("Comment"));
		statement.setBoolean(3, (boolean) execution.getVariable("Approve"));

		LOGGER.info("\n\n  ... LoggerDelegate invoked by "
	            + "activtyName='" + execution.getCurrentActivityName() + "'"
	            + ", activtyId=" + execution.getCurrentActivityId()
	            + ", processDefinitionId=" + execution.getProcessDefinitionId()
	            + ", processInstanceId=" + execution.getProcessInstanceId()
	            + ", businessKey=" + execution.getProcessBusinessKey()
	            + ", executionId=" + execution.getId()
	            + ", variables=" + execution.getVariables()
	            + ", sqlurl=" + props.getProperty("url")
	            + " \n\n");

	}

}

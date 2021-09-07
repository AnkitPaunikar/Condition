package com.algoengines.frm.AlertWorkflow;



import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;


public class payee implements JavaDelegate{

  private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
  
  public static String avg;


  public static String parse(String responseBody){
    // JSONArray payy = new JSONArray(responseBody);
     JSONObject pay = new JSONObject(responseBody);
     avg = pay.getString("avg");
     return avg;
  } 

  @Override
    public void execute(DelegateExecution execution) throws Exception {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/avgg/get")).build();
      client.sendAsync(request,HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenApply(payee::parse)
      .join(); 

       
      
        execution.getBpmnModelElementInstance();
        String vcUniqueTransID ="1234";
        String payeeavg = avg;   
        String Comment = "Fraud";
        Boolean Approve = true;
        String Approvee ="No";

        vcUniqueTransID = (String) execution.getVariable("vcUniqueTransID");
        execution.setVariable("Comment",execution.getVariable("Comment"));
        execution.getVariable("payeeavg");
        
        double moneyDouble = Double.parseDouble(payeeavg);
        if(moneyDouble > 100000){
            Approvee ="Yes";
            Comment = "Not Fraud";
            execution.setVariable("Approve", Approve);
            
           
        }else{
          Approvee="No";
         
        }
        execution.setVariable("Comment" , Comment);
        execution.setVariable("vcUniqueTransID",vcUniqueTransID);
        execution.setVariable("Approvee", Approvee);
        execution.setVariable("payeeavg", payeeavg);

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
            + "activtyName='" + execution.getCurrentActivityName() + "'"
            + ", activtyId=" + execution.getCurrentActivityId()
            + ", processDefinitionId=" + execution.getProcessDefinitionId()
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + ", variables=" + execution.getVariables()
            + " \n\n");
}

 
   
}
    


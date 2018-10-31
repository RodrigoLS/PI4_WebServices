package com.mycompany.pi4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginService {
    
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://pijulio.database.windows.net:1433;database=facenac";
    private static final String USER = "julio@pijulio";
    private static final String PASS = "Abcd123!";
    
   @POST   
   @Consumes("application/json;charset=utf-8")   
   @Produces("application/json;charset=utf-8")   
   public Response isAutorizado (Login login) throws SQLException {
        Response response;
        boolean autorizado = false;
               
        try{
            Class.forName(DRIVER);
            String sql = "select * from usuario where email = ? and senha = ?";
            
            try(Connection conn = DriverManager.getConnection(URL,USER,PASS);                
                PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, login.getEmail());
                stmt.setString(2, login.getSenha());
                try(ResultSet rs = stmt.executeQuery()){
                    if(rs.next()){
                       autorizado = true;
                    }                    
                }
            }
            response = Response.ok(autorizado).build();
          
        }catch(ClassNotFoundException | SQLException ex){
            response = Response.serverError().entity("ERRO: "+ex.getMessage()).build();
        }
        return response;    
   }
   
}

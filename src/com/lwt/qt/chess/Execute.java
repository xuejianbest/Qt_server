package com.lwt.qt.chess;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/Execute")
public class Execute extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private char[][] chess = {{'A','A','A','A'},{'N','N','N','N'},{'N','N','N','N'},{'B','B','B','B'}};
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get...");
		Map<String, Object> map = new HashMap<>();
		map.put("a", 1);
		ObjectMapper mapper = new ObjectMapper();
		String res = mapper.writeValueAsString(map);
		System.out.println(res);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> res= new HashMap<String, Object>();
		String operation = request.getParameter("operation");
		String player_id = request.getParameter("player_id");
		System.out.println(player_id + " : " + operation);
		
		if(!check_operation(operation, player_id)){
			res.put("status", "0");
			response.getWriter().write(res.toString());
			return;
		}
		do_operation(operation, player_id);
		res.put("status", "1");
		res.put("chess", chess);
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				System.out.print(chess[i][j] + " ");
			}
			System.out.println();
		}
		
		if(check_win(player_id)){
			res.put("end", "1");
			res.put("winer", player_id);
		}else{
			res.put("end", "0");
		}
		ObjectMapper mapper = new ObjectMapper();
		String rtn = mapper.writeValueAsString(res);
		System.out.println(rtn);
		response.getWriter().write(rtn);
	}

	private boolean check_operation(String operation, String player_id){
		String operation_1 = operation.substring(0, 1);
		String operation_2 = operation.substring(1);
		char player_flag = player_id.charAt(0);
		int i = -1, j = -1;
		if(Character.isDigit(operation_1.charAt(0))){ //row
			i = Integer.valueOf(operation_1) - 1;
			switch (operation_2) {
			case "A":
				j = 0;
				break;
			case "B":
				j = 1;
				break;
			case "C":
				j = 2;
				break;
			}
			if(chess[i][j] == player_flag && chess[i][j+1] == 'N' || chess[i][j] == 'N' && chess[i][j+1] == player_flag ){
				return true;
			}
		}else{ //col
			j = Integer.valueOf(operation_2) - 1;
			switch (operation_1) {
			case "T":
				i = 0;
				break;
			case "M":
				i = 1;
				break;
			case "D":
				i = 2;
				break;
			}
			if(chess[i][j] == player_flag && chess[i+1][j] == 'N' || chess[i][j] == 'N' && chess[i+1][j] == player_flag ){
				return true;
			}
		}
		return false;
	}
	private void do_operation(String operation, String player_id){
		String operation_1 = operation.substring(0, 1);
		String operation_2 = operation.substring(1);
		char player_flag = player_id.charAt(0);
		int i = -1, j = -1;
		if(Character.isDigit(operation_1.charAt(0))){ //row
			i = Integer.valueOf(operation_1) - 1;
			switch (operation_2) {
			case "A":
				j = 0;
				break;
			case "B":
				j = 1;
				break;
			case "C":
				j = 2;
				break;
			}
			if(chess[i][j] == player_flag){
				chess[i][j] = 'N';
				chess[i][j+1] = player_flag;
			}else{
				chess[i][j] = player_flag;
				chess[i][j+1] = 'N';
			}
			
		}else{ //col
			j = Integer.valueOf(operation_2) - 1;
			switch (operation_1) {
			case "T":
				i = 0;
				break;
			case "M":
				i = 1;
				break;
			case "D":
				i = 2;
				break;
			}
			if(chess[i][j] == player_flag){
				chess[i][j] = 'N';
				chess[i+1][j] = player_flag;
			}else{
				chess[i][j] = player_flag;
				chess[i+1][j] = 'N';
			}
		}
	}
	
	private boolean check_win(String player_id) {
		char player_flag = player_id.charAt(0);
		char P = 'A', NP = 'B';
		if(player_flag != 'A'){
			P = 'B'; NP = 'A';
		}
		for(int i=0; i<4; i++){
			if(chess[i][0] == 'N' && chess[i][1] == P && chess[i][2] == P && chess[i][3] == NP ||
			   chess[i][0] == 'N' && chess[i][1] == NP && chess[i][2] == P && chess[i][3] == P ||
			   chess[i][3] == 'N' && chess[i][2] == P && chess[i][1] == P && chess[i][0] == NP ||
			   chess[i][3] == 'N' && chess[i][2] == NP && chess[i][1] == P && chess[i][0] == P){
				return true;
			}
		}
		for(int j=0; j<4; j++){
			if(chess[0][j] == 'N' && chess[1][j] == P && chess[2][j] == P && chess[3][j] == NP ||
			   chess[0][j] == 'N' && chess[1][j] == NP && chess[2][j] == P && chess[3][j] == P ||
			   chess[3][j] == 'N' && chess[2][j] == P && chess[1][j] == P && chess[0][j] == NP ||
			   chess[3][j] == 'N' && chess[2][j] == NP && chess[1][j] == P && chess[0][j] == P){
				return true;
			}
		}
		return false;
	}
	
}

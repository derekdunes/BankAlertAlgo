//create an object to check most re-occurred transaction 
//which will be used to show a pie chart

public class BankMessageAi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//sample bank messages and non bank message to test the algorithm
		//FirstBank
		String txtmsg = "Your Acct 311XXXX951 Has Been Debited with NGN2,000.00 on 27-NOV-2017 17:43:44 By ATM WITHDRAWAL-11, BLOCK 1, ABAK RD HO/UCR";
		//String txtmsg = "Your Acct 311XXXX951 Has Been Credited with NGN2,000.00 on 27-NOV-2017 17:43:44 By ATM WITHDRAWAL-11, BLOCK 1, ABAK RD HO/UCR";
		//String txtmsg = "Your balance as at 27-NOV-2017 17:43:44 is NGN6,425;. You First";
		
		//AccessBank
		//String txtmsg = "access>>>Debit Alert Amt: NGN 50.00 Dr Acc: 073****630 Desc: MONTHLY CARD FEE ON:0020120685 Time: 30/08/17 a02:33 PM Bal: NGN 3,000.52 #SaveToday";
		//String txtmsg = "access)))Credit Alert Amt: NGN 2000.00 Cr Acc: 073****630 Desc: MBAH DEREK ONYEDIKACHI/Online Time: 30/08/17 a02:33 PM Bal: NGN 2,000.00 #SaveToday";
		
		//GTB
		//String txtmsg = "Acct: ******7186 Amt: NGN100.00 Dr Desc: Airtime Purchase-USSD-101CT0000000000079485129-2348184921792 Avail Bal: NGN3,985.28";
	    //String txtmsg = "Acct: ******7186 Amt: NGN1,000.00 Cr Desc: CASH WDL FROM OTHERS ATM--361611- -Shop 56 Polo Mall EnuguNG STAN9999361611 Avail Bal: NGN6,402.11";    
	    //String txtmsg = "Acct: ******7186 The Naira equivalent of USD12.18 has been authorized for the transaction done on DNH*GODADDY.COM        480-505-8855 AZUS";
	    //String txtmsg = "Acct: ******7186 The Naira equivalent of USD1.95 has been authorized for the transaction done on PP*1158CODE            4029357733     LU";
	    //String txtmsg = "Recharge your phone line with 1-Click airtime top up from GTBank. Simply dial *737*Amount# now. For N1000, dial *737*1000#";
	    
		//Fidelity
		//String txtmsg = "DEBIT ALERT Acct: **7074 Amt: N850 Desc: POS glskldfgdhlldf Date:27/JUN/17 14:57PM Bal: N94,645.45CR info:014485252";
		//String txtmsg = "CREDIT ALERT Acct: **7074 Amt: N850 Desc: POS glskldfgdhlldf Date:27/JUN/17 14:57PM Bal: N94,645.45CR info:014485252";
		//String txtmsg = "Weekly Summary Period: 21-May-2017 to 27-May-2017 Acct:**7074 Start Bal:40255.45 CR: 60,000.00 DR:-17,670.00 End Bal: 67890.45 To stop, dial *770*2#";
		//String txtmsg = "May 2017 Summary Acct: **7074 Currency: NGN Start Bal: 53,647.45 CR: 60,000.00 DR: -46,086.00 Closing Bal: 67,561.45 info: 014485252";
		
		//FCMB
		//String txtmsg ="Credit Alert Ac: **6016 DESC: CSH DEPOSIT BY : DEREK O DATE:01-Jun-2017 16:32 AMT:NGN1,100.00 BAL:NGN500.00 Pls call 012798800 for more info";
		//String txtmsg ="Debit Alert Ac: **6016 DESC: CSH DEPOSIT BY : DEREK O DATE:01-Jun-2017 16:32 AMT:NGN1,100.00 BAL:NGN500.00 Pls call 012798800 for more info";
		
		/** 
		 * An algorithm to recieve text messages from banks and automatically 
		 * collect vital data from the text message such as account number, debit
		 * or credit, amount debited or credited, balance and transaction
		 * description
		 * keywords common with all bank text messages which will be used by the algorithm to deduce whether the message is a debit or credit or ad
		 * Acct, Ac, Amt, AMT, NGN, N, Dr, Debit, DEBIT ALERT, Debit Alert, DEBIT ALERT, Cr, Credit, Credit Alert, Desc, DESC, BY, Avail Bal, BAL, Bal */
		
		//get text message from source and return a list containing
		//transaction amount, account balance, transaction status(credit, debit or not)
		//transaction description(optional)
		double transactionStatus[] = new double[14];//
		double balance;
		//call to the method containing the algorithm and pass the text to be parsed as a parameter 
		bankTransact(txtmsg);
		
	}
	
	public static void bankTransact(String bankData){
		//String[] bankinfo = new String[10];
		
		String newBankData = "";
		
		//change all letters to lower case 
		for(int i = 0; i < bankData.length(); i++){
			//find the character at i index
			char ch = bankData.charAt(i);
			//check if its a letter and if it is in uppercase
			if(Character.isLetter(ch) && Character.isUpperCase(ch)){
				//change to lowwer
				ch = Character.toLowerCase(ch);
			}
			//append to the new string
			newBankData += ch;
		}
		
		System.out.println(newBankData);
		
		//check for relevant data in the message using the keyword
		//search for amt, ngn or n(with a number beside eg n1000 ), desc, debit or dr
		//if credit or cr, avail bal or bal, debit or dr or credit or cr and bal 
		//and amt or time and acct or ac and desc are found in the message then its bank sms
		//else then its not a bank message
		//smsParam holds all the bank letter that shows that a text message is a bank message
		String smsParam[] = {"ac","cr","dr","by","amt","bal","acct","desc","debit","credit"};
		//smsStatus is a boolean array that signifies true when a search strings in smsparam is found
		//the length matches the length of smsParam so when an index is true it signifies that the search param at that position was found
		boolean smsStatus[] = new boolean[smsParam.length];
		
		//loop through the sms param containing alll the strings we are searching for in the bank messgage 
		TheLoop:for(int i = 0; i < smsParam.length; i++){
			
			//loop through the lowercased bank meesage
			bigloop:for(int j = 0; j < newBankData.length(); j++){
				String compString = "";
				
				//get the first character in the message
				char ch1 = newBankData.charAt(j);
				
				//change to a string
				String st = "" + ch1;
				
				//change the first character of the string in position i in the list to string eg 'amt' will result to 'a' as a string 
				String letter = Character.toString(smsParam[i].charAt(0));
				//compare the first character to the searcher's first character
				//if it matches then check all the characters\
				//else move to the next word in the message
				
				if(Character.isLetter(ch1) && st.equals(letter)){
					
					//count/j is the position in the bigloop in which the search string matches a word in the bank message 
					int count = j;
					
					//loop through the search string exclusively since the first letters of the search string and the word in the message matches
					for(int k = 0; k < smsParam[i].length(); k++){
						
						char ch2 = 'a';
						
						//protect the count index from becoming greater than the length of the bank message eg if the length of the bank message is 30 and count increments to 31 it will crash
						// this crash signifies that we have reached the end of the bank message and there is no other string to be found so break of the big loop to move to the next search string in the smsparam list
						try{
							//asssign the character of the message in position count
							ch2 = newBankData.charAt(count);
							
						}catch(StringIndexOutOfBoundsException e){
							//move to the next search string in the list
							break TheLoop;
						}
						
						//convert each character to string
						String st1 = "" + ch2;
						String let = Character.toString(smsParam[i].charAt(k));
						
						//compare the converted strings
						if(Character.isLetter(ch2) && st1.equals(let)){
							System.out.println(let);
							System.out.println(st1);
							
							//keep building the values of the matching string
							//add the new matched string to the existing string eg am + t to make amt
							compString = compString + let;
							
							//increment the count for the next letter in the bank message
							count = count + 1;
							
							//keep checking if the builtup string matches the search string
							//till it matches
							//eg amt of compSt.. equals amt of the search string in the list
							if(compString.equals(smsParam[i])){
								System.out.println("enter here");
								smsStatus[i] = true;//means that the search string(smsparam) at index i of the loop was found
								compString = "";//empty the string
								break;
							}
						}else{
							//the String does not match
							//move to next search string
							//System.out.println(let);
							//System.out.println(st1);
							System.out.println("in here");
							//empty compString
							compString = "";
							break; //skip building the string due to mismatch
							//and moves to the next letter/character on the text message
						}
											
					}
							
				}
			}
		}
		
		//print all the values of boolean array smsStatus used for checking search strings in smsParam that was found  
		for(boolean s : smsStatus){
			System.out.println(s);
		}
		//next use the boolean data returned to know if the string is a bankSms
		//{"ac","cr","dr","amt","bal","acct","desc","by","debit","credit"};
		//the message to be a bank alert the following parameters must be true
		//(ac or acct = true) and (cr or credit = true) or (dr or debit = true)
		//desc = true && by = true("by" indicates description for first Bank)  
		//{"ac","cr","dr","by","amt","bal","acct","desc","debit","credit"};
		
		boolean accountNo,desc,credit,debit,creditAlert,debitAlert;
		
		accountNo = (smsStatus[0] == true /*(ac)*/ || smsStatus[6] == true /*(acct)*/);
		creditAlert = (smsStatus[1] == true /*(cr)*/ || smsStatus[9] == true /*(credit)*/);
		debitAlert = (smsStatus[2] == true /*(dr)*/ || smsStatus[8] == true /*(debit)*/);
		desc = (smsStatus[3] == true /*(by)*/ || smsStatus[7] == true /*(desc)*/);
		
		//for the message to be a bank alert it must have accountNo as true and (credit or debit) true  
		if(accountNo && smsStatus[8] == true){
			debitAlert = true;
			creditAlert = false;
		}else if(accountNo && smsStatus[9] == true){
			creditAlert = true;
			debitAlert = false;
		}
		
		//Next extract relevant data if the message is a bank message
		//data like amount withrawn, available balance and description
		if(accountNo && creditAlert && !debitAlert && desc){
			System.out.println("Credit BankAlert!!!");
			relevantData(newBankData,smsStatus);
		}else if(accountNo && !creditAlert && debitAlert && desc){
			System.out.println("Debit BankAlert!!!");
			relevantData(newBankData,smsStatus);
		}else{
			System.out.println("Not BankAlert!!!");
		}
		
		//return bankinfo;
	}
	
	static void relevantData(String bankMsg,boolean[] smsStatus){
		//Relevant Data (Description,Amount and Balance)
		//if you find string amt or bal 
		//then start looking for numbers if its not a number discard 
		//till stop seeing numbers
		//for desc, find keyword desc and pick every text until you reach a space
		//or the next search keyword
		
		//Keywords to search for
		//keywords tell us which bank it is and how to get the transaction amount and the balance of the transaction
		String keyWords[] = new String[2];
		
		double bankVal[] = new double[2];
		
		//check if message is from first else its from other banks
		if(smsStatus[3] == true /*(by)*/ && smsStatus[7] == false /*(desc)*/){
			//if true it means the bank selected is first bank due to absense of balance
			//eg NGN200 ngn come before the transaction amt in the firstbank message while amt comes before ... in other banks
			keyWords[0] = "ngn";
			keyWords[1] = "acct";
		}else{
			//for other banks
			keyWords[0] = "amt";
			keyWords[1] = "bal";
		}
		
		//loop through the keyword to find them in the 
		for(int i = 0; i < keyWords.length; i++){
			bigloop:for(int j = 0; j < bankMsg.length(); j++){
				String compString = "";
				char ch1 = bankMsg.charAt(j);
				String st = "" + ch1;
				String letter = Character.toString(keyWords[i].charAt(0));
				//compare character to the searcher's first character
				if(Character.isLetter(ch1) && st.equals(letter)){
					int count = j;
					for(int k = 0; k < keyWords[i].length(); k++){ 
						char ch2 = bankMsg.charAt(count);
						String st1 = "" + ch2;
						String let = Character.toString(keyWords[i].charAt(k));
						
						if(Character.isLetter(ch2) && st1.equals(let)){
							System.out.println(let);
							System.out.println(st1);
							compString = compString + let;
							count = count + 1;
							if(compString.equals(keyWords[i])){
								//Start looking for the Numbers
								System.out.println("enter here");
								System.out.println(count);
								String Number = "";
								char lastChar = ' ';
								String fStop = ".";
								for(int p = count;p < bankMsg.length(); p++){
									char Num = bankMsg.charAt(p);
									String NewChar = "" + Num;
									try{
										int newNum = Integer.parseInt(NewChar);
										Number = Number + Integer.toString(newNum);
										System.out.println(Number);
									}catch(Exception e){
										System.out.println(Num);
										if(NewChar.equalsIgnoreCase(fStop) 
												&& Character.isDigit(lastChar) 
												&& Character.isDigit(bankMsg.charAt((p+1)))){
											Number += fStop;
										}
									}
									if(!Character.isDigit(lastChar) 
											&& !Character.isDigit(Num) 
											&& !Character.isDigit(bankMsg.charAt((p+1))) 
											&& !Number.isEmpty()){
										try{
											bankVal[i] = Double.parseDouble(Number);
											System.out.println("Parsed Number is "  + Number + " at the " + i + "th value");
										}catch(Exception e){
											e.printStackTrace();
											bankVal[i] = 0.0;
											System.out.println("Double parse error");
										}
										Number = "";
										break bigloop;
									}
									lastChar = Num;
									if(p == (bankMsg.length()-1)){
										try{
											bankVal[i] = Double.parseDouble(Number);
											System.out.println("Parsed Number is "  + Number + " at the " + i + "th value");
										}catch(Exception e){
											e.printStackTrace();
											bankVal[i] = 0.0;
											System.out.println("Double parse error");
										}
										Number = "";
										break bigloop;
									}
								}
								
							}
						}else{
							//System.out.println(let);
							//System.out.println(st1);
							System.out.println("in here");
							compString = "";
							break;
						}				
					}
							
				}
			}
		}
		//make amount negative or positive depending on whether the transaction
		//is a credit or a debit
		for(int i=0; i < bankVal.length;i++){
			if(i == 0){
				if((smsStatus[2] || smsStatus[8]) && (!smsStatus[1] || !smsStatus[9])){
					double newVal = bankVal[i];
					bankVal[i] = -newVal;
				}
			}
			System.out.println(bankVal[i]);
		}
	}
	
	public class Account{
		//1. check the account balance of the calc bank message transaction
		//2. finds the error between the calc Bank balance and Original banks balance
		//3. Finds the most reoccured Number
		
		private double[] mAmount;
		private double balance; 
		
		public Account(double[] amount){
			mAmount = amount;
		}
		
		public void checkAccountBalance(){
			//returns the calculated balance from the array
			//Through the array and sums all the transaction amounts
			for(int i = 0; i < mAmount.length; i++){
				balance = balance + mAmount[i];
			}
			
		}
		
		public void checkMostReoccured(){
			//create a new array to store the no of times a number reoccured
			//get the whole array and loop through the numbers
			//for each number search through array and count how many matches found
			//if found store the no of repetition
			//loop through the no of repitions
			//for each of repetitions look for other matching repetitions
			//if their amount matches set the new found amount to be (0.0) and 
			//its equivalent no of repetitions as (-1)
			
			int[] NofOccur = new int[mAmount.length];
			int count = 0;
			
			for(int i = 0; i < mAmount.length; i++){
				for(int j = 0; j < mAmount.length; j++){
					
					if(mAmount[i] == mAmount[j]){
						count++;
					}
					
					
					if(j == (mAmount.length-1)){
						NofOccur[i] = count;
						count = 0;
					}
								
				}

			}
			
			for(int k = 0;k < NofOccur.length; k++){
				int cont = 0;
				for(int l = 0; l < NofOccur.length; l++){
					if(NofOccur[l]== -1 && mAmount[l] == 0.0){
						break;
					}
					if(NofOccur[k] == NofOccur[l] && mAmount[k] == mAmount[l]){
						if(cont != 0){
							NofOccur[l] = -1;
							mAmount[l] = 0.0;
						}
						cont++;
					}
				}
			}
			
		}
		
		public double getBalance(){
			return balance;
		}
	}
}



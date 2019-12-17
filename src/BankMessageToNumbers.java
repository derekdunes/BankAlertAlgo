
public class BankMessageToNumbers {
	
	private String[] BankMessageArray;
	private String[] BankMessageDateArray;
	
	//transaction amount and balance
	private double amount[];
	private double balance[];
	private double date[];
	private double accountBalance = 0;
	
	private int cofTrues = 0;
	private int cofNoes = 0;
	
	
	public BankMessageToNumbers(String[] BankMessageArray, String[] BankMessageDateArray) {
		// TODO Auto-generated constructor stub
		this.BankMessageArray = BankMessageArray;
		this.BankMessageDateArray = BankMessageDateArray;
		
		checkRelevantData();
		initiateTransactArrays();
		saveTransactKeyValues();
		
	}
	
	//No of relevant bank transactions
	public int getNoOfTrues(){
		return cofTrues;
	}
	
	//No of irrelevant bank transactions
	public int getNoOfNoes(){
		return cofNoes;
	}
	
	public double[] getTransactionAmounts(){
		double newAmount[] = new double[amount.length];
		double count = 0.0;
		int counter = 0;
				
		for(double amt : amount){
			
			count = count + amt;
			newAmount[counter] = count;
			counter++;
			
		}
		
		return newAmount;
	
	}
	
	public double[] getTransactionDates(){
        return date;
    }
	
	public double[] getBalanceAmounts(){
		return balance;
	}
	
	public double getTotalBalance(){
		checkAccountBalance();
		return accountBalance;
	}
	
	public void checkRelevantData(){
		boolean[] relevant = new boolean[BankMessageArray.length];
		int count = 0;
		
		for(String s : BankMessageArray){
			relevant[count] = checkBankTransact(s);
			count++;
		}
		
		for(boolean r : relevant){
			//check no of trues and no of false in the bool array
			//no of trues will be used to create an array to contain relevant data
			
			if(r)
				cofTrues++;
			else
				cofNoes++;
		}
		
        //date array  for relevant dates
        date = new double[cofTrues];
        int cont = 0;

        for(int x = 0; x < relevant.length; x++){

            if (relevant[x]){
                date[cont] = Double.valueOf(BankMessageDateArray[x]);
                cont++;
            }
        }
	
	}
	
	//checkRelevantData method must be called before this
	void initiateTransactArrays(){
		amount = new double[cofTrues];
		balance = new double[cofTrues];
	}
		
	//store the relevant data in the amount and balance array
	public void saveTransactKeyValues(){
		
		for(int i=0; i < BankMessageArray.length;i++){
			BankTransact(BankMessageArray[i],i);
		}
	}
	
	//check for relevant bank messages in each string inputed 	
	public void BankTransact(String bankData, int BankMessageCount){
			
		String newBankData = "";
			
		//change all letters to lower case 
		for(int i = 0; i < bankData.length(); i++){
			char ch = bankData.charAt(i);
			if(Character.isLetter(ch) && Character.isUpperCase(ch)){
				ch = Character.toLowerCase(ch);
			}
			newBankData += ch;			
		}
			
		System.out.println(newBankData);
			
//		check for relevant data in the message using the keyword
//		search for amt, ngn or n(with a number beside), desc, debit or dr
//		credit or cr, avail bal or bal
//		if debit or dr or credit or cr and bal 
//		and amt or time and acct or ac and desc is true then its bank sms
//		else then its not a bank message
		String smsParam[] = {"ac","cr","dr","by","amt","bal","acct","desc","debit","credit"};
		boolean smsStatus[] = new boolean[smsParam.length];
			
		TheLoop:for(int i = 0; i < smsParam.length; i++){
			bigloop:for(int j = 0; j < newBankData.length(); j++){
				String compString = "";
				char ch1 = newBankData.charAt(j);
				String st = "" + ch1;
				String letter = Character.toString(smsParam[i].charAt(0));
				//compare the first character to the searcher's first character
				if(Character.isLetter(ch1) && st.equals(letter)){
					int count = j;
					for(int k = 0; k < smsParam[i].length(); k++){
						char ch2 = 'a';
						try{
							ch2 = newBankData.charAt(count);
						}catch(StringIndexOutOfBoundsException e){
							break TheLoop;
						}
						String st1 = "" + ch2;
						String let = Character.toString(smsParam[i].charAt(k));
							
						if(Character.isLetter(ch2) && st1.equals(let)){
							System.out.println(let);
							System.out.println(st1);
							compString = compString + let;
							count = count + 1;
							if(compString.equals(smsParam[i])){
								System.out.println("enter here");
								smsStatus[i] = true;
								compString = "";
								break bigloop;
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
		for(boolean s : smsStatus){
			System.out.println(s);
		}
		
		//next use the boolean data returned to know if the string is a bankSms
		//{"ac","cr","dr","amt","bal","acct","desc","by","debit","credit"};
		//the message to be a bank alert the following parameters must be true
		//(ac or acct = true) and (cr or credit = true) or (dr or debit = true)
		//desc = true && by = true("by" indicates description for first Bank)  
		//{"ac","cr","dr","by","amt","bal","acct","desc","debit","credit"};
			
		boolean accountNo,desc,creditAlert,debitAlert;
			
		accountNo = (smsStatus[0] == true || smsStatus[6] == true);
		creditAlert = (smsStatus[1] == true || smsStatus[9] == true);
		debitAlert = (smsStatus[2] == true || smsStatus[8] == true);
		desc = (smsStatus[3] == true || smsStatus[7] == true);
			
		//for the message to be alert it must have accountNo as true and (credit or debit) true  
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
			//System.out.println("Credit BankAlert!!!");
			relevantData(newBankData, smsStatus, BankMessageCount);
		}else if(accountNo && !creditAlert && debitAlert && desc){
			//System.out.println("Debit BankAlert!!!");
			relevantData(newBankData, smsStatus, BankMessageCount);
			
		}else{
			return;
			//System.out.println("Not BankAlert!!!");
		}
			
			
	}
	
	public void relevantData(String bankMsg, boolean[] smsStatus, int BankMessageCount){
		//Relevant Data (Description,Amount and Balance)
		//if you find string amt or bal 
		//then start looking for numbers if its not a number discard 
		//till stop seeing numbers
		//for desc, find keyword desc and pick every text until you reach a space
		//or the next search keyword
		
		//Keywords to search for
		
		String keyWords[] = new String[2];
		double bankVal[] = new double[2];
		
		if(smsStatus[3] == true && smsStatus[7] == false){
			//if true it means the bank selected is first bank due to absense of balance
			keyWords[0] = "ngn";
			keyWords[1] = "acct";
		}else{
			//for other banks
			keyWords[0] = "amt";
			keyWords[1] = "bal";
		}
		
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
									
									try{
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
									}catch(Exception e){
										e.printStackTrace();
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
				try{
					amount[BankMessageCount] = bankVal[i];
				}catch(ArrayIndexOutOfBoundsException e){
					
				}
				
			}
			
			if(i == 1){
				try{
					balance[BankMessageCount] = bankVal[i];
				}catch(ArrayIndexOutOfBoundsException e){
					
				}
			}
			
		}
	}
	
	//check for relevant bank messages in eaach string inputed 	
		public boolean checkBankTransact(String bankData){
			//String[] bankinfo = new String[10];
			
			String newBankData = "";
			
			//change all letters to lower case 
			for(int i = 0; i < bankData.length(); i++){
				char ch = bankData.charAt(i);
				if(Character.isLetter(ch) && Character.isUpperCase(ch)){
					ch = Character.toLowerCase(ch);
				}
				newBankData += ch;
			}
			
			System.out.println(newBankData);
			
			//check for relevant data in the message using the keyword
			//search for amt, ngn or n(with a number beside), desc, debit or dr
			//credit or cr, avail bal or bal
			//if debit or dr or credit or cr and bal 
			//and amt or time and acct or ac and desc is true then its bank sms
			//else then its not a bank message
			String smsParam[] = {"ac","cr","dr","by","amt","bal","acct","desc","debit","credit"};
			boolean smsStatus[] = new boolean[smsParam.length];
			
			TheLoop:for(int i = 0; i < smsParam.length; i++){
				bigloop:for(int j = 0; j < newBankData.length(); j++){
					String compString = "";
					char ch1 = newBankData.charAt(j);
					String st = "" + ch1;
					String letter = Character.toString(smsParam[i].charAt(0));
					//compare the first character to the searcher's first character
					if(Character.isLetter(ch1) && st.equals(letter)){
						int count = j;
						for(int k = 0; k < smsParam[i].length(); k++){
							char ch2 = 'a';
							try{
								ch2 = newBankData.charAt(count);
							}catch(StringIndexOutOfBoundsException e){
								break TheLoop;
							}
							String st1 = "" + ch2;
							String let = Character.toString(smsParam[i].charAt(k));
							
							if(Character.isLetter(ch2) && st1.equals(let)){
								System.out.println(let);
								System.out.println(st1);
								compString = compString + let;
								count = count + 1;
								if(compString.equals(smsParam[i])){
									System.out.println("enter here");
									smsStatus[i] = true;
									compString = "";
									break bigloop;
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
			for(boolean s : smsStatus){
				System.out.println(s);
			}
			//next use the boolean data returned to know if the string is a bankSms
			//{"ac","cr","dr","amt","bal","acct","desc","by","debit","credit"};
			//the message to be a bank alert the following parameters must be true
			//(ac or acct = true) and (cr or credit = true) or (dr or debit = true)
			//desc = true && by = true("by" indicates description for first Bank)  
			//{"ac","cr","dr","by","amt","bal","acct","desc","debit","credit"};
			
			boolean accountNo,desc,creditAlert,debitAlert;
			
			accountNo = (smsStatus[0] == true || smsStatus[6] == true);
			creditAlert = (smsStatus[1] == true || smsStatus[9] == true);
			debitAlert = (smsStatus[2] == true || smsStatus[8] == true);
			desc = (smsStatus[3] == true || smsStatus[7] == true);
			
			//for the message to be alert it must have accountNo as true and (credit or debit) true  
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
				//relevantData(newBankData,smsStatus);
				return true;
			}else if(accountNo && !creditAlert && debitAlert && desc){
				System.out.println("Debit BankAlert!!!");
				//relevantData(newBankData,smsStatus);
				return true;
			}else{
				return false;
				//System.out.println("Not BankAlert!!!");
			}
			
		}
		
		public void checkAccountBalance(){
			//returns the calculated balance from the array
			//Through the array and sums all the transaction amounts
			for(int i = 0; i < balance.length; i++){
				accountBalance = accountBalance + balance[i];
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
			
			int[] NofOccur = new int[amount.length];
			int count = 0;
			
			for(int i = 0; i < amount.length; i++){
				for(int j = 0; j < amount.length; j++){
					
					if(amount[i] == amount[j]){
						count++;
					}
					
					
					if(j == (amount.length-1)){
						NofOccur[i] = count;
						count = 0;
					}
								
				}

			}
			
			for(int k = 0;k < NofOccur.length; k++){
				int cont = 0;
				for(int l = 0; l < NofOccur.length; l++){
					if(NofOccur[l]== -1 && amount[l] == 0.0){
						break;
					}
					if(NofOccur[k] == NofOccur[l] && amount[k] == amount[l]){
						if(cont != 0){
							NofOccur[l] = -1;
							amount[l] = 0.0;
						}
						cont++;
					}
				}
			}
			
		}
		
		

}

